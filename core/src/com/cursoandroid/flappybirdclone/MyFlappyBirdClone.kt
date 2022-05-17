package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.cursoandroid.flappybirdclone.model.BirdModel
import com.cursoandroid.flappybirdclone.model.ElementModel
import com.cursoandroid.flappybirdclone.model.FlappyBirdModel
import com.cursoandroid.flappybirdclone.model.PipeModel
import java.util.*
import kotlin.collections.ArrayList

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
            axisX = 30F,
            axisY = Gdx.graphics.height.toFloat() / 2,
        )

        val pipeTop = createPipeTopModel(background)
        val pipeBottom = createPipeBottomModel(background)

        model = FlappyBirdModel(
            background = background,
            bird = bird,
            pipeTop = pipeTop,
            pipeBottom = pipeBottom
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