package dranjohn.graphics.texture

/**
 * An enum for the types of interpolation which may be used by the texture.
 */
enum class Interpolation {
	LINEAR,
	NEAREST
}

/**
 * Expects an implementation for loading [Texture]s.
 */
expect object TextureLoader {
	/**
	 * Loads the texture under the given name from the resource folder.
	 */
	fun loadTexture(name: String, interpolation: Interpolation = Interpolation.LINEAR): Texture
}