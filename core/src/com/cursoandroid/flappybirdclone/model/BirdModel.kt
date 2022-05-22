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
    val circle = Circle()
    private val imgs: ArrayList<Texture>
    private var axisXCurrent = axisX
    private var axisYCurrent = axisY

    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch) {
        val image = imgs[index.toInt()]
        batch.draw(image, axisXCurrent, axisYCurrent)
        val halfHeight = image.height / 2
        val halfWidth = image.width / 2
        circle.set(axisXCurrent + halfWidth, axisYCurrent + halfHeight, halfHeight.toFloat())
    }

    fun animate() {
        index += Gdx.graphics.deltaTime * 5
    }

    fun applyGravity(isTouched: Boolean) {
        if (isTouched) {
            gravity = -15f
        }

        if (axisYCurrent > 0 || isTouched) {
            axisYCurrent -= gravity
        }

        gravity++
    }

    fun dispose() {
        imgs.forEach { it.dispose() }
    }

    fun reset() {
        this.axisXCurrent = this.axisX
        this.axisYCurrent = this.axisY
    }

    private fun fillBirdImagens(): ArrayList<Texture> {
        val birdImgs = ArrayList<Texture>(3)
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
        return birdImgs
    }
}