package dranjohn.graphics.scene

import dranjohn.graphics.entity.Entity
import dranjohn.graphics.texture.Texture
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f
import dranjohn.utils.FileLoader
import org.lwjgl.opengl.GL20

actual object ShaderLoader {
	private const val shaderResourceFolder = "/shaders/"
	private val shaders: MutableCollection<Int> = hashSetOf()
	
	actual fun <EntityType : Entity> loadShader(name: String): Shader<EntityType> {
		return LWJGLShader(name)
	}
	
	
	private class LWJGLShader<EntityType : Entity> constructor(shaderName: String) : Shader<EntityType> {
		private val shaderProgramId: Int = GL20.glCreateProgram()
		private val entityLoaders: MutableCollection<(EntityType) -> Unit> = hashSetOf()
		private val entityTextures: MutableCollection<Pair<String, (EntityType) -> Texture>> = hashSetOf()
		
		init {
			GL20.glAttachShader(shaderProgramId, loadShaderFile("$shaderResourceFolder$shaderName/vertex.glsl",
					GL20.GL_VERTEX_SHADER))
			GL20.glAttachShader(shaderProgramId, loadShaderFile("$shaderResourceFolder$shaderName/fragment.glsl",
					GL20.GL_FRAGMENT_SHADER))
			GL20.glLinkProgram(shaderProgramId)
			
			shaders += shaderProgramId
		}
		
		private fun loadShaderFile(shaderFile: String, shaderType: Int): Int {
			val shader = GL20.glCreateShader(shaderType)
			
			GL20.glShaderSource(shader, FileLoader.loadResourceFileContent(shaderFile))
			GL20.glCompileShader(shader)
			
			return shader
		}
		
		
		override fun bind() {
			if (ShaderTracker.currentShader !== this) {
				GL20.glUseProgram(shaderProgramId)
				ShaderTracker.currentShader = this
			}
		}
		
		override fun unbind() {
			if (ShaderTracker.currentShader === this) {
				GL20.glUseProgram(0)
				ShaderTracker.currentShader = null
			}
		}
		
		
		override fun loadSceneData(scene: Scene) {
			GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(shaderProgramId,"projection"), true, scene.camera.getProjection().values)
		}
		
		override fun loadEntityData(entity: EntityType) {
			GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(shaderProgramId,"transformation"), true, floatArrayOf(
				entity.size.x, 0f, 0f, entity.position.x,
				0f, entity.size.y, 0f, entity.position.y,
				0f, 0f, 1f, 0f,
				0f, 0f, 0f, 1f
			))
			
			for (entityLoader in entityLoaders) {
				entityLoader(entity)
			}
			
			var textureId = 0
			for ((textureName, textureLoader) in entityTextures) {
				GL20.glUniform1i(GL20.glGetUniformLocation(shaderProgramId, textureName), textureId)
				GL20.glActiveTexture(GL20.GL_TEXTURE0 + textureId)
				GL20.glBindTexture(GL20.GL_TEXTURE_2D, textureLoader(entity).id)
				
				textureId++
			}
		}
		
		
		override fun addEntityProperty1b(name: String, property: (EntityType) -> Boolean) {
			entityLoaders.add { entity -> GL20.glUniform1f(GL20.glGetUniformLocation(shaderProgramId, name),
				property(entity).let { if (it) 1f else 0f })}
		}
		
		override fun addEntityProperty1f(name: String, property: (EntityType) -> Float) {
			entityLoaders.add { entity -> GL20.glUniform1f(GL20.glGetUniformLocation(shaderProgramId, name),
				property(entity)) }
		}
		
		override fun addEntityProperty2f(name: String, property: (EntityType) -> Vector2f) {
			entityLoaders.add { entity -> property(entity).let {
				GL20.glUniform2f(GL20.glGetUniformLocation(shaderProgramId, name), it.x, it.y)
			}}
		}
		
		override fun addEntityProperty3f(name: String, property: (EntityType) -> Vector3f) {
			entityLoaders.add { entity -> property(entity).let {
				GL20.glUniform3f(GL20.glGetUniformLocation(shaderProgramId, name), it.x, it.y, it.z)
			}}
		}
		
		
		override fun addEntityTexture2D(name: String, texture: (EntityType) -> Texture) {
			entityTextures.add(Pair (name, texture))
		}
		
		
		private object ShaderTracker {
			var currentShader: LWJGLShader<*>? = null
		}
	}
	
	
	fun cleanUp() {
		GL20.glUseProgram(0)
		for (shaderProgram in shaders) {
			GL20.glDeleteProgram(shaderProgram)
		}
	}
}