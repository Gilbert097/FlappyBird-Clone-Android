package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.cursoandroid.flappybirdclone.model.*

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel
    private val spacePipes = 150f

    override fun create() {
        batch = SpriteBatch()
        initModel()
    }

    override fun render() {
        batch.begin()
        model.draw(batch)
        model.execute(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        model.dispose()
    }

    private fun initModel() {
        val background = ElementModel(
            img = Texture("fundo.png"),
            width = Gdx.graphics.width.toFloat(),
            height = Gdx.graphics.height.toFloat()
        )

        val bird = BirdModel(
            axisX = 50f,
            axisY = Gdx.graphics.height.toFloat() / 2,
        )

        val pipeTop = createPipeTopModel(background)
        val pipeBottom = createPipeBottomModel(background)

        val scoreModel = ScoreModel(
            axisX = Gdx.graphics.width.toFloat() / 2,
            axisY = Gdx.graphics.height.toFloat() - 110
        )

        val gameFinishModel = createGameFinishModel()

        model = FlappyBirdModel(
            background = background,
            bird = bird,
            pipeTop = pipeTop,
            pipeBottom = pipeBottom,
            scoreModel = scoreModel,
            gameFinishModel = gameFinishModel
        )
    }

    private fun createGameFinishModel(): GameFinishModel {
        val gameOverImage = Texture("game_over.png")
        val newWidthImg = gameOverImage.width.toFloat() + (gameOverImage.width / 2)
        val newHeightImg = gameOverImage.height.toFloat() + (gameOverImage.height / 2)
        val imageModel = ElementModel(
            img = Texture("game_over.png"),
            axisX = (Gdx.graphics.width.toFloat() / 2) - (newWidthImg / 2),
            axisY = Gdx.graphics.height.toFloat() / 2,
            width = newWidthImg,
            height = newHeightImg
        )

        val resetModel = TextElementModel(
            value = "Toque para reiniciar!",
            axisX = (Gdx.graphics.width.toFloat() / 2) - newWidthImg/2.8f,
            axisY = (Gdx.graphics.height.toFloat() / 2) - (gameOverImage.height / 2),
            fontSize = 2,
            color = Color.GREEN
        )

        val bestScoreModel = TextElementModel(
            value = "Seu recorde é: %s pontos",
            axisX = (Gdx.graphics.width.toFloat() / 2) - newWidthImg/2.5f,
            axisY = resetModel.axisY - 55,
            fontSize = 2,
            color = Color.RED
        )

        return GameFinishModel(
            imageModel = imageModel,
            resetModel = resetModel,
            bestScoreModel = bestScoreModel
        )
    }

    private fun createPipeTopModel(background: ElementModel): PipeModel {
        val pipeTopImg = Texture("cano_topo_maior.png")
        val pipeTopHeight = (pipeTopImg.height + pipeTopImg.height / 2).toFloat()
        return PipeModel(
            img = pipeTopImg,
            axisX = Gdx.graphics.width.toFloat(),
            axisY = (background.height / 2) + spacePipes,
            width = pipeTopImg.width.toFloat(),
            height = pipeTopHeight
        )
    }

    private fun createPipeBottomModel(background: ElementModel): PipeModel {
        val pipeBottomImg = Texture("cano_baixo_maior.png")
        val pipeBottomHeight = (pipeBottomImg.height + pipeBottomImg.height / 2).toFloat()
        return PipeModel(
            img = pipeBottomImg,
            axisX = Gdx.graphics.width.toFloat(),
            axisY = ((background.height / 2) - pipeBottomHeight) - spacePipes,
            width = pipeBottomImg.width.toFloat(),
            height = pipeBottomHeight
        )
    }
}