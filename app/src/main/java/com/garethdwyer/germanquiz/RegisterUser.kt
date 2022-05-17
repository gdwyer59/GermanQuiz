package com.garethdwyer.germanquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isVisible
import com.garethdwyer.germanquiz.databinding.ActivityRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterUser : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.banner.setOnClickListener {
            val intent = Intent(this@RegisterUser, MainActivity::class.java)
            startActivity(intent)
        }

        binding.registerUser.setOnClickListener { registerUser() }

    }

    private fun registerUser() {

        //val email: String = binding.email.toString().trim()
        val email: String = binding.email.text.toString().trim()
        val password: String = binding.password.text.toString().trim()
        val fullName: String = binding.fullName.text.toString().trim()
        val age: String = binding.age.text.toString().trim()

        if (fullName.isEmpty()){
            binding.fullName.error = "Full Name is required"
            binding.fullName.requestFocus()
            return
        }

        else if (age.isEmpty()){
            binding.age.error = "Age is required"
            binding.age.requestFocus()
            return
        }
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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task -> if(task.isSuccessful) {
                val user = User(fullName, age, email)
                //I should print details of the user object just created above

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(user).addOnCompleteListener { task2 -> if(task2.isSuccessful) {
                    Toast.makeText(this@RegisterUser, "User has been registered successfully!", Toast.LENGTH_LONG).show()
                    binding.progressBar.isVisible = false

                    //redirect to login layout

                }else{
                    Toast.makeText(this@RegisterUser, "Failed to register! Try again!", Toast.LENGTH_LONG).show()
                    binding.progressBar.isVisible = false
                }}
            }else{
                Toast.makeText(this@RegisterUser, "Failed to register! Try again!", Toast.LENGTH_LONG).show()
                binding.progressBar.isVisible = false
            }
        }
    }
}