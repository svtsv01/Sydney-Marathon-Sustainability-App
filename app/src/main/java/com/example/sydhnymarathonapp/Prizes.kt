package com.example.sydhnymarathonapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Prizes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prizes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
}