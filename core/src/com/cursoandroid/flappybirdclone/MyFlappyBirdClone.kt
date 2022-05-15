package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import javax.swing.Spring

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var model: FlappyBirdModel
    private var index: Float = 0F

    override fun create() {
        batch = SpriteBatch()
        model = FlappyBirdModel(
           width =  Gdx.graphics.width.toFloat(),
           height =  Gdx.graphics.height.toFloat()
        )
    }

    override fun render() {
        //ScreenUtils.clear(1f, 0f, 0f, 1f)
        if (index > 3) {
            index = 0F
        }
        batch.begin()
        batch.draw(
            model.backgroundImg,
            0f,
            0f,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )
        batch.draw(
            model.birdImgs[index.toInt()],
            Gdx.graphics.width.toFloat() / 2,
            Gdx.graphics.height.toFloat() / 2
        )
        index += Gdx.graphics.deltaTime * 5
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        model.dispose()
    }
}

private class FlappyBirdModel(width: Float, height: Float) {
    val birdImgs = ArrayList<Texture>(3)
    val backgroundImg: Texture = Texture("fundo.png")
    init {
        fillBirdImagens()
    }

    private fun fillBirdImagens() {
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
    }

    fun dispose() {
        backgroundImg.dispose()
        birdImgs.forEach { it.dispose() }
    }

}