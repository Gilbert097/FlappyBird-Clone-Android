package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class PunctuationModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    private val bitmapFont: BitmapFont = BitmapFont(),
    private var value: Int = 0,
    fontSize: Int = 10,
    color: Color = Color.WHITE
) : Coordinate(axisX, axisY) {
    var isIncrementedValue = false
        private set

    init {
        bitmapFont.color = color
        bitmapFont.data.scale(fontSize.toFloat())
    }

    fun draw(batch: SpriteBatch) {
        bitmapFont.draw(batch, value.toString(), axisX, axisY)
    }

    fun incrementValue(){
        isIncrementedValue = true
        value++
    }

    fun reset(){
        isIncrementedValue = false
    }
}