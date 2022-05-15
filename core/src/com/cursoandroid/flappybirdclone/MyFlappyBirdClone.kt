package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import javax.swing.Spring

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel

    override fun create() {
        batch = SpriteBatch()
        model = FlappyBirdModel(
            width = Gdx.graphics.width.toFloat(),
            height = Gdx.graphics.height.toFloat(),
            axisX = 30F,
            axisY = Gdx.graphics.height.toFloat() / 2,
            gravity = 0F
        )
    }

    override fun render() {
        batch.begin()

        model.applyGravity(Gdx.input.justTouched())
        batch.draw(
            model.backgroundImg,
            0f,
            0f,
            model.width,
            model.height
        )
        batch.draw(
            model.birdImgs[model.index.toInt()],
            model.axisX,
            model.axisY
        )
        model.next()

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        model.dispose()
    }
}

private class FlappyBirdModel(
    val width: Float,
    val height: Float,
    val axisX: Float,
    var axisY: Float,
    var gravity: Float
) {
    val birdImgs = ArrayList<Texture>(3)
    val backgroundImg: Texture = Texture("fundo.png")
    var index: Float = 0F
        get() = if (field > 3) 0F else field

    init {
        fillBirdImagens()
    }

    private fun fillBirdImagens() {
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
    }

    fun dispose() {
        backgroundImg.dispose()
        birdImgs.forEach { it.dispose() }
    }

    fun next() {
        index += Gdx.graphics.deltaTime * 5
        gravity++
    }

    fun applyGravity(isTouched: Boolean) {

        if (isTouched) {
            gravity = -20f
        }

        if (axisY > 0 || isTouched) {
            axisY -= gravity
        }
    }

}