package com.example.demo_tttn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_tttn.adapter.NwAdapter
import com.example.demo_tttn.databinding.ActivityFetchingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var list:ArrayList<NewWordModel>
private lateinit var dbRef:DatabaseReference
private lateinit var binding: ActivityFetchingBinding

class FetchingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNw.layoutManager = LinearLayoutManager(this)
        binding.rvNw.setHasFixedSize(true)
        list = arrayListOf<NewWordModel>()
        GetListNewWord()


    }

    private fun GetListNewWord() {
        binding.rvNw.visibility = View.GONE
        binding.txtLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("NewWords")
        dbRef.addValueEventListener(object : ValueEventListener {
            //override
            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param snapshot The current data at the location
             */
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (nwSnap in snapshot.children) {
                        val nwData = nwSnap.getValue(NewWordModel::class.java)
                        list.add(nwData!!)
                    }
                    val mAdapter = NwAdapter(list)
                    binding.rvNw.adapter = mAdapter

                    //Details
                    mAdapter.setOnItemClickListener(object : NwAdapter.onItemclickListener {
                        override fun onItemClick(position: Int) {
                            val intent =
                                Intent(this@FetchingActivity, WordDetailsActivity::class.java)
                            //put extras
                            intent.putExtra("nwId", list[position].nwId)
                            intent.putExtra("nwWord", list[position].nwWord)
                            intent.putExtra("nwMean", list[position].nwMean)
                            intent.putExtra("nwExample", list[position].nwExample)
                            startActivity(intent)
                        }
                    })

                    binding.rvNw.visibility = View.VISIBLE
                    binding.txtLoadingData.visibility = View.GONE
                }
            }

            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase Database rules. For more information on
             * securing your data, see: [ Security
 * Quickstart](https://firebase.google.com/docs/database/security/quickstart)
             *
             * @param error A description of the error that occurred
             */
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}