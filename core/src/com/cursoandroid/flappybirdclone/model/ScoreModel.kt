package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class ScoreModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    private val bitmapFont: BitmapFont = BitmapFont(),
    fontSize: Int = 10,
    color: Color = Color.WHITE
) : Coordinate(axisX, axisY) {

    var isIncrementedValue = false
        private set

    var value: Int = 0
        private set

    private val fileSound = Gdx.files.internal("som_pontos.wav")
    private val incrementValueSound = Gdx.audio.newSound(fileSound)

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
        incrementValueSound.play()
    }

    fun resetIncrementControl(){
        isIncrementedValue = false
    }

    fun dispose() {
        bitmapFont.dispose()
    }

    fun reset(){
        value = 0
    }
}