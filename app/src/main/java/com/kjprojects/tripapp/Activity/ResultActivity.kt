package com.kjprojects.tripapp.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.kjprojects.tripapp.Adapters.TripsAdapter
import com.kjprojects.tripapp.Model.Trip
import com.kjprojects.tripapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResultBinding
    private lateinit var from: String
    private lateinit var to: String
    private lateinit var date: String
    var database: FirebaseDatabase?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        getIntentExtra()
        setVariable()
        initList()
    }

    private fun initList() {
        val myRef: DatabaseReference = database!!.getReference("Trips")
        val list = ArrayList<Trip>()
        val query: Query = myRef.orderByChild("from").equalTo(from)
        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(issue in snapshot.children){
                        val trip = issue.getValue(Trip::class.java)
                        trip?.let {
                            if(it.to == to){
                                list.add(it)
                            }
                        }
                    }
                    if(list.isNotEmpty()){
                        binding.searchView.layoutManager = LinearLayoutManager (this@ResultActivity,
                            LinearLayoutManager.VERTICAL,
                            false)
                        binding.searchView.adapter = TripsAdapter(list)
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun getIntentExtra() {
        from = intent.getStringExtra("from")?:""
        to = intent.getStringExtra("to")?:""
        date = intent.getStringExtra("date")?:""
    }
}