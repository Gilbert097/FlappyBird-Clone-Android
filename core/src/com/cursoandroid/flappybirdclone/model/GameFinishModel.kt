package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class GameFinishModel(
    val imageModel: ElementModel
//    val resetModel: TextElementModel,
//    val bestScoreModel: TextElementModel
) {

    fun draw(batch: SpriteBatch) {
        imageModel.draw(batch)
    }
}