package com.example.sydhnymarathonapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sydhnymarathonapp.databinding.ActivityMainBinding
import android.widget.Button
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        // Находим кнопку по ее ID
        val buttonToScanner: Button = findViewById(R.id.button)

        // Устанавливаем действие на кнопку
        buttonToScanner.setOnClickListener {
            // Создаем Intent для перехода на DashboardActivity
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)  // Запускаем новое Activity
        }

        // Находим кнопку по ее ID
        val buttonToPrizes: Button = findViewById(R.id.button2)

        // Устанавливаем действие на кнопку
        buttonToPrizes.setOnClickListener {
            // Создаем Intent для перехода на DashboardActivity
            val intent = Intent(this, Prizes::class.java)
            startActivity(intent)  // Запускаем новое Activity
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
    }
}