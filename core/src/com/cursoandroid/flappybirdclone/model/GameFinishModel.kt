package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class GameFinishModel(
    val imageModel: ElementModel,
    val resetModel: TextElementModel,
    val bestScoreModel: TextElementModel
) {
    companion object {
        private const val BEST_SCORE_TEMPLATE = "Seu recorde é: %s pontos"
    }

    fun draw(batch: SpriteBatch, bestScore: Int) {
        imageModel.draw(batch)
        resetModel.draw(batch)
        bestScoreModel.value = String.format(BEST_SCORE_TEMPLATE, bestScore)
        bestScoreModel.draw(batch)
    }

    fun dispose(){
        imageModel.dispose()
        resetModel.dispose()
        bestScoreModel.dispose()
    }
}