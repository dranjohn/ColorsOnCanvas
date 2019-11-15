package dranjohn.main.blitz

import dranjohn.graphics.texture.Texture
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f

open class MaskedColoredEntity(position: Vector2f = Vector2f(), size: Vector2f = Vector2f(1f), color: Vector3f = Vector3f(), var mask: Texture) : ColoredEntity(position, size, color)