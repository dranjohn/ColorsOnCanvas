package dranjohn.math

/**
 * A vector of 2 floats with basic arithmetic operators.
 */
data class Vector2f(var x: Float = 0f, var y: Float = 0f) {
	constructor(v: Float) : this(v, v)
	
	operator fun unaryMinus() = Vector2f(-x, -y)
	
	operator fun plus(other: Vector2f) = Vector2f(x + other.x, y + other.y)
	operator fun minus(other: Vector2f) = Vector2f(x - other.x, y - other.y)
	operator fun times(scalar: Float) = Vector2f(x * scalar, y * scalar)
	operator fun div(scalar: Float) = Vector2f(x / scalar, y / scalar)
	
	operator fun plusAssign(other: Vector2f) {
		this.x += other.x
		this.y += other.y
	}
	
	operator fun minusAssign(other: Vector2f) {
		this.x -= other.x
		this.y -= other.y
	}
	
	operator fun timesAssign(scalar: Float) {
		this.x *= scalar
		this.y *= scalar
	}
	
	operator fun timesAssign(matrix: Matrix2x2f) {
		val oldX = x
		
		this.x = x * matrix[0, 0] + y * matrix[1, 0]
		this.y = oldX * matrix[0, 1] + y * matrix[1, 1]
	}
	
	operator fun divAssign(scalar: Float) {
		this.x /= scalar
		this.y /= scalar
	}
}

operator fun Float.times(vector: Vector2f) = Vector2f(vector.x * this, vector.y * this)



/**
 * A vector of 3 floats with basic arithmetic operators.
 */
data class Vector3f(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f) {
	constructor(v: Float) : this(v, v, v)
	
	operator fun unaryMinus() = Vector3f(-x, -y, -z)
	
	operator fun plus(other: Vector3f) = Vector3f(x + other.x, y + other.y, z + other.z)
	operator fun minus(other: Vector3f) = Vector3f(x - other.x, y - other.y, z - other.z)
	operator fun times(scalar: Float) = Vector3f(x * scalar, y * scalar, z * scalar)
	operator fun div(scalar: Float) = Vector3f(x / scalar, y / scalar, z / scalar)
	
	operator fun plusAssign(other: Vector3f) {
		this.x += other.x
		this.y += other.y
		this.z += other.z
	}
	
	operator fun minusAssign(other: Vector3f) {
		this.x -= other.x
		this.y -= other.y
		this.z -= other.z
	}
	
	operator fun timesAssign(scalar: Float) {
		this.x *= scalar
		this.y *= scalar
		this.z += scalar
	}
	
	operator fun divAssign(scalar: Float) {
		this.x /= scalar
		this.y /= scalar
		this.z /= scalar
	}
}
