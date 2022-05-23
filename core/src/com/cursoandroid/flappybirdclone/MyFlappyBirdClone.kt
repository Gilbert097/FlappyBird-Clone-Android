package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.cursoandroid.flappybirdclone.model.*

class MyFlappyBirdClone : ApplicationAdapter() {
    companion object {
        private const val SPACE_PIPES = 115f
        private const val IMG_BACKGROUND_PATH = "fundo.png"
        private const val IMG_GAME_OVER_PATH = "game_over.png"
        private const val IMG_PIPE_TOP_PATH = "cano_topo_maior.png"
        private const val IMG_PIPE_BOTTOM_PATH = "cano_baixo_maior.png"
        private const val TEX_RESET_GAME = "Toque para reiniciar!"
    }

    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: StretchViewport

    private var deviceWidth: Float = 0f
    private var deviceHeigth: Float = 0f

    override fun create() {
        deviceWidth = Gdx.graphics.width.toFloat()
        deviceHeigth = Gdx.graphics.height.toFloat()
        batch = SpriteBatch()
        initCamera()
        initModel()
    }

    private fun initCamera() {
        camera = OrthographicCamera()
        camera.position.set(deviceWidth / 2, deviceHeigth / 2, 0f)
        viewport = StretchViewport(deviceWidth, deviceHeigth, camera)
    }

    override fun resize(width: Int, height: Int) {
        this.viewport.update(deviceWidth.toInt(), deviceHeigth.toInt())
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
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
            img = Texture(IMG_BACKGROUND_PATH),
            width = deviceWidth,
            height = deviceHeigth
        )

        val bird = BirdModel(
            axisX = 50f,
            axisY = deviceHeigth / 2,
        )

        val pipeTop = createPipeTopModel(background)
        val pipeBottom = createPipeBottomModel(background)

        val scoreModel = ScoreModel(
            axisX = deviceWidth / 2,
            axisY = deviceHeigth - 110
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
        val gameOverImage = Texture(IMG_GAME_OVER_PATH)
        val newWidthImg = gameOverImage.width.toFloat() + (gameOverImage.width / 2)
        val newHeightImg = gameOverImage.height.toFloat() + (gameOverImage.height / 2)
        val imageModel = ElementModel(
            img = gameOverImage,
            axisX = (deviceWidth / 2) - (newWidthImg / 2),
            axisY = deviceHeigth / 2,
            width = newWidthImg,
            height = newHeightImg
        )

        val resetModel = TextElementModel(
            value = TEX_RESET_GAME,
            axisX = (deviceWidth / 2) - newWidthImg / 2.8f,
            axisY = (deviceHeigth / 2) - (gameOverImage.height / 2),
            fontSize = 2,
            color = Color.GREEN
        )

        val bestScoreModel = TextElementModel(
            axisX = (deviceWidth / 2) - newWidthImg / 2.5f,
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
        val pipeTopImg = Texture(IMG_PIPE_TOP_PATH)
        val pipeTopHeight = (pipeTopImg.height + pipeTopImg.height / 2).toFloat()
        return PipeModel(
            img = pipeTopImg,
            axisX = deviceWidth,
            axisY = (background.height / 2) + SPACE_PIPES,
            width = pipeTopImg.width.toFloat(),
            height = pipeTopHeight
        )
    }

    private fun createPipeBottomModel(background: ElementModel): PipeModel {
        val pipeBottomImg = Texture(IMG_PIPE_BOTTOM_PATH)
        val pipeBottomHeight = (pipeBottomImg.height + pipeBottomImg.height / 2).toFloat()
        return PipeModel(
            img = pipeBottomImg,
            axisX = deviceWidth,
            axisY = ((background.height / 2) - pipeBottomHeight) - SPACE_PIPES,
            width = pipeBottomImg.width.toFloat(),
            height = pipeBottomHeight
        )
    }
}