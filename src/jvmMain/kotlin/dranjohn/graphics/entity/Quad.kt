package dranjohn.graphics.entity

private val QUAD_VERTICES = floatArrayOf(
	-0.5f, 0.5f,	-0.5f, -0.5f,	0.5f, -0.5f,
	0.5f, -0.5f,	0.5f, 0.5f,		-0.5f, 0.5f
)

object Quad: Model(QUAD_VERTICES)