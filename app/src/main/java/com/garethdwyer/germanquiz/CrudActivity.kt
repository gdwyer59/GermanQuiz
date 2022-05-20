package com.garethdwyer.germanquiz

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.collection.ArrayMap
import com.garethdwyer.germanquiz.databinding.ActivityCrudBinding
import com.garethdwyer.germanquiz.databinding.ActivityQuizBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import java.nio.file.FileSystem
import java.nio.file.Files

class CrudActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudBinding
    lateinit var database: LessonLibrary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startQuiz.setOnClickListener { startQuiz() }
        binding.create.setOnClickListener {
            val intent = Intent(this@CrudActivity, CreateActivity::class.java)
            startActivity(intent)
        }
        binding.update.setOnClickListener { update() }
        binding.read.setOnClickListener { read() }
        binding.save.setOnClickListener { save() }
        binding.delete.setOnClickListener { delete() }
    }

    private fun startQuiz() {
        //abort if no lesson library is loaded
        if (!::database.isInitialized) {
            Toast.makeText(this@CrudActivity, "No lesson library has been loaded", Toast.LENGTH_LONG).show()
            return
        }
        //set score for quiz
        var score = 0
        //set number of answered questions
        var totalNumberOfQuestions = 0
        //pick a quiz topic
        var quizTopic = askQuestion("Pick a quiz topic", database.listLessons())

        //find all the topic matching questions
        for(i in database.lessonLibrary) {
            if(i.lessonName.lowercase().contentEquals(quizTopic.lowercase())) {
                //ask all matching questions
                if(askQuestion(i.lessonContent)) {
                    score++
                }
                totalNumberOfQuestions++
            }
        }

        //show result
        Toast.makeText(this@CrudActivity, "You have scored $score out of $totalNumberOfQuestions", Toast.LENGTH_LONG).show()
    }

    private fun askQuestion(data: Array<String>): Boolean {
        //Build dialog box to show user
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@CrudActivity)
        //reset answer to empty string
        var result = ""
        //set question
        alert.setTitle(data[4])
        //set options
        alert.setSingleChoiceItems(arrayOf(data[0], data[1], data[2], data[3]), -1, DialogInterface.OnClickListener { dialog, which -> result = data[which] })
        //record the users final submitted answer
        alert.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            if(result.isNotEmpty()) {
                result = which.toString()
            }
        })
        //show dialog box
        alert.show()
        //process result
        return result.contentEquals(data[5])
    }

    private fun askQuestion(question: String, options: Array<String>): String {
        //Build dialog box to show user
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@CrudActivity)
        //reset answer to empty string
        var result = ""
        //set question
        alert.setTitle(question)
        //set options
        alert.setSingleChoiceItems(options, -1, DialogInterface.OnClickListener { dialog, which -> result = options[which] })
        //records the users final submitted answer
        alert.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            if(result.isNotEmpty()) {
                result = which.toString()
            }
        })
        //show dialog box
        alert.show()
        //process result
        return result
    }

    private fun update() {
        //Load demo LessonLibrary into memory
        database = loadDemoData()
        Toast.makeText(this@CrudActivity, "Data loaded into Memory", Toast.LENGTH_LONG).show()
    }

    private fun read() {
        //abort if there is no file to read from
        if(!this@CrudActivity.getFileStreamPath("lessonLibrary.json").exists()) {
            Toast.makeText(this@CrudActivity, "No files to read from", Toast.LENGTH_LONG).show()
            return
        }

        //file name to read from
        val jsonFileName = "lessonLibrary.json"
        lateinit var jsonFileContents: String

        //read lessonLibrary JSON from .json file to jsonFileContents variable
        this@CrudActivity.openFileInput(jsonFileName).bufferedReader().useLines {
            //jsonFileContents = it.toString()
            jsonFileContents = it.single()
        }

        //change lessonLibrary JSON into lessonLibrary object
        var gson = Gson()
        database = gson.fromJson(jsonFileContents, LessonLibrary::class.java)
    }

    private fun loadDemoData(): LessonLibrary {
        //create lessonLibrary
        var tempLessonLibrary: ArrayList<Lesson> = ArrayList<Lesson>()
        //create lesson content
        var tempLessonContent = arrayOf("Hello","Hallo","Hola","Hi","What is the german for Hello","2")
        //add lesson content to Lesson
        var tempLesson: Lesson = Lesson("Greetings", tempLessonContent)
        //add Lesson to lessonLibrary
        tempLessonLibrary.add(tempLesson)
        //return a lessonLibrary
        return LessonLibrary(tempLessonLibrary)
    }

    private fun save() {
        if (!::database.isInitialized) {
            Toast.makeText(this@CrudActivity, "No lesson library in memory to save", Toast.LENGTH_LONG).show()
            return
        }
        //convert lessonLibrary object to JSON
        var gson = Gson()
        var jsonFileContents = gson.toJson(database)
        //file name for saving to
        val jsonFileName = "lessonLibrary.json"
        // write lessonLibrary JSON into .json file
        // overwrites file if it already exists
        // creates file if it doesn't already exist
        this@CrudActivity.openFileOutput(jsonFileName, MODE_PRIVATE).use {
            it.write(jsonFileContents.toByteArray())
            //it.write(jsonFileContents.toString())
        }
        Toast.makeText(this@CrudActivity, "Lesson library in memory was saved", Toast.LENGTH_LONG).show()
    }

    private fun delete() {
        //file name to delete
        val jsonFileName = "lessonLibrary.json"
        //delete file
        this@CrudActivity.deleteFile(jsonFileName)
        Toast.makeText(this@CrudActivity, "Lesson library deleted", Toast.LENGTH_LONG).show()
    }
}