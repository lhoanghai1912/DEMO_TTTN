package com.example.demo_tttn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_tttn.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityRegisterBinding
private lateinit var auth: FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (checkAllField()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    //if succesfull account is created
                    //alse signed in
                    if (it.isSuccessful) {
                        auth.signOut()
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this,NewWordActivity::class.java)
                        startActivity(intent)
                    } else {
                        //account not created
                        Log.e("erro", it.exception.toString())
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
        }

        else{
            binding.textInputLayoutEmail.error = null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }
        else{
            binding.textInputLayoutEmail.error = null
        }
        if (binding.edtPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        else{
            binding.textInputLayoutPassword.error = null
        }
        //note password must be at least 8 char
        if (binding.edtPassword.length() <= 7) {
            binding.textInputLayoutPassword.error = "Password must be at least 8 character long"
            binding.textInputLayoutPassword.errorIconDrawable = null
        return false
        }
        else{
            binding.textInputLayoutPassword.error
        }
        if (binding.edtPassword.text.toString() == "") {
            binding.textInputLayoutConfirm.error = "This is required field"
            binding.textInputLayoutConfirm.errorIconDrawable = null
            return false
        }
        else{
            binding.textInputLayoutConfirm.error
        }
        if (binding.edtConfirm.text.toString() != binding.edtPassword.text.toString()) {
            binding.textInputLayoutConfirm.error = "Password is not matching"
            binding.textInputLayoutConfirm.errorIconDrawable = null
            return false
        }
        else{
            binding.textInputLayoutConfirm.error = null
        }
        return true
    }
}