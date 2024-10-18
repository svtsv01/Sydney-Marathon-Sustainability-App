//package com.example.sydhnymarathonapp
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.util.AttributeSet
//import android.view.View
//import kotlin.math.cos
//import kotlin.math.min
//import kotlin.math.sin
//
//class RouletteWheel(context: Context, attrs: AttributeSet) : View(context, attrs) {
//
//    private val paint = Paint()
//    private val colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN)
//    val labels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
//
//    private var rotationAngle = 0f
//
//    // Custom probabilities for each section
////    private val probabilities = listOf(0.1f, 0.05f, 0.1f, 0.05f, 0.1f, 0.05f, 0.1f, 0.05f, 0.1f, 0.05f, 0.1f, 0.1f)
//
//    init {
//        paint.isAntiAlias = true
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val width = width.toFloat()
//        val height = height.toFloat()
//        val radius = min(width, height) / 2 * 0.9f // 90% of the view size
//        val centerX = width / 2
//        val centerY = height / 2
//
//        // Draw the roulette wheel sections
//        val sectionAngle = 360f / labels.size
//
//        for (i in labels.indices) {
//            paint.color = colors[i % colors.size]
//            val startAngle = i * sectionAngle + rotationAngle
//            canvas.drawArc(
//                centerX - radius, centerY - radius,
//                centerX + radius, centerY + radius,
//                startAngle, sectionAngle, true, paint
//            )
//
//            // Draw the labels
//            paint.color = Color.BLACK
//            paint.textSize = 50f
//            val angleInRadians = Math.toRadians((startAngle + sectionAngle / 2).toDouble())
//            val x = (centerX + radius / 2 * cos(angleInRadians)).toFloat() - paint.measureText(labels[i]) / 2
//            val y = (centerY + radius / 2 * sin(angleInRadians)).toFloat() + paint.textSize / 2
//            canvas.drawText(labels[i], x, y, paint)
//        }
//
//        // Draw the smaller circle in the middle
//        drawCenterCircle(canvas, centerX, centerY)
//
//        // Draw the triangle pointer at the top
//        drawPointerTriangle(canvas, centerX, centerY, radius)
//    }
//
//    // Method to update rotation angle and redraw
//    fun rotateWheel(newAngle: Float) {
//        rotationAngle = newAngle
//        invalidate() // Redraw the view
//    }
//
//    // Draw smaller circle in the middle of the wheel
//    private fun drawCenterCircle(canvas: Canvas, centerX: Float, centerY: Float) {
//        paint.color = Color.WHITE
//        canvas.drawCircle(centerX, centerY, 100f, paint) // Adjust the size of the circle as needed
//        paint.color = Color.BLACK
//        canvas.drawCircle(centerX, centerY, 95f, paint)
//    }
//
//    // Draw a triangle at the top of the wheel to indicate the winning section
//    private fun drawPointerTriangle(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
//        paint.color = Color.BLACK
//        val path = android.graphics.Path()
//        val triangleHeight = 40f
//        path.moveTo(centerX, centerY - radius - triangleHeight) // Top point
//        path.lineTo(centerX - 30f, centerY - radius) // Bottom-left point
//        path.lineTo(centerX + 30f, centerY - radius) // Bottom-right point
//        path.close()
//
//        canvas.drawPath(path, paint)
//    }
//
//    // Method to determine the section based on random spin angle and custom probabilities
//    fun getSectionByProbability(spinAngle: Float): Int {
//        val normalizedAngle = (spinAngle % 360 + 360) % 360
//        val sectionAngle = 360f / labels.size
//        var cumulativeProbability = 0f
//
//        for (i in probabilities.indices) {
//            cumulativeProbability += probabilities[i] * 360
//            if (normalizedAngle < cumulativeProbability) {
//                return i // Return the section index
//            }
//        }
//        return labels.size - 1 // Fallback
//    }
//}
