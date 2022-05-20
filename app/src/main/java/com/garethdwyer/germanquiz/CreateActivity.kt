package com.garethdwyer.germanquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.garethdwyer.germanquiz.databinding.ActivityCreateBinding
import com.garethdwyer.germanquiz.databinding.ActivityMainBinding

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener { createLesson() }
    }
    
    private fun createLesson() {
        val lessonTopic: String = binding.lessonTopic.text.toString().trim()
        val german1: String = binding.germanWord1.text.toString().trim()
        val english1: String = binding.englishWord1.text.toString().trim()
        val german2: String = binding.germanWord2.text.toString().trim()
        val english2: String = binding.englishWord2.text.toString().trim()
        val german3: String = binding.germanWord3.text.toString().trim()
        val english3: String = binding.englishWord3.text.toString().trim()

        if (lessonTopic.isEmpty()){
            binding.lessonTopic.error = "An entry is required"
            binding.lessonTopic.requestFocus()
            return
        }
        if (german1.isEmpty()){
            binding.germanWord1.error = "An entry is required"
            binding.germanWord1.requestFocus()
            return
        }
        if (english1.isEmpty()){
            binding.englishWord1.error = "An entry is required"
            binding.englishWord1.requestFocus()
            return
        }
        if (german2.isEmpty()){
            binding.germanWord2.error = "An entry is required"
            binding.germanWord2.requestFocus()
            return
        }
        if (english2.isEmpty()){
            binding.englishWord2.error = "An entry is required"
            binding.englishWord2.requestFocus()
            return
        }
        if (german3.isEmpty()){
            binding.germanWord3.error = "An entry is required"
            binding.germanWord3.requestFocus()
            return
        }
        if (english3.isEmpty()){
            binding.englishWord3.error = "An entry is required"
            binding.englishWord3.requestFocus()
            return
        }

        var newLesson = Lesson(lessonTopic, arrayOf(german2, english2, german3, english3,german1, english1))
        Toast.makeText(this@CreateActivity, "New Lesson created", Toast.LENGTH_LONG).show()
    }
}