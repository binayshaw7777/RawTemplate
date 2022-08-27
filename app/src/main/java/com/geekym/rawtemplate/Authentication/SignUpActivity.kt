package com.geekym.rawtemplate.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.geekym.rawtemplate.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var fd: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        fd = FirebaseDatabase.getInstance()
        db = fd.getReference("Users")

        // For Sign In text
        binding.gotosignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        // For Sign Up button
        binding.signupbtn.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.signupemail.text.toString()
            val pass = binding.signuppass.text.toString()
            val confirmpass = binding.confirmpass.text.toString()

            //Create user object
            val user = User(name, email)

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (pass == confirmpass && pass.length > 8) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            //get the generated uid
                            val u = firebaseAuth.currentUser

                            //add user data in the Realtime Database
                            db.child(u?.uid!!).setValue(user).addOnCompleteListener { it1 ->
                                if (it1.isSuccessful) {
                                    u.sendEmailVerification()
                                    Toast.makeText(this, "Email Verification sent to your mail", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this, SignInActivity::class.java))
                                }
                            }

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter the details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}