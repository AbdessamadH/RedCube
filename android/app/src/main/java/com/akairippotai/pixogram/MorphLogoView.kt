package com.akairippotai.pixogram

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd

/**
 * Reproduces the RedCube web intro's animated mark natively:
 * a rounded square that spins and morphs into a circle over 5s,
 * then swaps to a static rotated arc plus a disc that fades out.
 */
class MorphLogoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val squarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = RED
        style = Paint.Style.FILL
        setShadowLayer(dp(25f), 0f, 0f, Color.argb(178, 227, 6, 19))
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

    private val bounds = RectF()
    private var rotationDeg = 0f
    private var cornerRadiusPx = dp(15f)
    private var showSquare = true
    private var showArcAndDisc = false
    private var discAlpha = 255

    private var rotateAnimator: ValueAnimator? = null
    private var morphAnimator: ValueAnimator? = null
    private var fadeAnimator: ValueAnimator? = null

    init {
        // Software layer needed for Paint.setShadowLayer to render correctly.
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun dp(v: Float) = v * resources.displayMetrics.density

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    /** Starts the full intro sequence: rotation + morph, then arc/disc reveal. */
    fun startIntro() {
        showSquare = true
        showArcAndDisc = false

        rotateAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotationDeg = it.animatedValue as Float
                invalidate()
            }
            start()
        }

        morphAnimator = ValueAnimator.ofFloat(dp(15f), dp(50f)).apply {
            duration = 5000
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                cornerRadiusPx = it.animatedValue as Float
                invalidate()
            }
            doOnEnd { switchToArcAndDisc() }
            start()
        }
    }

    private fun switchToArcAndDisc() {
        rotateAnimator?.cancel()
        showSquare = false
        showArcAndDisc = true
        discAlpha = 255
        invalidate()

        fadeAnimator = ValueAnimator.ofInt(255, 0).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                discAlpha = it.animatedValue as Int
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f

        if (showSquare) {
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
    }

    fun stopAnimations() {
        rotateAnimator?.cancel()
        morphAnimator?.cancel()
        fadeAnimator?.cancel()
    }

    override fun onDetachedFromWindow() {
        stopAnimations()
        super.onDetachedFromWindow()
    }

    private companion object {
        const val RED = 0xFFE30613.toInt()
    }
}
