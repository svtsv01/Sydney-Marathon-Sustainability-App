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

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        //
        // Optionally set a custom title for the ActionBar
        PointsManager.setPoints(10)

        // Update ActionBar to show points
        PointsManager.updateActionBarPoints(this)

        // Button listeners for Scanner and Prizes Activities
        val buttonToScanner: Button = findViewById(R.id.button)
        buttonToScanner.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val buttonToPrizes: Button = findViewById(R.id.button2)
        buttonToPrizes.setOnClickListener {
            val intent = Intent(this, Prizes::class.java)
            startActivity(intent)
        }
    }
}