package com.akairippotai.pixogram

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.animation.PathInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * RedCube animated intro: rotating/morphing logo with "red" + "ube" text
 * fading in, matching the timing of the web splash screen (see index.html
 * at the repo root for the reference implementation and exact constants).
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

        morphLogo.onReveal = { IntroSoundEffects.playPop() }
        morphLogo.startIntro()
        IntroSoundEffects.playWhoosh(MorphLogoView.MORPH_DURATION_MS)

        // "red" appears, then "ube" a beat later: rhythm instead of a synced fade.
        fadeInText(textRed, delay = 1000)
        fadeInText(textUbe, delay = 1150)

        window.decorView.postDelayed(introFinishedRunnable, TOTAL_DURATION_MS)
    }

    private fun fadeInText(textView: TextView, delay: Long) {
        val alpha = PropertyValuesHolder.ofFloat(TextView.ALPHA, 0f, 1f)
        val translateY = PropertyValuesHolder.ofFloat(
            TextView.TRANSLATION_Y, textView.resources.displayMetrics.density * 6f, 0f
        )
        ObjectAnimator.ofPropertyValuesHolder(textView, alpha, translateY).apply {
            startDelay = delay
            duration = 1000
            interpolator = PathInterpolator(0.22f, 1f, 0.36f, 1f)
            start()
        }
    }

    override fun onDestroy() {
        window.decorView.removeCallbacks(introFinishedRunnable)
        super.onDestroy()
    }

    private fun onIntroFinished() {
        // TODO: navigate to Pixogram's main menu once that screen exists.
    }

    private companion object {
        const val TOTAL_DURATION_MS =
            MorphLogoView.MORPH_DURATION_MS + MorphLogoView.DISC_FADE_MS + 1000L
    }
}
