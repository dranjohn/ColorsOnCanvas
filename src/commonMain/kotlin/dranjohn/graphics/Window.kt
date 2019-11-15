package dranjohn.graphics

import dranjohn.graphics.entity.TimedUpdateListener
import dranjohn.graphics.entity.UpdateListener
import dranjohn.graphics.scene.Scene

/**
 * A function consuming two [Float]s (the (x, y) position of the click, in units on the scene), the [MouseButton] used and the [ButtonAction] taken.
 */
typealias ClickListener = (Float, Float, MouseButton, ButtonAction) -> Unit

/**
 * A function consuming an [Int] for the key code and the [ButtonAction] taken.
 */
typealias KeyListener = (Int, ButtonAction) -> Unit

/**
 * Expects an implementation for a window. Windows are initialized with a width, height and a displayed title.
 * The default size of a window 1280x720 for testing on a 1920x1080 screen, the default name is empty.
 */
expect class Window(width: Int = 1280, height: Int = 720, title: String = "") {
	/**
	 * Shows this window if it is hidden. If the window is currently visible, no action will be taken.
	 */
	fun show()
	
	/**
	 * Hides this window if it is visible. If the window is currently hidden, no action will be taken.
	 */
	fun hide()
	
	
	/**
	 * Creates a new [Scene]. This window will immediately use this new [Scene].
	 */
	fun createScene(): Scene
	
	/**
	 * Gets the [Scene] currently used by this window.
	 */
	fun getScene(): Scene
	
	
	/**
	 * Opens this window. Starts the logic and render loop of this window.
	 */
	fun open()
	
	/**
	 * Closes this window before it iterates through its render/logic loop the next time.
	 */
	fun close()
	
	
	/**
	 * Sets the current title of this window.
	 *
	 * @param title - the title to display for this window.
	 */
	fun setTitle(title: String)
	
	
	//TODO: move all listeners to scenes
	//TODO: differentiate between pre-timed and post-timed update listeners
	//TODO: unregistering of update listeners
	/**
	 * Registers an [UpdateListener] for this window. These time-independent update listeners are called once before
	 * rendering the next frame and before the time-dependent update listeners registered by using
	 * [registerTimedUpdateListener]. No guarantee is made in which order these update listeners are called.
	 *
	 * @param listener - the [UpdateListener] to register.
	 */
	fun registerUpdateListener(listener: UpdateListener)
	
	/**
	 * Registers a [TimedUpdateListener] for this window. These time-dependent update listeners are called once before
	 * rendering the next frame and after the time- independent update listeners registered by using
	 * [registerUpdateListener]. No guarantee is made in which order these update listeners are called.
	 *
	 * @param listener - the [TimedUpdateListener] to register.
	 */
	fun registerTimedUpdateListener(listener: TimedUpdateListener)
	
	//TODO: currently dependent on logic loop in JVM, change this system
	fun registerClickListener(listener: ClickListener)
	fun unregisterClickListener(listener: ClickListener)
	fun unregisterAllClickListener()
	
	fun registerKeyListener(listener: KeyListener)
	fun unregisterKeyListener(listener: KeyListener)
	fun unregisterAllKeyListener()
}