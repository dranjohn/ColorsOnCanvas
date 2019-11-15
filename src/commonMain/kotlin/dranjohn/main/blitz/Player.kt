package dranjohn.main.blitz

import dranjohn.graphics.texture.Texture
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f

enum class PlayerAction {
	MOVE_LEFT,
	MOVE_RIGHT,
	JUMP
}

private const val GRAVITY = -24f
private const val SPEED = 4f

class Player(position: Vector2f = Vector2f(), size: Vector2f = Vector2f(1f), color: Vector3f = Vector3f(), mask: Texture) : MaskedColoredEntity(position, size, color, mask) {
	private var velocity = Vector2f()
	private val groundLevel = position.y
	
	private var jumping = false
	
	fun startAction(action: PlayerAction) {
		when (action) {
			PlayerAction.MOVE_LEFT -> velocity.x -= SPEED
			PlayerAction.MOVE_RIGHT -> velocity.x += SPEED
			PlayerAction.JUMP -> jumping = true
		}
	}
	
	fun stopAction(action: PlayerAction) {
		when (action) {
			PlayerAction.MOVE_LEFT -> velocity.x += SPEED
			PlayerAction.MOVE_RIGHT -> velocity.x -= SPEED
			PlayerAction.JUMP -> jumping = false
		}
	}
	
	
	fun updatePhysics(deltaTime: Float) {
		val deltaTimeSquared = deltaTime * deltaTime
		position.plusAssign(velocity * deltaTime)
		position.y += 0.5f * GRAVITY * deltaTimeSquared
		
		if (position.y <= groundLevel) {
			velocity.y = 0f
			position.y = groundLevel
			
			if (jumping) {
				velocity.y = 12f
			}
		} else {
			velocity.y += GRAVITY * deltaTime
		}
	}
}
