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
import kotlin.random.Random

class Prizes : AppCompatActivity() {
    private lateinit var rouletteWheel: RouletteWheel
    private lateinit var spinButton: Button

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

        // Находим кнопку по ее ID
        val buttonScannerBack: Button = findViewById(R.id.button3)

        // Устанавливаем действие на кнопку
        buttonScannerBack.setOnClickListener {
            // Создаем Intent для перехода на DashboardActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)  // Запускаем новое Activity
        }

    }

    private fun startSpin() {
        val randomAngle = Random.nextFloat() * 360f
        val handler = Handler(Looper.getMainLooper())
        var currentAngle = 0f
        val updateInterval = 10L // milliseconds

        handler.post(object : Runnable {
            override fun run() {
                currentAngle += 5f
                if (currentAngle <= randomAngle) {
                    rouletteWheel.rotateWheel(currentAngle)
                    handler.postDelayed(this, updateInterval)
                } else {
                    // Spin finished, determine result
                    val section = ((randomAngle % 360) / (360 / rouletteWheel.labels.size)).toInt()
//                    showResult(section)
                }
            }
        })
    }

//    private fun showResult(section: Int) {
//        Toast.makeText(this, "Result: ${rouletteWheel.labels[section]}", Toast.LENGTH_SHORT).show()
//    }

}