package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle

class BirdModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    var gravity: Float = 0f,
) : Coordinate(axisX, axisY) {
    var index: Float = 0F
        get() = if (field >= 3) 0F else field
    private val imgs: ArrayList<Texture>
    val circle = Circle()

    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch) {
        val image = imgs[index.toInt()]
        batch.draw(image, axisX, axisY)
        val halfHeight = image.height/2
        val halfWidth = image.width/2
        circle.set(axisX + halfWidth, axisY + halfHeight, halfHeight.toFloat())
    }

    fun animate() {
        index += Gdx.graphics.deltaTime * 5
    }

    fun applyGravity(isTouched: Boolean) {
        if (isTouched) {
            gravity = -15f
        }

        if (axisY > 0 || isTouched) {
            axisY -= gravity
        }

        gravity++
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