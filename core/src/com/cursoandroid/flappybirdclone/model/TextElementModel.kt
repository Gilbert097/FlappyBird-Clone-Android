package com.cursoandroid.flappybirdclone.model
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

open class TextElementModel(
    val text: BitmapFont,
    val value: String,
    axisX: Float = 0f,
    axisY: Float = 0f,
) : Coordinate(axisX, axisY) {

    open fun draw(batch: SpriteBatch) {
        text.draw(batch, value, axisX, axisY)
    }

    fun dispose() {
        text.dispose()
    }
}