package com.example.project_g03

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g03.databinding.ActivityWelcomeBackBinding

class WelcomeBackActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBackBinding

    private val sharedPrefKey = "com.example.project_g03.PREFERENCES"
    private val lessonsCompletionKey = "LessonsCompletion"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("Student_Name", "name") ?: "Guest"
        val completedLessonsCount = sharedPreferences.getInt("CompletedLessonsCount", 0)
        val totalLessons = sharedPreferences.getInt("TotalLessons", 4)


        val progressPercentage = if (totalLessons > 0) {
            (completedLessonsCount * 100) / totalLessons
        } else {
            0
        }

        //welcome message
        binding.tvWelcome.text = """
           Welcome back, $userName.
           You've completed $progressPercentage% of the course!
           
           Lessons completed: $completedLessonsCount
           Lessons remaining: ${totalLessons - completedLessonsCount}
        """.trimIndent()

        //continue button
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, LessonListActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Reset Button
        binding.resetButton.setOnClickListener {
            if (sharedPreferences.contains("Student_Name")) {
                with(sharedPreferences.edit()) {
                    clear()
                    apply()
                }
                Toast.makeText(this, "User removed successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@WelcomeBackActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
