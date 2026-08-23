package com.akairippotai.pixogram

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * Synthesizes the intro's sound effects at runtime (no audio assets needed):
 * a whoosh under the spin/morph, and a short pop when the mark is revealed.
 */
object IntroSoundEffects {

    private const val SAMPLE_RATE = 44100

    fun playWhoosh(durationMs: Long) = playBuffer(buildWhoosh(durationMs))

    fun playPop() = playBuffer(buildPop())

    private fun buildWhoosh(durationMs: Long): ShortArray {
        val samples = (SAMPLE_RATE * durationMs / 1000).toInt()
        val buffer = ShortArray(samples)
        for (i in 0 until samples) {
            val t = i.toDouble() / SAMPLE_RATE
            val progress = i.toDouble() / samples
            val freq = 180.0 + 420.0 * progress
            val envelope = sin(PI * progress) // fades in, then out
            val sample = sin(2.0 * PI * freq * t) * envelope * 0.5
            buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
        }
        return buffer
    }

    private fun buildPop(): ShortArray {
        val durationMs = 180
        val samples = SAMPLE_RATE * durationMs / 1000
        val buffer = ShortArray(samples)
        for (i in 0 until samples) {
            val t = i.toDouble() / SAMPLE_RATE
            val progress = i.toDouble() / samples
            val envelope = exp(-progress * 8.0)
            val sample = sin(2.0 * PI * 880.0 * t) * envelope * 0.6
            buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
        }
        return buffer
    }

    private fun playBuffer(data: ShortArray) {
        thread(isDaemon = true) {
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(SAMPLE_RATE)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setTransferMode(AudioTrack.MODE_STATIC)
                .setBufferSizeInBytes(data.size * 2)
                .build()
            track.write(data, 0, data.size)
            track.play()
            Thread.sleep(data.size * 1000L / SAMPLE_RATE + 100)
            track.release()
        }
    }
}
