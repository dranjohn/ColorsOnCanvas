#version 330 core

in vec2 squarePosition;

out vec4 fragmentColor;

void main() {
    fragmentColor = vec4(0.5, squarePosition.x, squarePosition.y, 1.0);
}
