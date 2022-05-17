package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

class FlappyBirdModel(
    val background: ElementModel,
    val bird: BirdModel,
    val pipeTop: PipeModel,
    val pipeBottom: PipeModel
) {
    private val random = Random()
    private var spaceRandom = 0f

    fun draw(batch: SpriteBatch) {
        //Desenhando fundo
        background.draw(batch)

        //Desenhando pássaro e aplicando gravidade
        bird.draw(batch)
        bird.applyGravity(Gdx.input.justTouched())
        bird.next()

        //Desenhando e movendo cano de baixo no eixo X
        pipeBottom.moveAxisX()
        pipeBottom.draw(batch)

        //Desenhando e movendo cano de cima no eixo X
        pipeTop.moveAxisX()
        pipeTop.draw(batch)

        //Validando se os canos chegaram no final
        // (eixo X dos canos foram resetados)
        validateResetAxisXPipes()

        //Alterando a altura dos canso de forma aleatória
        //(Movendo o espaço entre os canos de posição)
        pipeBottom.moveAxisY(spaceRandom)
        pipeTop.moveAxisY(spaceRandom)
    }

    private fun validateResetAxisXPipes() {
        if (pipeBottom.isAxisXReset && pipeTop.isAxisXReset) {
            spaceRandom = (random.nextInt(900) - 450).toFloat()
            pipeTop.isAxisXReset = false
            pipeBottom.isAxisXReset = false
        }
    }

    fun dispose() {
        background.dispose()
        bird.dispose()
        pipeTop.dispose()
        pipeBottom.dispose()
    }
}