package dranjohn.main.blitz

import dranjohn.graphics.entity.Entity
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f

open class ColoredEntity(position: Vector2f = Vector2f(), size: Vector2f = Vector2f(1f), var color: Vector3f = Vector3f()) : Entity(position, size)