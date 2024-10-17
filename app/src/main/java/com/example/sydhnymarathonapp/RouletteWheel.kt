package com.example.sydhnymarathonapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class RouletteWheel(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private val colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN)
//    private val colors = listOf(Color.BLUE, Color.YELLOW)
//    private val colors = listOf(Color.BLACK, Color.RED)
    val labels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    private var rotationAngle = 0f

    init {
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = min(width, height) / 2 * 0.9f // 90% of the view size

        // Draw the roulette wheel sections
        val sectionAngle = 360f / labels.size

        for (i in labels.indices) {
            paint.color = colors[i % colors.size]
            val startAngle = i * sectionAngle + rotationAngle
            canvas.drawArc(
                width / 2 - radius, height / 2 - radius,
                width / 2 + radius, height / 2 + radius,
                startAngle, sectionAngle, true, paint
            )

            // Draw the labels
            paint.color = Color.BLACK
            paint.textSize = 50f
            val angleInRadians = Math.toRadians((startAngle + sectionAngle / 2).toDouble())
            val x = (width / 2 + radius / 2 * cos(angleInRadians)).toFloat()
            val y = (height / 2 + radius / 2 * sin(angleInRadians)).toFloat()
            canvas.drawText(labels[i], x, y, paint)
        }
    }

    // Method to update rotation angle and redraw
    fun rotateWheel(newAngle: Float) {
        rotationAngle = newAngle
        invalidate() // Redraw the view
    }
}

