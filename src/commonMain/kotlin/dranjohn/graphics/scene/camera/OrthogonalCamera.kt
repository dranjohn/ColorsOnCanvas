package dranjohn.graphics.scene.camera

import dranjohn.math.Matrix4x4f

/**
 * A [Camera] implementation providing a simple view in the window. Orthogonal projection only transform coordinates
 * from world space into a 2D coordinate system on the screen from -1 to 1 on both the x and y axis.
 */
class OrthogonalCamera(private val unitWidth: Float, private val unitHeight: Float) : Camera  {
	override var xPos: Float = 0f
		set(value) {
			if (field == value) {
				return
			}
			
			field = value
			view[0, 3] = value
		}
	override var yPos: Float = 0f
		set(value) {
			if (field == value) {
				return
			}
			
			field = value
			view[1, 3] = value
		}
	
	private var view = Matrix4x4f(floatArrayOf(
		1f, 0f, 0f, xPos,
		0f, 1f, 0f, yPos,
		0f, 0f, 1f, 0f,
		0f, 0f, 0f, 1f
	))
	private val projection = Matrix4x4f(floatArrayOf(
		2f / unitWidth, 0f, 0f, 0f,
		0f, 2f / unitHeight, 0f, 0f,
		0f, 0f, 1f, 0f,
		0f, 0f, 0f, 1f
	))
	
	
	override fun getView(): Matrix4x4f = view
	override fun getProjection(): Matrix4x4f = projection
}