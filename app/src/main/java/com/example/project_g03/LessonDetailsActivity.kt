package com.example.project_g03

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g03.databinding.ActivityLessonDetailsBinding

class LessonDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonDetailsBinding
    val sharedPrefKey = "com.example.project_g03.PREFERENCES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lesson = intent.getSerializableExtra("lesson") as? Lesson
        lesson?.let {
            binding.tvLessonDetails.text = """
                Lesson ${it.number}. ${it.name}
                Length: ${it.duration}
                
                ${it.content}
            """.trimIndent()
        }

        // Button to watch the lesson video
        binding.btnWatchLesson.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=G3e-cpL7ofc"))
            startActivity(intent)
        }

        // Button to mark the lesson as complete
        binding.btnMarkComplete.setOnClickListener {
            lesson?.let { lesson ->

                lesson.isCompleted = true

                // Storing in SharedPreferences
                val sharedPref = getSharedPreferences(sharedPrefKey, MODE_PRIVATE)
                val editor = sharedPref.edit()
                val progressArray = sharedPref.getStringSet("CompletedLessons", mutableSetOf())?.toMutableSet()

                progressArray?.add(lesson.number.toString())
                editor.putStringSet("CompletedLessons", progressArray)
                editor.apply()


                val resultIntent = Intent().apply {
                    putExtra("updatedLesson", lesson)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
