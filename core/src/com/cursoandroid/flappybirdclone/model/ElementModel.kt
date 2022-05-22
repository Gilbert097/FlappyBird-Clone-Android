package com.cursoandroid.flappybirdclone.model
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

open class ElementModel(
    val img: Texture,
    axisX: Float = 0f,
    axisY: Float = 0f,
    width: Float = 0f,
    height: Float = 0f
) : Coordinate(axisX, axisY, width, height) {

    open fun draw(batch: SpriteBatch) {
        if(width > 0 && height > 0) {
            batch.draw(img, axisX, axisY, width, height)
        } else {
            batch.draw(img, axisX, axisY)
        }
    }

    fun dispose() {
        img.dispose()
    }
}