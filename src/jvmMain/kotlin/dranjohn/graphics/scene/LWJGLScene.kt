package dranjohn.graphics.scene

import dranjohn.graphics.Window
import dranjohn.graphics.entity.Entity
import dranjohn.graphics.entity.Quad
import dranjohn.graphics.scene.camera.Camera
import dranjohn.graphics.scene.camera.OrthogonalCamera
import org.lwjgl.opengl.GL11

private const val DEFAULT_UNITS_PER_SCREEN_WIDTH = 16f

class LWJGLScene(window: Window) : Scene {
	private val renderers: MutableCollection<Renderer<*>> = hashSetOf()
	
	override var unitWidth: Float = DEFAULT_UNITS_PER_SCREEN_WIDTH
	override var unitHeight: Float = unitWidth * window.height / window.width
	override var camera: Camera = OrthogonalCamera(unitWidth, unitHeight)
	
	
	override fun <EntityType : Entity> getRenderer(shader: Shader<EntityType>): Renderer<EntityType> {
		return Renderer(shader, this).also { renderers.add(it) }
	}
	
	override fun <EntityType : Entity> addRenderer(renderer: Renderer<EntityType>) {
		renderers.add(renderer)
	}
	
	override fun renderAll() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
		
		if (!Quad.isBound()) {
			Quad.bind()
		}
		
		for (renderer in renderers) {
			renderer.renderStoredEntities()
		}
	}
	
	
	override fun setBackgroundColor(r: Float, g: Float, b: Float) {
		GL11.glClearColor(r, g, b, 0.0f)
	}
}