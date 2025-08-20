#version 330 //Have to include the pragma once, otherwise it'll load version 1.1 by default. 330 should be supported by all platforms, and maintain consistency between platforms.
//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform sampler2D u_galaxyTexture;
uniform float Time; //we use Time to move the galaxy texture independent of the sprite.

//frankly, I don't really understand how shaders work, so please don't look at this as a good example of how to implement shaders.

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;

float textureLength;

vec3 rgb2hsv(vec3 rgb) //function to convert rgb to hsv values, no need to touch, I didn't even write this.
{
	float Cmax = max(rgb.r, max(rgb.g, rgb.b));
	float Cmin = min(rgb.r, min(rgb.g, rgb.b));
    float delta = Cmax - Cmin;

	vec3 hsv = vec3(0., 0., Cmax);

	if (Cmax > Cmin)
	{
		hsv.y = delta / Cmax;

		if (rgb.r == Cmax)
			hsv.x = (rgb.g - rgb.b) / delta;
		else
		{
			if (rgb.g == Cmax)
				hsv.x = 2. + (rgb.b - rgb.r) / delta;
			else
				hsv.x = 4. + (rgb.r - rgb.g) / delta;
		}
		hsv.x = fract(hsv.x / 6.);
	}
	return hsv;
}

float chromaKey(vec3 color)
{
	vec3 backgroundColor = vec3(1, 0, 1); //The colour we're chromakeying out. Utsuho uses pure purple for that. If you want to do this with like, Hatate, probably change this to green.
	vec3 weights = vec3(4., 1., 0.); //I just played around with the weights until it worked, I'm sure there's a science to it but I don't know it.

	vec3 hsv = rgb2hsv(color);
	vec3 target = rgb2hsv(backgroundColor);
	float dist = length(weights * (target - hsv));
	return 1. - clamp(3. * dist - 1.5, 0., 1.);
}

//vec3 changeSaturation(vec3 color, float saturation)
//{
//	float luma = dot(vec3(0.213, 0.715, 0.072) * color, vec3(1.));
//	return mix(vec3(luma), color, saturation);
//}

void main() {
    //sample the texture
    vec4 texColor = texture2D(u_texture, v_texCoord);

    //Create a texture from the galaxy texture, cycling it based on the time function
    vec3 galaxy = texture2D(u_galaxyTexture, vec2(gl_FragCoord.x/(textureSize(u_galaxyTexture, 0).x) + Time/100, gl_FragCoord.y/(textureSize(u_galaxyTexture, 0).y) + Time/100)).rgb;

    //float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));

    vec3 texColorNoAlpha = texColor.rgb; //texColor is the RGBA value, but we only need the RGB value. Trying to implicitly convert will prevent compilation in some versions of GLSL

    float incrustation = chromaKey(texColorNoAlpha); //chromakey out the purple
    texColor.rgb = mix(texColor.rgb, galaxy.rgb, incrustation); //mix replace the purple with the galaxy texture.

    //final colour
    gl_FragColor = texColor;
}