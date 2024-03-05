package com.example.demo_tttn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_tttn.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityLoginBinding
private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

        binding.btnSignIn.setOnClickListener {
            //if true
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (checkAllField()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //if succesfully already sign in
                        Toast.makeText(this, "Succesfully sign", Toast.LENGTH_SHORT).show()

                        //go New Word Library
                        val intent = Intent(this, NewWordActivity::class.java)
                        startActivity(intent)

                        //destroy activity
                        finish()
                    } else {
                        //not sign in
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }
    }

    private fun checkAllField(): Boolean {
        val email = binding.edtEmail.text.toString()
        if (binding.edtEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "This is required field"
            return false
        } else {
            binding.textInputLayoutEmail.error = null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        } else {
            binding.textInputLayoutEmail.error = null
        }
        if (binding.edtPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        } else {
            binding.textInputLayoutPassword.error = null
        }
        //note password must be at least 8 char
        if (binding.edtPassword.length() <= 7) {
            binding.textInputLayoutPassword.error = "Password must be at least 8 character long"
            binding.textInputLayoutPassword.errorIconDrawable = null
        } else {
            binding.textInputLayoutPassword.error = null
        }
        return true
    }
}