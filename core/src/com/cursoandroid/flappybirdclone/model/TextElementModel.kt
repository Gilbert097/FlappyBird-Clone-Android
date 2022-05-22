package com.cursoandroid.flappybirdclone.model
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

open class TextElementModel(
    private val text: BitmapFont = BitmapFont(),
    var value: String = "",
    color: Color = Color.BLACK,
    fontSize: Int = 10,
    axisX: Float = 0f,
    axisY: Float = 0f,
) : Coordinate(axisX, axisY) {

    init{
        text.color = color
        text.data.scale(fontSize.toFloat())
    }

    open fun draw(batch: SpriteBatch) {
        text.draw(batch, value, axisX, axisY)
    }

    fun dispose() {
        text.dispose()
    }
}