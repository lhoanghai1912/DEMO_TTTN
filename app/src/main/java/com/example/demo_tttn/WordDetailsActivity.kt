package com.example.demo_tttn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_tttn.databinding.ActivityWordDetailsBinding
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityWordDetailsBinding

class WordDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValuetoView()

        //code for btnDelete
        binding.btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("nwWord").toString()
            )
        }

        //code for btnUpdate
        binding.btnUpdate.setOnClickListener {
            openUpdateForm(
                intent.getStringExtra("nwId").toString(),
                intent.getStringExtra("nwWord").toString()
//                intent.getStringExtra("nwMean").toString(),
//                intent.getStringExtra("nwExample").toString()
            )
        }
        //Mở màn hình insertionActivity
        binding.btnGoInsert.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        //Mở màn hình mainActivity
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, NewWordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openUpdateForm(nwId: String,nwWord: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_form,null)
        mDialog.setView(mDialogView)

        //update data to Dialog
        val edtWord = mDialogView.findViewById<EditText>(R.id.edtWord)
        val edtMean = mDialogView.findViewById<EditText>(R.id.edtMean)
        val edtExample = mDialogView.findViewById<EditText>(R.id.edtExample)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        //get data fetch form to dialog form
        edtWord.setText(intent.getStringExtra("nwWord").toString())
        edtMean.setText(intent.getStringExtra("nwMean").toString())
        edtExample.setText(intent.getStringExtra("nwExample").toString())

        mDialog.setTitle("Updating New Words .....")
        val alterDialog = mDialog.create()
        alterDialog.show()

        //Btn Updatedata click
        btnUpdateData.setOnClickListener {
                updateWordData(
                nwId,
                edtWord.text.toString(),
                edtMean.text.toString(),
                edtExample.text.toString()
            )
            //Noti Updated
            Toast.makeText(applicationContext,"Word Data updated",Toast.LENGTH_SHORT).show()
            //Close Dialog and update data to Fetch Form
            binding.tvNwWord.setText(edtWord.text.toString())
            binding.tvNwMean.setText(edtMean.text.toString())
            binding.tvNwExample.setText(edtExample.text.toString())
            alterDialog.dismiss()

        }
    }

    private fun updateWordData(
        id: String,
        word: String,
        mean: String,
        example: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("NewWords").child(id)
        val wordInfo = NewWordModel(id,word,mean,example)
        dbRef.setValue(wordInfo)

    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("NewWords").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Word has been deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Delete err ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValuetoView() {
        binding.tvNwId.text = intent.getStringExtra("nwId")
        binding.tvNwWord.text = intent.getStringExtra("nwWord")
        binding.tvNwMean.text = intent.getStringExtra("nwMean")
        binding.tvNwExample.text = intent.getStringExtra("nwExample")
    }
}