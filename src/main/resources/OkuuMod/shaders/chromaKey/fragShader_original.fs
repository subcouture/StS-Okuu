//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform sampler2D u_galaxyTexture;
uniform float Time;

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;

float textureLength;

vec3 rgb2hsv(vec3 rgb)
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
	vec3 backgroundColor = vec3(1, 0, 1);
	vec3 weights = vec3(4., 1., 0.);

	vec3 hsv = rgb2hsv(color);
	vec3 target = rgb2hsv(backgroundColor);
	float dist = length(weights * (target - hsv));
	return 1. - clamp(3. * dist - 1.5, 0., 1.);
}

vec3 changeSaturation(vec3 color, float saturation)
{
	float luma = dot(vec3(0.213, 0.715, 0.072) * color, vec3(1.));
	return mix(vec3(luma), color, saturation);
}




void main() {
    //sample the texture

    vec4 texColor = texture2D(u_texture, v_texCoord);
    vec3 c = texture2D(u_galaxyTexture, vec2(gl_FragCoord.x/(textureSize(u_galaxyTexture, 0).x) + Time/100, gl_FragCoord.y/(textureSize(u_galaxyTexture, 0).y) + Time/100)).rgb;
	//vec3 c = texture2D(u_galaxyTexture, gl_FragCoord/textureSize(u_galaxyTexture, 0)).rgb;


	//textureLength = textureSize(u_galaxyTexture, 0).x;

	//vec3 c = texture2D(u_galaxyTexture, textureSize(u_galaxyTexture)).rgb;

    float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));

    //float incrustation = chromaKey(texColor);

    //texColor.rgb = changeSaturation(texColor.rgb, 0.5)
    //texColor.rgb = mix(texColor.rgb, galaxyColor.rgb, incrustation);

    //final color

    float incrustation = chromaKey(texColor);

    texColor.rgb = mix(texColor.rgb, c.rgb, incrustation);


	gl_FragColor = vec4(texColor);

}