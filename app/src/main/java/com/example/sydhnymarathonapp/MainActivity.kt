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

import android.graphics.Color
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // References to Buttons
        val buttonToScanner: Button = findViewById(R.id.button)
        val buttonToPrizeDraw: Button = findViewById(R.id.button2)

        // Handle Scanner Button Click
        buttonToScanner.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        // Handle Prize Draw Button Click
        buttonToPrizeDraw.setOnClickListener {
            val intent = Intent(this,  Prizes::class.java)
            startActivity(intent)
        }


        // Sample array of pairs (Route, Emission)
        val routeEmissionList = listOf(
            Pair("Route - 1", 80),
            Pair("Route - 2", 9012),
            Pair("Route - 23", 901),
            Pair("Route - 212", 9012),
            Pair("Route - 3", 100)
        )

        val tableLayout: TableLayout = findViewById(R.id.tableLayout)
        val recyclingSection: LinearLayout = findViewById(R.id.recyclingSection)

        val sortedList = routeEmissionList.sortedBy{ it.second }

        val minEmission = sortedList.minByOrNull { it.second }?.second ?: 0
        val maxEmission = sortedList.maxByOrNull { it.second }?.second ?: 0

        for (pair in sortedList) {
            val tableRow = TableRow(this)

            val backgroundColor = when (pair.second) {
                minEmission -> "#A8E6CF"
                maxEmission -> "#FFD97D"
                else -> "#FFD97D"
            }
            tableRow.setBackgroundColor(Color.parseColor(backgroundColor))


            val routeTextView = TextView(this)
            routeTextView.text = pair.first
            routeTextView.setPadding(8, 8, 8, 8)
            routeTextView.setTextColor(Color.DKGRAY)
            routeTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            val emissionTextView = TextView(this)
            emissionTextView.text = pair.second.toString()
            emissionTextView.setPadding(8, 8, 8, 8)
            emissionTextView.setTextColor(Color.DKGRAY)
            emissionTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            tableRow.addView(routeTextView)
            tableRow.addView(emissionTextView)
            tableLayout.addView(tableRow)
        }


        val submitButton: Button = findViewById(R.id.submitButton)


        submitButton.setOnClickListener {

            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.duration = 1000
            tableLayout.startAnimation(animation)
            tableLayout.visibility = View.VISIBLE


            val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout) // Root layout ID
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)


            constraintSet.clear(R.id.recyclingSection, ConstraintSet.TOP)
            constraintSet.connect(
                R.id.recyclingSection,
                ConstraintSet.TOP,
                R.id.tableLayout,
                ConstraintSet.BOTTOM,
                10
            )


            constraintSet.applyTo(constraintLayout)
        }
    }
}