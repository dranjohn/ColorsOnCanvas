package dranjohn.graphics.scene

import dranjohn.graphics.entity.Entity

/**
 * Stores and renders a given type of [Entity]s.
 */
expect class Renderer<EntityType : Entity> {
	/**
	 * Puts one [Entity] in this renderers entity storage.
	 */
	fun storeEntity(entity: EntityType)
	
	/**
	 * Renders a single [Entity].
	 */
	fun renderEntity(entity: EntityType)
	
	
	/**
	 * Render all entities in this renderers [Entity] storage by calling [renderEntity] for each of them.
	 */
	fun renderStoredEntities()
}