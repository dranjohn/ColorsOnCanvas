plugins {
	kotlin("multiplatform") version "1.3.60"
}

repositories {
	mavenCentral()
}

val lwjglVersion = "3.2.2"
val jomlVersion = "1.9.17"
val lwjglNatives = "natives-windows"

kotlin {
	jvm()
	
	sourceSets {
		getting {
			dependencies {
				implementation(kotlin("stdlib-common"))
				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
			}
		}
		
		jvm().compilations["main"].defaultSourceSet {
			dependencies {
				implementation(kotlin("stdlib-jdk8"))
				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
				
				//lwjgl with windows natives
				implementation("org.lwjgl:lwjgl:$lwjglVersion")
				implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
				implementation("org.lwjgl:lwjgl-openal:$lwjglVersion")
				implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")
				implementation("org.lwjgl:lwjgl-stb:$lwjglVersion")
				runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-openal:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-opengl:$lwjglVersion:$lwjglNatives")
				runtimeOnly("org.lwjgl:lwjgl-stb:$lwjglVersion:$lwjglNatives")
			}
		}
	}
}