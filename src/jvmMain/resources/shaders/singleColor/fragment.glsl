#version 330 core

uniform vec3 color;

in vec2 squarePosition;

out vec4 fragmentColor;

void main() {
    fragmentColor = vec4(color, 1.0);
}
