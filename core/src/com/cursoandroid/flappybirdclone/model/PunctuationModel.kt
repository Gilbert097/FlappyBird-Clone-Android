package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class PunctuationModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    val bitmapFont: BitmapFont = BitmapFont(),
    var value: Int = 0,
    val fontSize: Int = 10,
    val color: Color = Color.WHITE
) : Coordinate(axisX, axisY) {

    init {
        bitmapFont.color = color
        bitmapFont.data.scale(fontSize.toFloat())
    }

    fun draw(batch: SpriteBatch){
        bitmapFont.draw(batch, value.toString(), axisX, axisY)
    }
}