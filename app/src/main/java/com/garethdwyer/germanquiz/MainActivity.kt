package com.garethdwyer.germanquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
//import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.garethdwyer.germanquiz.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterUserActivity::class.java)
            startActivity(intent)
        }

        binding.signIn.setOnClickListener { userLogin() }
    }

    /*override fun onResume() {
        super.onResume()
        binding.email.setText("")
        binding.password.setText("")
    }*/

    private fun userLogin() {
        val email: String = binding.email.text.toString().trim()
        val password: String = binding.password.text.toString().trim()

        if (email.isEmpty()){
            binding.email.error = "Email is required"
            binding.email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Please provide a valid email!"
            binding.email.requestFocus()
            return
        }
        if (password.isEmpty()){
            binding.password.error = "Password is required"
            binding.password.requestFocus()
            return
        }
        if (password.length < 6){
            binding.password.error = "Minimum Password length should be 6 characters!"
            binding.password.requestFocus()
            return
        }

        binding.progressBar.isVisible = true

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task -> if(task.isSuccessful) {
            binding.progressBar.isVisible = false

            //redirect to user profile
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)

            }else{
                Toast.makeText(this@MainActivity, "Failed to Login! Please check your credentials", Toast.LENGTH_LONG).show()
                binding.progressBar.isVisible = false
            }
        }
    }
}