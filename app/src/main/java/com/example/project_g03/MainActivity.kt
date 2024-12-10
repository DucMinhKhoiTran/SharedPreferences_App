package com.example.project_g03

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val sharedPrefKey = "com.example.project_g03.PREFERENCES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("Student_Name", null)

        if (!savedName.isNullOrBlank()) {
            navigation()
            return
        }

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isNotBlank()) {
                // Save the user's name to SharedPreferences
                with(sharedPreferences.edit()) {
                    putString("Student_Name", name)
                    apply()
                }
                Toast.makeText(this, "Logged in Successfully!", Toast.LENGTH_SHORT).show()
                navigation()
            } else {
                Toast.makeText(this, "Please Enter a Name to Continue!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Navigating to WelcomeBackActivity
    private fun navigation() {
        val intent = Intent(this@MainActivity, WelcomeBackActivity::class.java)
        startActivity(intent)
        finish()
    }
}
