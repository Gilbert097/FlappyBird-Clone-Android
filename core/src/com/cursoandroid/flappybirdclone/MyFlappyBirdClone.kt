package com.cursoandroid.flappybirdclone

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import javax.swing.Spring

class MyFlappyBirdClone : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var backgroundImg: Texture
    private lateinit var birdImg: Texture


    override fun create() {
        batch = SpriteBatch()
        backgroundImg = Texture("fundo.png")
        birdImg = Texture("passaro1.png")
    }

    override fun render() {
        //ScreenUtils.clear(1f, 0f, 0f, 1f)
        batch.begin()
        batch.draw(backgroundImg,  0f,  0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        batch.draw(birdImg,  0f,  0f,)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        backgroundImg.dispose()
    }
}