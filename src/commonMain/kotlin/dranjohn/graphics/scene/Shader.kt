package dranjohn.graphics.scene

import dranjohn.graphics.entity.Entity
import dranjohn.graphics.texture.Texture
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f

/**
 * A shader program for a specific sub-type of [Entity]s.
 */
interface Shader<EntityType : Entity> {
	/**
	 * Binds this shader for use.
	 */
	fun bind()
	
	/**
	 * Unbinds this shader if it is currently used.
	 */
	fun unbind()
	
	
	/**
	 * Loads the data for a [Scene] to this shader. Needs to be done once before rendering the first [Entity], and after
	 * every time the [Scene] has been swapped.
	 *
	 * @param scene - the [Scene] to load the data of into this shader program. This data stays loaded until another
	 * scene is loaded.
	 */
	fun loadSceneData(scene: Scene)
	
	/**
	 * Loads the data for a specific [Entity] to this shader. Needs to be done once before rendering the [Entity].
	 *
	 * @param entity - the [Entity] to load the data of into this shader program. This data stays loaded until another
	 * entity is loaded.
	 */
	fun loadEntityData(entity: EntityType)
	
	
	/**
	 * Adds a [Boolean] property to this shader.
	 *
	 * These properties will be requested from each [Entity] when loading their data using [loadEntityData] and passed
	 * on to the render pipeline under the given name.
	 *
	 * @param name - the name of this property.
	 * @param property - a function for retrieving the data from the [Entity] of the [EntityType] this shader renders.
	 */
	fun addEntityProperty1b(name: String, property: (EntityType) -> Boolean)
	
	/**
	 * Adds a [Float] property to this shader.
	 *
	 * These properties will be requested from each [Entity] when loading their data using [loadEntityData] and passed
	 * on to the render pipeline under the given name.
	 *
	 * @param name - the name of this property.
	 * @param property - a function for retrieving the data from the [Entity] of the [EntityType] this shader renders.
	 */
	fun addEntityProperty1f(name: String, property: (EntityType) -> Float)
	
	/**
	 * Adds a [Vector2f] property to this shader.
	 *
	 * These properties will be requested from each [Entity] when loading their data using [loadEntityData] and passed
	 * on to the render pipeline under the given name.
	 *
	 * @param name - the name of this property.
	 * @param property - a function for retrieving the data from the [Entity] of the [EntityType] this shader renders.
	 */
	fun addEntityProperty2f(name: String, property: (EntityType) -> Vector2f)
	
	/**
	 * Adds a [Vector3f] property to this shader.
	 *
	 * These properties will be requested from each [Entity] when loading their data using [loadEntityData] and passed
	 * on to the render pipeline under the given name.
	 *
	 * @param name - the name of this property.
	 * @param property - a function for retrieving the data from the [Entity] of the [EntityType] this shader renders.
	 */
	fun addEntityProperty3f(name: String, property: (EntityType) -> Vector3f)
	
	
	/**
	 * Adds a [Texture] to this shader.
	 *
	 * These textures will be requested from each [Entity] when loading their data using [loadEntityData] and passed
	 * on to the render pipeline under the given name.
	 *
	 * @param name - the name of this property.
	 * @param texture - a function for retrieving the data from the [Entity] of the [EntityType] this shader renders.
	 */
	fun addEntityTexture2D(name: String, texture: (EntityType) -> Texture)
}