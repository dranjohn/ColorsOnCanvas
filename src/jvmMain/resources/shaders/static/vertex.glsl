#version 330 core

layout (location = 0) in vec2 position;

uniform mat4 transformation;
uniform mat4 projection;

out vec2 squarePosition;

void main() {
    squarePosition = position + vec2(0.5, 0.5);

    gl_Position = projection * transformation * vec4(position, 0.0, 1.0);
}
