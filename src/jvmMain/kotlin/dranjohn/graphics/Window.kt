package dranjohn.graphics

import dranjohn.graphics.entity.Model
import dranjohn.graphics.entity.TimedUpdateListener
import dranjohn.graphics.entity.UpdateListener
import dranjohn.graphics.scene.LWJGLScene
import dranjohn.graphics.scene.Scene
import dranjohn.graphics.scene.ShaderLoader
import dranjohn.graphics.texture.TextureLoader
import dranjohn.math.Vector2f
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import kotlin.math.max

actual class Window actual constructor(width: Int, height: Int, title: String) {
	var width: Int
		private set
	var height: Int
		private set
	
	private var mousePos = Vector2f(0f)
	
	private val windowHandle: Long
	private var currentScene: Scene
	
	private val updateListeners: MutableCollection<UpdateListener>
	private val timedUpdateListeners: MutableCollection<TimedUpdateListener>
	private val clickListeners: MutableCollection<ClickListener>
	private val keyListeners: MutableCollection<KeyListener>
	
	var minRenderTime: Float = 1f / 60f
	
	init {
		this.width = width
		this.height = height
		
		GLFWManager.initGLFW()
		
		GLFW.glfwDefaultWindowHints()
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
		
		this.windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0)
		
		GLFW.glfwMakeContextCurrent(windowHandle)
		GL.createCapabilities()
		
		//uncap GLFW framerate to make as many physics updates as possible
		//TODO: this should be done differently (with multi-threading)
		GLFW.glfwSwapInterval(0)
		
		this.currentScene = LWJGLScene(this)
		
		
		this.updateListeners = mutableSetOf()
		this.timedUpdateListeners = mutableSetOf()
		this.clickListeners = mutableSetOf()
		this.keyListeners = mutableSetOf()
		
		GLFW.glfwSetCursorPosCallback(windowHandle) { windowHandle, x, y ->
			run {
				if (this.windowHandle != windowHandle) {
					return@run
				}
				
				this.mousePos.x = (x.toFloat() / width - 0.5f) * 16 //TODO: move magic number 16
				this.mousePos.y = (y.toFloat() / height - 0.5f) * (16 * height / width) * -1
			}
		}
		
		GLFW.glfwSetMouseButtonCallback(windowHandle) { windowHandle, buttonCode, actionCode, _ ->
			run {
				if (this.windowHandle != windowHandle) {
					return@run
				}
				
				for (listener in clickListeners) {
					listener(mousePos.x, mousePos.y, GLFWtoCOCMouseButton(buttonCode), GLFWtoCOCButtonAction(actionCode))
				}
			}
		}
		
		GLFW.glfwSetKeyCallback(windowHandle) { windowHandle, key, _, action, _ ->
			run {
				if (this.windowHandle != windowHandle) {
					return@run
				}
				
				if (action == GLFW.GLFW_REPEAT) {
					return@run
				}
				
				for (listener in keyListeners) {listener(key, GLFWtoCOCButtonAction(action))
				}
			}
		}
	}
	
	
	actual fun show() {
		GLFW.glfwShowWindow(windowHandle)
	}
	
	actual fun hide() {
		GLFW.glfwHideWindow(windowHandle)
	}
	
	
	actual fun createScene(): Scene {
		val newScene = LWJGLScene(this)
		this.currentScene = newScene
		
		return newScene
	}
	
	actual fun getScene(): Scene {
		return currentScene
	}
	
	
	actual fun open() {
		show()
		
		GLFW.glfwSetTime(0.0)
		
		while (!GLFW.glfwWindowShouldClose(windowHandle)) {
			//update and render for predicted render time
			//TODO: adaptive predicted render time
			update(minRenderTime)
			render()
			
			//let the thread sleep until the predicted render time is reached
			runBlocking { delay(max(((minRenderTime - GLFW.glfwGetTime()) * 1000).toLong(), 0)) }
			GLFW.glfwSetTime(0.0)
			
			//display rendered frame
			GLFW.glfwSwapBuffers(windowHandle)
		}
		
		//close the window itself
		Callbacks.glfwFreeCallbacks(windowHandle)
		GLFW.glfwDestroyWindow(windowHandle)
		
		unregisterAllClickListener()
		unregisterAllKeyListener()
		
		//delete models, shaders and texture loaders in OpenGL allocated for this window
		//TODO: might be in use by another window, change cleanup call
		Model.cleanUp()
		ShaderLoader.cleanUp()
		TextureLoader.cleanUp()
		
		//terminate GLFW if needed (managed by the GLFW manager)
		GLFWManager.terminateGLFW()
	}
	
	actual fun close() {
		GLFW.glfwSetWindowShouldClose(windowHandle, true)
	}
	
	
	private fun update(deltaTime: Float) {
		//update
		GLFW.glfwPollEvents()
		
		updateListeners.forEach { it() }
		timedUpdateListeners.forEach { it(deltaTime) }
	}
	
	private fun render() {
		//render current scene
		currentScene.renderAll()
	}
	
	
	actual fun setTitle(title: String) {
		GLFW.glfwSetWindowTitle(windowHandle, title)
	}
	
	
	actual fun registerUpdateListener(listener: UpdateListener) {
		updateListeners += listener
	}
	
	actual fun registerTimedUpdateListener(listener: TimedUpdateListener) {
		timedUpdateListeners += listener
	}
	
	
	actual fun registerClickListener(listener: ClickListener) {
		clickListeners += listener
	}
	
	actual fun unregisterClickListener(listener: ClickListener) {
		clickListeners -= listener
	}
	
	actual fun unregisterAllClickListener() {
		clickListeners.clear()
	}
	
	actual fun registerKeyListener(listener: KeyListener) {
		keyListeners += listener
	}
	
	actual fun unregisterKeyListener(listener: KeyListener) {
		keyListeners -= listener
	}
	
	actual fun unregisterAllKeyListener() {
		keyListeners.clear()
	}
	
	
	private object GLFWManager {
		private var windowInstances: Int = 0
		
		fun initGLFW() {
			if (windowInstances == 0) {
				GLFW.glfwInit()
			}
			
			windowInstances++
		}
		
		fun terminateGLFW() {
			windowInstances--
			
			if (windowInstances == 0) {
				GLFW.glfwTerminate()
			}
		}
	}
}