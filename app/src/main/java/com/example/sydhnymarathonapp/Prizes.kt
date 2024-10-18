package com.example.sydhnymarathonapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlin.math.floor
import kotlin.random.Random

class Prizes : AppCompatActivity() {
    private lateinit var rouletteWheel: RouletteWheel
    private lateinit var spinButton: Button
    private var currentRotation = 0f // Track the current rotation angle of the wheel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prizes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rouletteWheel = findViewById(R.id.rouletteWheel)
        spinButton = findViewById(R.id.spinButton)

        spinButton.setOnClickListener {
            startSpin()
        }

        // Back button logic
        val buttonScannerBack: Button = findViewById(R.id.button3)
        buttonScannerBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        PointsManager.updateActionBarPoints(this)
    }

    private fun startSpin() {
        // Total spin will be 3 to 5 full rotations + some random final angle
        val randomAngle = (Random.nextInt(3, 6) * 360) + Random.nextFloat() * 360f
        val handler = Handler(Looper.getMainLooper())
        val totalDuration = 3000L // Total spin time in milliseconds
        val startTime = System.currentTimeMillis()

        spinButton.isEnabled = false // Disable button during spin

        handler.post(object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime < totalDuration) {
                    val progress = elapsedTime / totalDuration.toFloat()
                    val easedProgress = (1 - (1 - progress) * (1 - progress)) // Ease out function
                    val currentAngle = easedProgress * randomAngle
                    rouletteWheel.rotateWheel(currentRotation + currentAngle)
                    handler.postDelayed(this, 16L) // Approx 60 FPS
                } else {
                    spinButton.isEnabled = true // Enable button after spin

                    // After the spin is done, we calculate the new rotation angle of the wheel
                    currentRotation = (currentRotation + randomAngle) % 360 // Update current rotation angle

                    // Determine the winning section based on the final rotation
                    val winningSection = getWinningSection(currentRotation)
                    showResult(winningSection)
                }
            }
        })
    }

    // Method to determine which section is at the top based on the final spin angle
    private fun getWinningSection(finalAngle: Float): Int {
        // Normalize the angle (remove complete rotations and ensure it's between 0 and 360)
        val normalizedAngle = (finalAngle % 360 + 360) % 360

        // Calculate the section size in degrees
        val sectionAngle = 360f / rouletteWheel.labels.size

        // Determine the section that aligns with the top (0-degree position)
        val winningSection = floor(normalizedAngle / sectionAngle).toInt()

        return winningSection
    }

    private fun showResult(section: Int) {
        Toast.makeText(this, "Result: ${rouletteWheel.labels[section]}", Toast.LENGTH_SHORT).show()
        println("Winning Section: ${section + 1}") // Print the winning section to the console
    }
}
