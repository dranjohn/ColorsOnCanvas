package dranjohn.graphics.entity

import org.lwjgl.opengl.*

open class Model(positions: FloatArray) {
	private val vaoId: Int = GL30.glGenVertexArrays()
	val vertexCount: Int
	
	init {
		require(positions.size % 2 == 0)
		vaos += vaoId
		
		bind()
		storeInArrayBuffer(0, positions, 2)
		unbind()
		
		this.vertexCount = positions.size / 2
	}
	
	private fun storeInArrayBuffer(index: Int, data: FloatArray, dataPointSize: Int) {
		val vbo = GL15.glGenBuffers()
		vbos += vbo
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW)
		GL20.glVertexAttribPointer(index, dataPointSize, GL11.GL_FLOAT, false, 0, 0)
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
	}
	
	private fun allocateInstanceArrayBuffer(floatCapacities: IntArray, maxInstances: Int) {
		//check if all requirement for instance buffer
		require(floatCapacities.isNotEmpty())
		require(floatCapacities.all { it > 0 })
		
		require(maxInstances >= 2)
		
		
		//allocate buffer with required capacity
		val vbo = GL15.glGenBuffers()
		vbos += vbo
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, (floatCapacities.sum() * maxInstances).toLong() * 4, GL15.GL_STREAM_DRAW)
		
		//bind VAO and set attribute pointers
		GL30.glBindVertexArray(vaoId)
		
		var attributeIndex = 1
		var offset: Long = 0
		
		for (floatCapacity in floatCapacities) {
			GL20.glVertexAttribPointer(attributeIndex, floatCapacity, GL11.GL_FLOAT, false, floatCapacity * 4, offset * 4)
			GL33.glVertexAttribDivisor(attributeIndex, 1)
			
			offset += floatCapacity
			attributeIndex++
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
		GL30.glBindVertexArray(0)
	}
	
	
	fun bind() {
		GL30.glBindVertexArray(vaoId)
		ModelTracker.currentModel = this
	}
	
	fun unbind() {
		GL30.glBindVertexArray(0)
		ModelTracker.currentModel = null
	}
	
	fun isBound(): Boolean {
		return ModelTracker.currentModel === this
	}
	
	
	companion object BuffersTracker {
		private val vaos: MutableCollection<Int> = hashSetOf()
		private val vbos: MutableCollection<Int> = hashSetOf()
		
		fun cleanUp() {
			for (vbo in vbos) {
				GL15.glDeleteBuffers(vbo)
			}
			
			for (vao in vaos) {
				GL30.glDeleteVertexArrays(vao)
			}
		}
	}
	
	private object ModelTracker {
		var currentModel: Model? = null
	}
}