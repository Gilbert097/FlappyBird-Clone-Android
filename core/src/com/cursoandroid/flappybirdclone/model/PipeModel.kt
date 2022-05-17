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

    private var axisXTemp: Float = axisX
    private var axisYTemp: Float = axisY
    var isAxisXReset = false

    override fun draw(batch: SpriteBatch) {
        batch.draw(img, axisXTemp, axisYTemp, width, height)
    }

    fun moveAxisX() {
        axisXTemp-= Gdx.graphics.deltaTime * 150
        if(axisXTemp < -img.width) {
            axisXTemp = axisX
            isAxisXReset = true
        }
    }

    fun moveAxisY(spaceRandom: Float) {
        axisYTemp = axisY + spaceRandom
    }
}