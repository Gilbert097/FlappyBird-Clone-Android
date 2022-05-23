package com.cursoandroid.flappybirdclone.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle

class BirdModel(
    axisX: Float = 0f,
    axisY: Float = 0f,
    private var gravity: Float = 0f,
) : Coordinate(axisX, axisY) {

    companion object {
        private const val GRAVITY_VALUE = -15f
        private const val FLAPPING_SOUND_PATH = "som_asa.wav"
        private const val COLLIDED_SOUND_PATH = "som_batida.wav"
    }

    val circle = Circle()
    private var index: Float = 0F
        get() = if (field >= 3) 0F else field
    private val imgs: ArrayList<Texture>
    private var axisXCurrent = axisX
    private var axisYCurrent = axisY
    private val flappingSound = Gdx.audio.newSound(Gdx.files.internal(FLAPPING_SOUND_PATH))
    private val collidedSound = Gdx.audio.newSound(Gdx.files.internal(COLLIDED_SOUND_PATH))
    private var axisXTemp = 0f
    init {
        imgs = fillBirdImagens()
    }

    fun draw(batch: SpriteBatch) {
        val image = imgs[index.toInt()]
        batch.draw(image, axisXCurrent + axisXTemp, axisYCurrent)
        val halfHeight = image.height / 2
        val halfWidth = image.width / 2
        circle.set(axisXCurrent + halfWidth + axisXTemp, axisYCurrent + halfHeight, halfHeight.toFloat())
    }

    fun executeCollidedSound(){
        collidedSound.play()
    }

    fun animateCollided(){
        val image = imgs[index.toInt()]
        if((axisXCurrent + axisXTemp) > -image.width) {
            axisXTemp -= Gdx.graphics.deltaTime * 500
            applyGravity()
            Gdx.app.log("Collided", "Caindo!")
        }
    }

    fun animate() {
        index += Gdx.graphics.deltaTime * 5
    }

    fun move(isTouched: Boolean) {
        moveUp(isTouched)
        applyGravity(isTouched)
    }

    private fun moveUp(isTouched: Boolean) {
        if (isTouched) {
            gravity = GRAVITY_VALUE
            flappingSound.play()
        }
    }

    private fun applyGravity(isTouched: Boolean = false) {
        if (axisYCurrent > 0 || isTouched) {
            axisYCurrent -= gravity
        }
        gravity++
    }

    fun dispose() {
        imgs.forEach { it.dispose() }
    }

    fun isFell() = axisYCurrent <= 0f

    fun reset() {
        this.axisXCurrent = this.axisX
        this.axisYCurrent = this.axisY
        this.axisXTemp = 0f
        this.gravity = 0f
    }

    private fun fillBirdImagens(): ArrayList<Texture> {
        val birdImgs = ArrayList<Texture>(3)
        birdImgs.add(Texture("passaro1.png"))
        birdImgs.add(Texture("passaro2.png"))
        birdImgs.add(Texture("passaro3.png"))
        return birdImgs
    }
}