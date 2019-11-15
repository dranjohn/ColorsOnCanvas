#version 330 core

uniform vec3 color;
uniform sampler2D mask;

in vec2 texturePos;

out vec4 fragmentColor;

void main() {
    fragmentColor = vec4(color, 1.0) * texture(mask, texturePos);
}
