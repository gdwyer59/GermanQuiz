package com.garethdwyer.germanquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garethdwyer.germanquiz.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }

        /*
        val user = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userID = user!!.uid
        val name = reference.child(userID).child("fullName")

        binding.textView.setText(name)
        */

    }
}