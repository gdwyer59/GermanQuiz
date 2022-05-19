package com.garethdwyer.germanquiz

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.collection.ArrayMap
import com.garethdwyer.germanquiz.databinding.ActivityCrudBinding
import com.garethdwyer.germanquiz.databinding.ActivityQuizBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class CrudActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudBinding
    lateinit var lessonLibrary: LessonLibrary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startQuiz.setOnClickListener { startQuiz() }
        binding.create.setOnClickListener { create() }
        binding.read.setOnClickListener { read() }
        binding.update.setOnClickListener {  }
        binding.delete.setOnClickListener {  }
    }

    private fun startQuiz() {
        //abort if no lesson library loaded
        if (!::lessonLibrary.isInitialized) {
            return
        }

        //pick a quiz lesson
        //var testSubject = askQuestion("Pick a lesson", lessonLibrary.listLessons().toArray() as Array<String>)

        //start testing loop

        //for(i in lessonLibrary)

        //end testing loop, show result
    }

    private fun askQuestion(question: String, answer: Int, options: Array<String>): Boolean {
        //Build dialog box to show user
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@CrudActivity)
        //reset answer to empty string
        var result = ""
        //Question
        alert.setTitle(question)
        //tracks the users current selected answer
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
        return result.contentEquals(answer.toString())
    }

    private fun askQuestion(question: String, options: Array<String>): String {
        //Build dialog box to show user
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@CrudActivity)
        //reset answer to empty string
        var result = ""
        //Question
        alert.setTitle(question)
        //tracks the users current selected answer
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

    private fun create() {
        //Load demo LessonLibrary into memory
        lessonLibrary = loadDemoData()
    }

    private fun read() {
        //file name to read from
        val jsonFileName = "lessonLibrary.json"
        lateinit var jsonFileContents: String

        //read lessonLibrary JSON from .json file to jsonFileContents variable
        /*this@QuizActivity.openFileInput(jsonFileName).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                jsonFileContents = text
            }
        }*/

        //read lessonLibrary JSON from .json file to jsonFileContents variable
        this@CrudActivity.openFileInput(jsonFileName).bufferedReader().useLines {
            //jsonFileContents = it.toString()
            jsonFileContents = it.single()

        }

        //change lessonLibrary JSON into lessonLibrary object
        var gson = Gson()
        //var lessonLibrary = gson.fromJson(jsonFileContents, ArrayList<Lesson>())
        //val typeOfT: Type = TypeToken<ArrayList<Lesson>>()
        //val typeOfT: Type = object : TypeToken<ArrayList<Lesson?>?>() {}.type
        //val employee = gson.fromJson(jsonFileContents, typeOfT)
        var lessonLibrary = gson.fromJson(jsonFileContents, LessonLibrary::class.java)


    }

    private fun loadDemoData(): LessonLibrary {
        //create lessonLibrary
        var tempLessonLibrary: ArrayList<Lesson> = ArrayList<Lesson>()
        //create empty lesson content
        var tempLessonContent: ArrayMap<String, String> = ArrayMap<String, String>()
        //add content
        tempLessonContent["Hallo"] = "Hello"
        //add lesson content to Lesson
        var tempLesson: Lesson = Lesson("How to say Hello", tempLessonContent)
        //add Lesson to lessonLibrary
        tempLessonLibrary.add(tempLesson)
        //return a lessonLibrary
        return LessonLibrary(tempLessonLibrary)
    }



    private fun saveLessonLibrary(lessonLibrary: LessonLibrary) {
        //convert lessonLibrary object to JSON
        var gson = Gson()
        var jsonFileContents = gson.toJson(lessonLibrary)
        //file name for saving to
        val jsonFileName = "lessonLibrary.json"
        // write lessonLibrary JSON into .json file
        // overwrites file if it already exists
        // creates file if it doesn't already exist
        this@CrudActivity.openFileOutput(jsonFileName, MODE_PRIVATE).use {
            it.write(jsonFileContents.toByteArray())
            //it.write(jsonFileContents.toString())
        }
    }


}