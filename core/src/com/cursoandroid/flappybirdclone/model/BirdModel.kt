package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class BirdModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    var gravity: Float = 0f,
) : Coordinate(axisX, axisY) {
    var index: Float = 0F
        get() = if (field >= 3) 0F else field
    private val imgs: ArrayList<Texture>

    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch) {
        batch.draw(imgs[index.toInt()], axisX, axisY)
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