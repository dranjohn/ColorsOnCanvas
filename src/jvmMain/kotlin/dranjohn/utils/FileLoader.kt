package dranjohn.utils

import java.io.FileInputStream
import java.io.InputStream

object FileLoader {
	fun loadResourceFileStream(filePath: String): InputStream = FileLoader::class.java.getResourceAsStream(filePath)
	fun loadResourceFileContent(filePath: String): String = FileLoader::class.java.getResource(filePath).readText()
}