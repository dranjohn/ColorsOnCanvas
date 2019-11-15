package dranjohn.main

import dranjohn.graphics.ButtonAction
import dranjohn.graphics.Window
import dranjohn.graphics.scene.ShaderLoader
import dranjohn.graphics.texture.TextureLoader
import dranjohn.main.blitz.*
import dranjohn.math.Vector2f
import dranjohn.math.Vector3f

//purple: 0.37, 0.2, 0.64

//red: 1, 0.31,  0.31
//yellow: 0.9, 1, 0.33
//blue: 0.19, 0.4, 0.745
//green: 0.07, 0.27, 0.05

/**
 * The entry point to the test application. Will be removed once the API is usable.
 */
fun main() {
	//Create a new window.
	val window = Window()
	
	//Initialize shader for (masked) single color
	val singleColorShader = ShaderLoader.loadShader<ColoredEntity>("singleColor")
	singleColorShader.addEntityProperty3f("color") { entity -> entity.color }
	val maskedColorShader = ShaderLoader.loadShader<MaskedColoredEntity>("maskedSingleColor")
	maskedColorShader.addEntityProperty3f("color") { entity -> entity.color }
	maskedColorShader.addEntityTexture2D("mask") { entity -> entity.mask }
	
	//Create a new scene.
	val windowScene = window.getScene()
	windowScene.setBackgroundColor(0.4f)
	
	//Create renderers for both shaders.
	val singleColorRenderer = windowScene.getRenderer(singleColorShader)
	val colorRenderer = windowScene.getRenderer(maskedColorShader)
	
	//Load the border texture for masked color entities.
	val border = TextureLoader.loadTexture("border")
	
	//Place a ground of solid black.
	val ground = ColoredEntity(Vector2f(0f, windowScene.unitHeight * -0.375f), Vector2f(windowScene.unitWidth, windowScene.unitHeight * 0.25f))
	ground.color = Vector3f(0.05f)
	singleColorRenderer.storeEntity(ground)
	
	//Create the player.
	val player = Player(position = Vector2f(0f, windowScene.unitHeight * -0.25f + 0.5f), color = Vector3f(0.37f, 0.2f, 0.64f), mask = border)
	colorRenderer.storeEntity(player)
	
	//Register all update listeners (FPS display, player controls and exit).
	window.registerTimedUpdateListener { deltaTime -> window.setTitle("DeltaTime: $deltaTime") }
	
	window.registerTimedUpdateListener { deltaTime -> player.updatePhysics(deltaTime) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 65 && buttonAction == ButtonAction.PRESS) player.startAction(PlayerAction.MOVE_LEFT) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 68 && buttonAction == ButtonAction.PRESS) player.startAction(PlayerAction.MOVE_RIGHT) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 87 && buttonAction == ButtonAction.PRESS) player.startAction(PlayerAction.JUMP) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 65 && buttonAction == ButtonAction.RELEASE) player.stopAction(PlayerAction.MOVE_LEFT) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 68 && buttonAction == ButtonAction.RELEASE) player.stopAction(PlayerAction.MOVE_RIGHT) }
	window.registerKeyListener { keyCode, buttonAction -> if (keyCode == 87 && buttonAction == ButtonAction.RELEASE) player.stopAction(PlayerAction.JUMP) }
	
	window.registerKeyListener { keyCode, _ -> if (keyCode == 256) window.close() }
	
	//Open the window.
	//TODO: The window should open in a coroutine to not block the main thread.
	window.open()
}
