package com.example.demo_tttn.view

import com.example.demo_tttn.NewWordModel
import com.example.demo_tttn.databinding.ActivityFetchingBinding
import com.google.firebase.database.DatabaseReference

private lateinit var list: ArrayList<NewWordModel>
private lateinit var dbRef: DatabaseReference
private lateinit var binding: ActivityFetchingBinding

