package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel

    override fun create() {
        batch = SpriteBatch()
        val background = ElementModel(
            img = Texture("fundo.png"),
            width = Gdx.graphics.width.toFloat(),
            height = Gdx.graphics.height.toFloat()
        )

        val bird = BirdModel(
            axisX = 30F,
            axisY = Gdx.graphics.height.toFloat() / 2,
        )

        model = FlappyBirdModel(
            background = background,
            bird = bird,
            gravity = 0F
        )
    }

    override fun render() {
        batch.begin()

        model.applyGravity(Gdx.input.justTouched())

        model.draw(batch)

        model.next()

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        model.dispose()
    }


}

private class FlappyBirdModel(
    val background: ElementModel,
    val bird: BirdModel,
    var gravity: Float
) {
    val pipeTop = Texture("cano_topo_maior.png")
    val pipeBottom = Texture("cano_baixo_maior.png")

    var index: Float = 0F
        get() = if (field > 3) 0F else field


    fun dispose() {
        background.dispose()
        bird.dispose()
    }

    fun next() {
        index += Gdx.graphics.deltaTime * 5
        gravity++
    }

    fun applyGravity(isTouched: Boolean) {

        if (isTouched) {
            gravity = -20f
        }

        if (bird.axisY > 0 || isTouched) {
            bird.axisY -= gravity
        }
    }

    fun draw(batch: SpriteBatch) {
        background.draw(batch)
        bird.draw(batch, index)
    }
}

private class BirdModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
) : Coordinate(axisX, axisY) {
    private val imgs: ArrayList<Texture>

    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch, index: Float) {
        batch.draw(imgs[index.toInt()], axisX, axisY)
    }

    fun dispose() {
        imgs.forEach { it.dispose() }
    }

    private fun fillBirdImagens(): ArrayList<Texture> {
        val birdImgs = ArrayList<Texture>(3)
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
        return birdImgs
    }
}

private open class ElementModel(
    val img: Texture,
    axisX: Float = 0f,
    axisY: Float = 0f,
    width: Float = 0f,
    height: Float = 0f
) : Coordinate(axisX, axisY, width, height) {

    open fun draw(batch: SpriteBatch) {
        batch.draw(img, axisX, axisY, width, height)
    }

    fun dispose() {
        img.dispose()
    }
}

private open class Coordinate(
    val axisX: Float = 0f,
    var axisY: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
)