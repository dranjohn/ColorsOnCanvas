package dranjohn.graphics.scene

import dranjohn.graphics.Window
import dranjohn.graphics.entity.Entity
import dranjohn.graphics.scene.camera.Camera

/**
 * A set of renderers currently rendering on one or more [Window]s.
 */
interface Scene {
	/**
	 * Store the width of this scene. Doesn't change depending on the size of the [Window] this scene is rendered on.
	 */
	var unitWidth: Float
	
	/**
	 * Store the height of this scene. Doesn't change depending on the size of the [Window] this scene is rendered on.
	 */
	var unitHeight: Float
	
	/**
	 * The [Camera] used by the [Renderer]s in this scene to render.
	 */
	var camera: Camera
	
	
	/**
	 * Gets the [Renderer] for the given [Shader]. If no [Renderer] exists yet for the given [Shader], a new one is
	 * created.
	 *
	 * @param shader - the [Shader] of the requested [Renderer].
	 */
	fun <EntityType : Entity> getRenderer(shader: Shader<EntityType>): Renderer<EntityType>
	
	/**
	 * Adds an existing [Renderer] to this scene.
	 *
	 * @param renderer - the [Renderer] to add to this scene.
	 */
	fun <EntityType : Entity> addRenderer(renderer: Renderer<EntityType>)
	
	/**
	 * Renders all [Renderer]s belonging to this scene.
	 */
	fun renderAll()
	
	/**
	 * Changes the current background color of this scene. Calls [setBackgroundColor] with the given value for the red,
	 * green and blue value. This background color persists until changed again.
	 *
	 * @param value - the value for all red, green and blue of the new background color. Will be clamped to [0, 1].
	 */
	fun setBackgroundColor(value: Float) = setBackgroundColor(value, value, value)
	
	/**
	 * Changes the current backgriund color of this scene. This background color persists until changed again.
	 *
	 * @param red - the red component of the new background color. Will be clamped to [0, 1].
	 * @param green - the green component of the new background color. Will be clamped to [0, 1].
	 * @param blue - the blue component of the new background color. Will be clamped to [0, 1].
	 */
	fun setBackgroundColor(red: Float, green: Float, blue: Float)
}