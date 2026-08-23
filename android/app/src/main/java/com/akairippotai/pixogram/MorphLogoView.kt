package com.akairippotai.pixogram

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.PathInterpolator
import androidx.core.animation.doOnEnd

/**
 * Reproduces the RedCube intro's animated mark natively: a rounded square
 * that spins down and morphs into a circle over 1.8s (with a glow pulse and
 * a slight overshoot "pop"), then swaps to a static rotated arc, a disc
 * that fades out, and a brief light burst at the transition.
 */
class MorphLogoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    /** Called the instant the square finishes morphing and the arc/disc appear. */
    var onReveal: (() -> Unit)? = null

    private val squarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = RED
        style = Paint.Style.FILL
    }
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = RED
        style = Paint.Style.STROKE
        strokeWidth = dp(6f)
        strokeCap = Paint.Cap.ROUND
    }
    private val discPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = RED
        style = Paint.Style.FILL
    }
    private val burstPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bounds = RectF()
    private var rotationDeg = 0f
    private var cornerRadiusPx = dp(15f)
    private var glowBlurPx = dp(25f)
    private var glowAlpha = 178
    private var showSquare = true
    private var showArcAndDisc = false
    private var discAlpha = 255
    private var burstScale = 0f
    private var burstAlpha = 0f
    private var showBurst = false

    private var rotateAnimator: ValueAnimator? = null
    private var morphAnimator: ValueAnimator? = null
    private var glowAnimator: ValueAnimator? = null
    private var fadeAnimator: ValueAnimator? = null
    private var burstAnimator: ValueAnimator? = null

    init {
        // Software layer needed for Paint.setShadowLayer to render correctly.
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun dp(v: Float) = v * resources.displayMetrics.density

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(0f, 0f, w.toFloat(), h.toFloat())
        burstPaint.shader = RadialGradient(
            w / 2f, h / 2f, w * 0.7f,
            Color.argb(230, 255, 90, 90), Color.argb(0, 227, 6, 19),
            Shader.TileMode.CLAMP
        )
    }

    /** Starts the full intro sequence: rotation + morph, then arc/disc reveal. */
    fun startIntro() {
        showSquare = true
        showArcAndDisc = false

        // Decelerates to a stop exactly as the morph finishes; by then the
        // shape is nearly a circle so the remaining spin is imperceptible,
        // avoiding the hard visual cut of an infinite rotation being cancelled.
        rotateAnimator = ValueAnimator.ofFloat(0f, 1080f).apply {
            duration = MORPH_DURATION_MS
            interpolator = PathInterpolator(0.22f, 1f, 0.36f, 1f)
            addUpdateListener {
                rotationDeg = it.animatedValue as Float
                invalidate()
            }
            start()
        }

        // Slight overshoot ("pop") instead of a plain ease-in-out.
        morphAnimator = ValueAnimator.ofFloat(dp(15f), dp(50f)).apply {
            duration = MORPH_DURATION_MS
            interpolator = PathInterpolator(0.34f, 1.56f, 0.64f, 1f)
            addUpdateListener {
                cornerRadiusPx = it.animatedValue as Float
                invalidate()
            }
            doOnEnd { switchToArcAndDisc() }
            start()
        }

        glowAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                val t = it.animatedValue as Float
                glowBlurPx = dp(25f + 15f * t)
                glowAlpha = (178 + (242 - 178) * t).toInt()
                invalidate()
            }
            start()
        }
    }

    private fun switchToArcAndDisc() {
        rotateAnimator?.cancel()
        glowAnimator?.cancel()
        showSquare = false
        showArcAndDisc = true
        discAlpha = 255
        invalidate()
        onReveal?.invoke()

        fadeAnimator = ValueAnimator.ofInt(255, 0).apply {
            duration = DISC_FADE_MS
            interpolator = LinearInterpolator()
            addUpdateListener {
                discAlpha = it.animatedValue as Int
                invalidate()
            }
            start()
        }

        showBurst = true
        burstAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 400
            interpolator = PathInterpolator(0.22f, 1f, 0.36f, 1f)
            addUpdateListener {
                val t = it.animatedValue as Float
                burstScale = 0.4f + 1.4f * t
                burstAlpha = 0.9f * (1f - t)
                invalidate()
            }
            doOnEnd { showBurst = false }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f

        if (showSquare) {
            squarePaint.setShadowLayer(glowBlurPx, 0f, 0f, Color.argb(glowAlpha, 227, 6, 19))
            canvas.save()
            canvas.rotate(rotationDeg, cx, cy)
            canvas.drawRoundRect(bounds, cornerRadiusPx, cornerRadiusPx, squarePaint)
            canvas.restore()
        }

        if (showArcAndDisc) {
            canvas.save()
            canvas.rotate(140f, cx, cy)
            val inset = dp(3f)
            val arcRect = RectF(
                bounds.left + inset,
                bounds.top + inset,
                bounds.right - inset,
                bounds.bottom - inset
            )
            canvas.drawArc(arcRect, -90f, 270f, false, arcPaint)
            canvas.restore()

            if (discAlpha > 0) {
                discPaint.alpha = discAlpha
                canvas.drawCircle(cx, cy, width / 2f, discPaint)
            }
        }

        if (showBurst) {
            burstPaint.alpha = (burstAlpha * 255).toInt()
            canvas.save()
            canvas.scale(burstScale, burstScale, cx, cy)
            canvas.drawCircle(cx, cy, width * 0.7f, burstPaint)
            canvas.restore()
        }
    }

    fun stopAnimations() {
        rotateAnimator?.cancel()
        morphAnimator?.cancel()
        glowAnimator?.cancel()
        fadeAnimator?.cancel()
        burstAnimator?.cancel()
    }

    override fun onDetachedFromWindow() {
        stopAnimations()
        super.onDetachedFromWindow()
    }

    companion object {
        const val MORPH_DURATION_MS = 1800L
        const val DISC_FADE_MS = 500L
        private const val RED = 0xFFE30613.toInt()
    }
}
