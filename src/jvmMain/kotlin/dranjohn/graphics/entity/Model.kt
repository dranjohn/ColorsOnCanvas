package dranjohn.graphics.entity

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer

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
        
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bufferData(data), GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(index, dataPointSize, GL11.GL_FLOAT, false, 0, 0)
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }
    
    private fun bufferData(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        
        return buffer
    }
    
    
    fun bind() {
        GL30.glBindVertexArray(vaoId)
    }

    fun unbind() {
        GL30.glBindVertexArray(0)
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
}