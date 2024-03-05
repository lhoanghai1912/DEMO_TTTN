package com.example.demo_tttn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo_tttn.databinding.ActivityNewwordBinding


private lateinit var binding: ActivityNewwordBinding
class NewWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newword)

        //Khởi tạo viewBinding
        binding = ActivityNewwordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Mở màn hình insertionActivity
        binding.btnInsertData.setOnClickListener {
            val intent = Intent(this,InsertionActivity::class.java)
            startActivity(intent)
        }
        //Mở màn hình fetchActivity
        binding.btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}