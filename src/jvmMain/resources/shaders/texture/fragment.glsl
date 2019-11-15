#version 330 core

uniform sampler2D entityTexture;

in vec2 texturePosition;

out vec4 color;

void main() {
    color = texture(entityTexture, texturePosition);
}
