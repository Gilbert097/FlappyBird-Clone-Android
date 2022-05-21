package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

class FlappyBirdModel(
    val background: ElementModel,
    val bird: BirdModel,
    val pipeTop: PipeModel,
    val pipeBottom: PipeModel,
    val punctuationModel: PunctuationModel
) {
    private val random = Random()
    private var spaceRandom = 0f
    private var isIncrementedValue = false

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

        //Gerando espaço aleatório entre canos
        validateGenerateRandomSpace()

        //Desenhando texto da pontuação
        punctuationModel.draw(batch)

        //Incrementando valor da pontuação
        validatePunctuationIncrement()

        //Alterando a altura dos canso de forma aleatória
        //(Movendo o espaço entre os canos de posição)
        pipeBottom.moveAxisY(spaceRandom)
        pipeTop.moveAxisY(spaceRandom)
    }

    private fun validatePunctuationIncrement() {
        if (!isIncrementedValue && isBirdPassed()) {
            isIncrementedValue = true
            punctuationModel.incrementValue()
        }

        if (isResetPipes()) {
            isIncrementedValue = false
        }
    }

    private fun isBirdPassed() =
        pipeBottom.axisXCurrent + pipeBottom.width < bird.axisX &&
                pipeTop.axisXCurrent + pipeTop.width < bird.axisX

    private fun isResetPipes() = pipeBottom.isAxisXReset && pipeTop.isAxisXReset

    private fun validateGenerateRandomSpace() {
        if (isResetPipes()) {
            spaceRandom = (random.nextInt(900) - 450).toFloat()
        }
    }

    fun dispose() {
        background.dispose()
        bird.dispose()
        pipeTop.dispose()
        pipeBottom.dispose()
    }
}