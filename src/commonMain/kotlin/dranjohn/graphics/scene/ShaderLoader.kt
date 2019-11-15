package dranjohn.graphics.scene

import dranjohn.graphics.entity.Entity

/**
 * Expects an implementation for loading [Shader]s.
 */
expect object ShaderLoader {
	/**
	 * Loads a [Shader] from the ressource files under the given name. It is expected that the [Shader] can render
	 * entities of the given [EntityType].
	 */
	fun <EntityType : Entity> loadShader(name: String): Shader<EntityType>
}