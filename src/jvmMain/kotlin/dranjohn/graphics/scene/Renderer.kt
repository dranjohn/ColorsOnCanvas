package dranjohn.graphics.scene

import dranjohn.graphics.entity.Entity
import dranjohn.graphics.entity.Quad
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

actual class Renderer<EntityType : Entity>(private val shader: Shader<EntityType>, scene: Scene) {
	private val entities: MutableCollection<EntityType> = hashSetOf()
	
	
	init {
		shader.bind()
		shader.loadSceneData(scene)
		shader.unbind()
	}
	
	
	actual fun storeEntity(entity: EntityType) {
		entities += entity
	}
	
	actual fun renderEntity(entity: EntityType) {
		shader.bind()
		
		GL20.glEnableVertexAttribArray(0)
		shader.loadEntityData(entity)
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Quad.vertexCount)
		
		GL20.glDisableVertexAttribArray(0)
	}
	
	
	actual fun renderStoredEntities() {
		for (entity in entities) {
			renderEntity(entity)
		}
	}
}