package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class GameFinishModel(
    val imageModel: ElementModel,
    val resetModel: TextElementModel,
    val bestScoreModel: TextElementModel
) {

    fun draw(batch: SpriteBatch) {
        imageModel.draw(batch)
        resetModel.draw(batch)
        bestScoreModel.draw(batch)
    }

    fun dispose(){
        imageModel.dispose()
        resetModel.dispose()
        bestScoreModel.dispose()
    }
}