package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*
import kotlin.collections.ArrayList

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel
    private val spacePipes = 200f

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
            axisX = Gdx.graphics.width.toFloat() - pipeTopImg.width,
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
            axisX = Gdx.graphics.width.toFloat() - pipeBottomImg.width,
            axisY = ((background.height / 2) - pipeBottomHeight) - spacePipes,
            width = pipeBottomImg.width.toFloat(),
            height = pipeBottomHeight
        )
    }
}

private class FlappyBirdModel(
    val background: ElementModel,
    val bird: BirdModel,
    val pipeTop: PipeModel,
    val pipeBottom: PipeModel
) {
    val random = Random()
     var spaceRandom = 0f
    fun draw(batch: SpriteBatch) {
        background.draw(batch)

        bird.draw(batch)
        bird.applyGravity(Gdx.input.justTouched())
        bird.next()

        pipeBottom.moveAxisX()
        pipeBottom.draw(batch)

        pipeTop.moveAxisX()
        pipeTop.draw(batch)

        if(pipeBottom.isAxisXReset && pipeTop.isAxisXReset) {
            spaceRandom = (random.nextInt(900) - 450).toFloat()
            pipeTop.isAxisXReset = false
            pipeBottom.isAxisXReset = false
        }

        pipeBottom.moveAxisY(spaceRandom)
        pipeTop.moveAxisY(spaceRandom)
    }

    fun dispose() {
        background.dispose()
        bird.dispose()
        pipeTop.dispose()
        pipeBottom.dispose()
    }
}

private class BirdModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    var gravity: Float = 0f,
) : Coordinate(axisX, axisY) {
    var index: Float = 0F
        get() = if (field >= 3) 0F else field
    private val imgs: ArrayList<Texture>

    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch) {
        batch.draw(imgs[index.toInt()], axisX, axisY)
    }

    fun next() {
        index += Gdx.graphics.deltaTime * 5
        gravity++
    }

    fun applyGravity(isTouched: Boolean) {
        if (isTouched) {
            gravity = -20f
        }

        if (axisY > 0 || isTouched) {
            axisY -= gravity
        }
    }

    fun dispose() {
        imgs.forEach { it.dispose() }
    }

    private fun fillBirdImagens(): ArrayList<Texture> {
        val birdImgs = ArrayList<Texture>(3)
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
        return birdImgs
    }
}

private open class PipeModel(
    img: Texture,
    axisX: Float = 0f,
    axisY: Float = 0f,
    width: Float = 0f,
    height: Float = 0f
) : ElementModel(img, axisX, axisY, width, height) {

    private var axisXTemp: Float = axisX
    private var axisYTemp: Float = axisY
    var isAxisXReset = false

    override fun draw(batch: SpriteBatch) {
        batch.draw(img, axisXTemp, axisYTemp, width, height)
    }

    fun moveAxisX() {
        axisXTemp-= Gdx.graphics.deltaTime * 150
        if(axisXTemp < -img.width) {
            axisXTemp = Gdx.graphics.width.toFloat()
            isAxisXReset = true
        }
    }

    fun moveAxisY(spaceRandom: Float) {
        axisYTemp = axisY + spaceRandom
    }
}

private open class ElementModel(
    val img: Texture,
    axisX: Float = 0f,
    axisY: Float = 0f,
    width: Float = 0f,
    height: Float = 0f
) : Coordinate(axisX, axisY, width, height) {

    open fun draw(batch: SpriteBatch) {
        batch.draw(img, axisX, axisY, width, height)
    }

    fun dispose() {
        img.dispose()
    }
}

private open class Coordinate(
    var axisX: Float = 0f,
    var axisY: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
)