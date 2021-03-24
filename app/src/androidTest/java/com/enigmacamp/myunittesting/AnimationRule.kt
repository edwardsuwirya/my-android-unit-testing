package com.enigmacamp.myunittesting

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class AnimationRule : TestRule {
    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        override fun evaluate() {
            changeAnimationStatus(enable = false)
            base?.evaluate();
            changeAnimationStatus(enable = true)
        }
    }

    fun Boolean.toInt() = if (this) 1 else 0

    private fun changeAnimationStatus(enable: Boolean = true) {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand(" $TRANSITION_ANIMATION_SCALE ${enable.toInt()}")
            executeShellCommand("$WINDOW_ANIMATION_SCALE ${enable.toInt()}")
            executeShellCommand("$ANIMATOR_DURATION ${enable.toInt()}")
        }
    }

    companion object {
        private const val TRANSITION_ANIMATION_SCALE =
            "settings put global transition_animation_scale"
        private const val WINDOW_ANIMATION_SCALE = "settings put global window_animation_sc"
        private const val ANIMATOR_DURATION = "settings put global animator_duration_scale"
    }
}