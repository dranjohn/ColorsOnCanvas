package dranjohn.graphics.scene.camera

import dranjohn.math.Matrix4x4f
import dranjohn.graphics.scene.Renderer

/**
 * A camera providing a view and projection [Matrix4x4f] to [Renderer]s.
 */
interface Camera {
	/**
	 * The x position of the camera. Changing this will change the view matrix.
	 */
	var xPos: Float
	
	/**
	 * The y position of the camera. Changing this will change the view matrix.
	 */
	var yPos: Float
	
	
	/**
	 * The view matrix of this camera.
	 */
	fun getView(): Matrix4x4f
	
	/**
	 * The projection matrix provided by this camera.
	 */
	fun getProjection(): Matrix4x4f
}