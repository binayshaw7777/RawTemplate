package com.geekym.rawtemplate.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.geekym.rawtemplate.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

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

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){
                if (pass == confirmpass){

                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            if (firebaseAuth.currentUser?.isEmailVerified!!) {
                                startActivity(Intent(this, SignInActivity::class.java))
                            } else {
                                val u = firebaseAuth.currentUser
                                u?.sendEmailVerification()
                                Toast.makeText(this, "Email Verification sent to your mail", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this,"Password is not matching!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Please enter the details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}