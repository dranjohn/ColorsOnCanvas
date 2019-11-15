package dranjohn.graphics

/**
 * An enum of all possible mouse buttons. If an unknown mouse button is used, the [MouseButton.OTHER] type is used.
 */
enum class MouseButton {
	LEFT, MIDDLE, RIGHT, OTHER
}

/**
 * An enum all possible action to use a button (mouse button, key on the keyboard, etc.). If an unknown action was used,
 * the [ButtonAction.UNKNOWN] is used.
 */
enum class ButtonAction {
	PRESS, RELEASE, UNKNOWN
}