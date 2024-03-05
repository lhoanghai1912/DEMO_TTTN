package com.example.demo_tttn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.demo_tttn.databinding.ActivityInsertionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityInsertionBinding
class InsertionActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("NewWords")

        //Xử lý sự kiện save
        binding.btnSave.setOnClickListener {
            saveNewWordsData()
        }

        //Mở màn hình fetchActivity
        binding.btnGoFetch.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

        //Mở màn hình mainActivity
        binding.btnHome.setOnClickListener {
            val intent = Intent(this,NewWordActivity::class.java)
            startActivity(intent)
        }

    }



    private fun saveNewWordsData() {
        //getting value
        val nwWord = binding.edtWord.text.toString()
        val nwMean = binding.edtMean.text.toString()
        val nwExample = binding.edtExample.text.toString()

        //pushing data
        val nwId = dbRef.push().key!!
        val newwords = NewWordModel(nwId,nwWord,nwMean,nwExample)

        //Validating dữ liệu nhập vào
        if(nwWord.isEmpty()){
            binding.edtWord.error = "Please enter a word"
            return
        }
        if(nwMean.isEmpty()){
            binding.edtMean.error = "Please enter the mean of that word"
            return
        }
        if(nwExample.isEmpty()){
            binding.edtExample.error = "Please enter the example of the word"
            return
        }

        dbRef.child(nwId).setValue(newwords)
            .addOnCompleteListener {
                Toast.makeText(this,"Added data successfully",Toast.LENGTH_SHORT).show()
                clearData()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this," Error ${err.message} There was an error adding data",Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearData() {
        binding.edtWord.setText("")
        binding.edtMean.setText("")
        binding.edtExample.setText("")
    }
}