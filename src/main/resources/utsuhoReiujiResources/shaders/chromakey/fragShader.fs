//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform sampler2D u_galaxyTexture;

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;

void main() {
    //sample the texture
    vec4 texColor = texture2D(u_texture, v_texCoord);
    vec4 galaxyColor = texture2D(u_galaxyTexture, v_texCoord);
    
    float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));

    //final color
    gl_FragColor = vec4(gray, gray, gray, texColor.a);
}