package com.example.project_g03

import LessonsAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g03.Lesson
import com.example.project_g03.LessonDetailsActivity
import com.example.project_g03.databinding.ActivityLessonListBinding

class LessonListActivity : AppCompatActivity() {


    lateinit var binding: ActivityLessonListBinding

    val lessons = mutableListOf(
        Lesson(1, "Introduction to the Course", "12 min", "This course is designed for beginners, so no prior programming experience is required. We'll start with the basics and gradually build up to more complex concepts. By the end of this course, you'll have a solid foundation in web development and be able to create your own websites.", false),
        Lesson(2, "What is Javascript?", "30 min", "JavaScript is a versatile programming language that brings web pages to life. It's the third pillar of web development, alongside HTML and CSS. While HTML structures a page and CSS styles it, JavaScript adds interactivity and dynamic behavior.", false),
        Lesson(3, "Variables and Conditionals", "1 hr 20 min", "In JavaScript, variables are containers used to store data. You declare a variable using the let or const keyword, followed by the variable name. Conditions in JavaScript allow you to execute different code blocks based on whether a certain condition is true or false. The most common conditional statement is the if statement", false),
        Lesson(4, "Loops", "38 min", "Loops are a fundamental programming concept that allows you to repeatedly execute a block of code. JavaScript provides several types of loops to control the flow of your program", false)
    )


    private val lessonResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedLesson = result.data?.getSerializableExtra("updatedLesson") as? Lesson
            updatedLesson?.let { lesson ->
                val position = lessons.indexOfFirst { it.number == lesson.number }
                if (position != -1) {
                    lessons[position].isCompleted = true
                    (binding.recyclerViewLessons.adapter as LessonsAdapter).updateLessonCompletion(position)
                    saveLessonCompletionState()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up RecyclerView and adapter
        val adapter = LessonsAdapter(lessons) { lesson ->
            val intent = Intent(this, LessonDetailsActivity::class.java).apply {
                putExtra("lesson", lesson)
            }
            lessonResultLauncher.launch(intent)
        }

        binding.recyclerViewLessons.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLessons.adapter = adapter

        loadLessonCompletionState()
    }

    private fun loadLessonCompletionState() {
        val sharedPreferences = getSharedPreferences("com.example.project_g03.PREFERENCES", Context.MODE_PRIVATE)
        lessons.forEach { lesson ->
            lesson.isCompleted = sharedPreferences.getBoolean("Lesson_${lesson.number}_Completed", false)
        }
    }

    private fun saveLessonCompletionState() {
        val sharedPreferences = getSharedPreferences("com.example.project_g03.PREFERENCES", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        lessons.forEach { lesson ->
            editor.putBoolean("Lesson_${lesson.number}_Completed", lesson.isCompleted)
        }
        editor.apply()

        val completedLessonsCount = lessons.count { it.isCompleted }
        val totalLessons = lessons.size

        with(sharedPreferences.edit()) {
            putInt("CompletedLessonsCount", completedLessonsCount)
            putInt("TotalLessons", totalLessons)
            apply()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveLessonCompletionState()
        val intent = Intent(this, WelcomeBackActivity::class.java)
        startActivity(intent)
        finish()
    }
}
