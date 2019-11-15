package dranjohn.graphics.entity

import dranjohn.graphics.Window

/**
 * An update listener is a function with no parameters and no return type. It is called in the logic part of the
 * rendering process of a [Window].
 */
typealias UpdateListener = () -> Unit

/**
 * A timed update listener is a consumer for [Float]s, the time that passed since the last render. It is called in the
 * logic part of the rendering process of a [Window].
 */
typealias TimedUpdateListener = (Float) -> Unit