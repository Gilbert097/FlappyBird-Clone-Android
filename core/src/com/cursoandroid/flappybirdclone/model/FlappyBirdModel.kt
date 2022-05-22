package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

class FlappyBirdModel(
    val background: ElementModel,
    val bird: BirdModel,
    val pipeTop: PipeModel,
    val pipeBottom: PipeModel,
    val scoreModel: ScoreModel,
    val gameFinishModel: GameFinishModel
) {
    private val random = Random()
    private var spaceRandom = 0f
    private var isBirdCollided = false
    private var state: GameState = GameState.WAITING

    fun draw(batch: SpriteBatch) {
        //Desenhando fundo
        background.draw(batch)

        //Desenhando pássaro
        bird.draw(batch)

        //Desenhando cano cima e baixo
        pipeBottom.draw(batch)
        pipeTop.draw(batch)

        //Desenhando texto da pontuação
        scoreModel.draw(batch)
    }

    fun execute(batch: SpriteBatch) {
        val isTouched = Gdx.input.justTouched()
        when (state) {
            GameState.WAITING -> {
                executeStateWaiting(isTouched)
            }
            GameState.PLAYING -> {
                executeStatePlaying(isTouched)
            }
            GameState.FINISHED -> {
                gameFinishModel.draw(batch)
            }
        }
        bird.animate()
    }

    private fun executeStatePlaying(isTouched: Boolean) {
        //Aplicando gravidade no pássaro
        bird.applyGravity(isTouched)

        //movendo cano de cima e baixo no eixo X
        pipeTop.moveAxisX()
        pipeBottom.moveAxisX()

        //Verificando se houve colisão do pássaro
        validateBirdCollided()

        //Gerando espaço aleatório entre canos
        validateGenerateRandomSpace()

        //Incrementando valor da pontuação
        validateScoreIncrement()

        //Alterando a altura dos canso de forma aleatória
        //(Movendo o espaço entre os canos de posição)
        pipeBottom.moveAxisY(spaceRandom)
        pipeTop.moveAxisY(spaceRandom)
    }

    private fun executeStateWaiting(isTouched: Boolean) {
        if (isTouched) {
            state = GameState.PLAYING
            //Aplicando gravidade no passáro
            bird.applyGravity(isTouched)
        }
    }

    fun dispose() {
        background.dispose()
        bird.dispose()
        pipeTop.dispose()
        pipeBottom.dispose()
        scoreModel.dispose()
        gameFinishModel.dispose()
    }

    private fun validateBirdCollided() {
        val isColliedPipes = (pipeTop.isBirdCollided(bird) || pipeBottom.isBirdCollided(bird))
        if (!isBirdCollided && isColliedPipes) {
            isBirdCollided = true
            state = GameState.FINISHED
            Gdx.app.log("FlappyBirdModel", "Colidiu!")
        }
    }

    private fun validateScoreIncrement() {
        if (!scoreModel.isIncrementedValue && isBirdPassed()) {
            scoreModel.incrementValue()
        }

        if (isResetPipes()) {
            scoreModel.reset()
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
}