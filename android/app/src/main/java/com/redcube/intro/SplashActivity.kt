package com.redcube.intro

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * RedCube animated intro: rotating/morphing logo with "red" + "ube" text
 * fading in, matching the timing of the original web splash screen.
 * Navigation to whatever screen follows the intro is intentionally not
 * wired up yet.
 */
class SplashActivity : AppCompatActivity() {

    private val introFinishedRunnable = Runnable { onIntroFinished() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val textRed = findViewById<TextView>(R.id.textRed)
        val textUbe = findViewById<TextView>(R.id.textUbe)
        val morphLogo = findViewById<MorphLogoView>(R.id.morphLogo)

        morphLogo.startIntro()

        listOf(textRed, textUbe).forEach { textView ->
            ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f).apply {
                startDelay = 1000
                duration = 1000
                interpolator = DecelerateInterpolator()
                start()
            }
        }

        window.decorView.postDelayed(introFinishedRunnable, 7000)
    }

    override fun onDestroy() {
        window.decorView.removeCallbacks(introFinishedRunnable)
        super.onDestroy()
    }

    private fun onIntroFinished() {
        // TODO: navigate to Pixogram's main menu once that screen exists.
    }
}
