package dranjohn.graphics

val GLFWtoCOCMouseButton = {buttonCode: Int -> when(buttonCode) {
	0 -> MouseButton.LEFT
	1 -> MouseButton.RIGHT
	2 -> MouseButton.MIDDLE
	else -> MouseButton.OTHER
}}

val GLFWtoCOCButtonAction = {actionCode: Int -> when(actionCode) {
	0 -> ButtonAction.RELEASE
	1 -> ButtonAction.PRESS
	else -> ButtonAction.UNKNOWN
}}
