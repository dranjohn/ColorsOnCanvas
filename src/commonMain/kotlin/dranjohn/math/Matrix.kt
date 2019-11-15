package dranjohn.math

/**
 * A matrix of 2x2 floats. Can be multiplied with [Vector2f] and other [Matrix2x2f]s.
 */
class Matrix2x2f(a11: Float, a12: Float, a21: Float, a22: Float) {
	constructor(value: Float) : this(value, value, value, value)
	constructor() : this(0f)
	
	private var values: FloatArray = floatArrayOf(a11, a12, a21, a22)
	
	operator fun get(r: Int, c: Int): Float {
		return values[r + 2 * c]
	}
	
	operator fun set(r: Int, c: Int, value: Float) {
		values[r + 2 * c] = value
	}
	
	operator fun times(vector: Vector2f): Vector2f = Vector2f(
		values[0] * vector.x + values[1] * vector.y,
		values[2] * vector.x + values[3] * vector.y
	)
	
	operator fun times(matrix: Matrix2x2f): Matrix2x2f = Matrix2x2f(
		this[0, 0] * matrix[0, 0] + this[0, 1] * matrix[1, 0], this[0, 0] * matrix[0, 1] + this[0, 1] * matrix[1, 1],
		this[1, 0] * matrix[0, 0] + this[1, 1] * matrix[1, 0], this[1, 0] * matrix[0, 1] + this[1, 1] * matrix[1, 1]
	)
}

/**
 * A matrix of 4x4 floats.
 */
class Matrix4x4f(values: FloatArray) {
	var values: FloatArray
		private set
	
	constructor() : this(FloatArray(16))
	
	init {
		require(values.size == 16)
		this.values = values
	}
	
	operator fun get(r: Int, c: Int): Float {
		return values[r + 4 * c]
	}
	
	operator fun set(r: Int, c: Int, value: Float) {
		values[r + 4 * c] = value
	}
}
