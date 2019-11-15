#version 330 core

uniform sampler2D entityTexture;
uniform sampler2D entityTextureMask;

in vec2 texturePosition;

out vec4 color;

void main() {
    color = texture(entityTexture, texturePosition) * texture(entityTextureMask, texturePosition);
}
