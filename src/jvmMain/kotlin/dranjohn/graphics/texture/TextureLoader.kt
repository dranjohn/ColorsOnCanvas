package dranjohn.graphics.texture

import dranjohn.utils.FileLoader
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import javax.imageio.ImageIO

actual object TextureLoader {
	private val textures: MutableCollection<Int> = hashSetOf()
	
	actual fun loadTexture(name: String, interpolation: Interpolation): Texture {
		val pixels: IntArray
		val textureWidth: Int
		val textureHeight: Int
		
		try {
			val path = "/textures/$name.png"
			val image = ImageIO.read(FileLoader.loadResourceFileStream(path))
			
			textureWidth = image.width
			textureHeight = image.height
			
			pixels = IntArray(textureWidth * textureWidth)
			
			image.getRGB(0, 0, textureWidth, textureWidth, pixels, 0, textureWidth)
		} catch (fileReadException: IOException) {
			TODO("log error")
		}
		
		val data = IntArray(textureWidth * textureHeight)
		
		for (i in 0 until textureWidth * textureHeight) {
			val a = pixels[i] and -0x1000000 shr 24
			val r = pixels[i] and 0xff0000 shr 16
			val g = pixels[i] and 0xff00 shr 8
			val b = pixels[i] and 0xff
			
			data[i] = a shl 24 or (b shl 16) or (g shl 8) or r
		}
		
		val buffer = ByteBuffer.allocateDirect(data.size shl 2).order(ByteOrder.nativeOrder()).asIntBuffer()
		buffer.put(data).flip()
		
		val textureId = GL11.glGenTextures()
		
		val textureLookup: Int = when (interpolation) {
			Interpolation.LINEAR -> GL11.GL_LINEAR
			Interpolation.NEAREST -> GL11.GL_NEAREST
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId)
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, textureLookup)
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, textureLookup)
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureWidth, textureHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
		
		textures += textureId
		return Texture(textureId, textureWidth, textureHeight)
	}
	
	fun cleanUp() {
		for (texture in textures) {
			GL20.glDeleteTextures(texture)
		}
	}
}