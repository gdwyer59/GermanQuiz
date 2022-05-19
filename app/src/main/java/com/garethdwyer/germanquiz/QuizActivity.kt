package com.garethdwyer.germanquiz

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.garethdwyer.germanquiz.databinding.ActivityQuizBinding
import com.google.android.material.snackbar.Snackbar

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    //var result: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Start.setOnClickListener { startQuiz() }
    }

    private fun startQuiz() {
        //Build dialog box to show user
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@QuizActivity)

        //reset answer to empty string
        var result = ""
        //Question
        alert.setTitle("What is the German word for Hello?")
        //selectable answers
        var question = arrayOf("Hello","Hallo","Hola","Hi")
        //tracks the users current selected answer
        alert.setSingleChoiceItems(question, -1, DialogInterface.OnClickListener { dialog, which -> result = question[which] })
        //records the users final submitted answer
        alert.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            if(result.isNotEmpty()) {
                Snackbar.make(binding.Start, result, Snackbar.LENGTH_LONG).show()
                binding.Start.text = result
            }
        })
        //show dialog box
        alert.show()
    }
}