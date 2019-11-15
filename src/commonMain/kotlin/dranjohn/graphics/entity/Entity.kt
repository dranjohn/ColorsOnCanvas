package dranjohn.graphics.entity

import dranjohn.graphics.scene.Renderer
import dranjohn.graphics.scene.Shader
import dranjohn.math.Vector2f

/**
 * An entity which may be rendered by a [Renderer] using a [Shader]. A position and a size are attributes in [Vector2f]
 * format all entities possess.
 */
open class Entity(var position: Vector2f = Vector2f(), var size: Vector2f = Vector2f(1f))