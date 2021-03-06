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
    companion object {
        private const val BEST_SCORE_KEY = "best_score"
    }

    private val random = Random()
    private var spaceRandom = 0f
    private var isBirdCollided = false
    private var isAnimateCollided = false
    private var state: GameState = GameState.WAITING
    private val preferences = Gdx.app.getPreferences(BEST_SCORE_KEY)

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
        bird.animate()
        val isTouched = Gdx.input.justTouched()
        when (state) {
            GameState.WAITING -> {
                executeStateWaiting(isTouched)
            }
            GameState.PLAYING -> {
                executeStatePlaying(isTouched)
            }
            GameState.FINISHED -> {
                executeStateFinished(batch, isTouched)
            }
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

    private fun executeStatePlaying(isTouched: Boolean) {
        //Aplicando gravidade no pássaro
        bird.move(isTouched)

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
            bird.move(isTouched)
        }
    }

    private fun executeStateFinished(batch: SpriteBatch, isTouched: Boolean) {
        //Caso o pássaro tenha caido, não é necessário animar a colisão
        if (isAnimateCollided) {
            bird.animateCollided()
        }

        val currentBestScore = updateBestScore()
        gameFinishModel.draw(batch, currentBestScore)

        //Aguardando toque na tela para resetar o jogo
        if (isTouched) {
            resetGame()
        }
    }

    private fun updateBestScore(): Int {
        var currentBestScore = preferences.getInteger(BEST_SCORE_KEY, 0)
        if (scoreModel.value > currentBestScore) {
            currentBestScore = scoreModel.value
            preferences.putInteger(BEST_SCORE_KEY, currentBestScore)
            preferences.flush();
        }
        return currentBestScore
    }

    private fun resetGame() {
        state = GameState.WAITING
        isBirdCollided = false
        isAnimateCollided = false
        bird.reset()
        pipeTop.reset()
        pipeBottom.reset()
        scoreModel.reset()
    }

    private fun validateBirdCollided() {
        val isColliedPipes = (pipeTop.isBirdCollided(bird) || pipeBottom.isBirdCollided(bird))
        //Validando se o pássaro bateu nos canos ou caiu.
        if (!isBirdCollided && (isColliedPipes || bird.isFell())) {
            if (isColliedPipes) {
                this.isAnimateCollided = true
            }
            bird.executeCollidedSound()
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
            scoreModel.resetIncrementControl()
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