package com.garethdwyer.germanquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.garethdwyer.germanquiz.databinding.ActivityLearnBinding
import java.util.*

class LearnActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLearnBinding
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        Toast.makeText(this@LearnActivity, "Click any German word to hear it!", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ObsoleteSdkInt")
    fun speak(v: View?){

        val b = v as TextView
        val buttonText = b.getText().toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(buttonText, TextToSpeech.QUEUE_FLUSH, null,"")
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set german as language for tts
            val result = tts!!.setLanguage(Locale.GERMAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                Log.e("TTS", "Working!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }

}