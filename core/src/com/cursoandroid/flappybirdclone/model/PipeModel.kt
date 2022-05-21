package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class PipeModel(
    img: Texture,
    axisX: Float = 0f,
    axisY: Float = 0f,
    width: Float = 0f,
    height: Float = 0f
) : ElementModel(img, axisX, axisY, width, height) {

    var isAxisXReset = false
    var axisXCurrent: Float = axisX
        private set

    private var axisYCurrent: Float = axisY
    private var spaceRandom: Float = 0f

    override fun draw(batch: SpriteBatch) {
        batch.draw(img, axisXCurrent, axisYCurrent, width, height)
    }

    fun moveAxisX() {
        axisXCurrent-= Gdx.graphics.deltaTime * 150
        if(axisXCurrent < -img.width) {
            axisXCurrent = axisX
            isAxisXReset = true
        }
    }

    fun moveAxisY(spaceRandom: Float) {
        if (this.spaceRandom != spaceRandom) {
            this.spaceRandom = spaceRandom
            this.isAxisXReset = false
        }
        this.axisYCurrent = axisY + spaceRandom
    }
}