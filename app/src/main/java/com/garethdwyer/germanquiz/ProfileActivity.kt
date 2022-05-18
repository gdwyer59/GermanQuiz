package com.garethdwyer.germanquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garethdwyer.germanquiz.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            //significantly faster using finish
            finish()
            //val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            //startActivity(intent)
        }
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user!!.uid

        database = FirebaseDatabase.getInstance().reference.child("Users/${userId}/fullName")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userName: String = snapshot.value.toString()
                userName = "Welcome ${userName.replaceFirstChar { it.uppercase() }}"
                binding.textView.text = userName
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}