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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.util.Log
import android.widget.EditText
import android.animation.ObjectAnimator
import android.view.View
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample array of pairs (Route, Emission)
        val routeEmissionList = listOf(
            Pair("Route A", 120),
            Pair("Route B", 150),
            Pair("Route C", 80),
            Pair("Route D", 200),
            Pair("Route E", 300),
            Pair("Route F", 300)
        )

        // Sort by emission in ascending order
        val sortedList = routeEmissionList.sortedBy { it.second }

        // Get references to the TableLayout and LinearLayout
        val tableLayout: TableLayout = findViewById(R.id.tableLayout)
        val recyclingSection: LinearLayout = findViewById(R.id.recyclingSection)

        // Set initial visibility to GONE
        tableLayout.visibility = View.GONE
        recyclingSection.visibility = View.GONE

        // Populate table dynamically but keep it hidden at first
        for (pair in sortedList) {
            val tableRow = TableRow(this)

            // Create TextView for the Route
            val routeTextView = TextView(this)
            routeTextView.text = pair.first
            routeTextView.setPadding(8, 8, 8, 8)
            routeTextView.setTextColor(resources.getColor(android.R.color.white))

            // Create TextView for the Emission
            val emissionTextView = TextView(this)
            emissionTextView.text = pair.second.toString()
            emissionTextView.setPadding(8, 8, 8, 8)
            emissionTextView.setTextColor(resources.getColor(android.R.color.white))

            // Add TextViews to the TableRow
            tableRow.addView(routeTextView)
            tableRow.addView(emissionTextView)

            // Add TableRow to TableLayout
            tableLayout.addView(tableRow)
        }

        // References to EditText and Button
        val address1EditText: EditText = findViewById(R.id.address1)
        val address2EditText: EditText = findViewById(R.id.address2)
        val submitButton: Button = findViewById(R.id.submitButton)

        // Handle Submit Button Click
        submitButton.setOnClickListener {
            val address1 = address1EditText.text.toString()
            val address2 = address2EditText.text.toString()

            // Log the entered values to the console
            Log.d("MainActivity", "Entered Address 1: $address1")
            Log.d("MainActivity", "Entered Address 2: $address2")

            // Make the table visible with animation
            tableLayout.visibility = View.VISIBLE
            val tableAlphaAnimator = ObjectAnimator.ofFloat(tableLayout, "alpha", 0f, 1f)
            tableAlphaAnimator.duration = 1000 // Duration of animation in milliseconds
            tableAlphaAnimator.start()

            // Make the recycling section visible after the table is shown
            recyclingSection.visibility = View.VISIBLE
            val recyclingAlphaAnimator = ObjectAnimator.ofFloat(recyclingSection, "alpha", 0f, 1f)
            recyclingAlphaAnimator.duration = 1000
            recyclingAlphaAnimator.start()
        }

        // Navigation setup
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

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