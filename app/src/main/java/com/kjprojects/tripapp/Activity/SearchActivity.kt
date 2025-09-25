package com.kjprojects.tripapp.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kjprojects.tripapp.Model.Location
import com.kjprojects.tripapp.R
import com.kjprojects.tripapp.databinding.ActivitySearchBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var adultPassenger = 1
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private val calendar = Calendar.getInstance()
    var database: FirebaseDatabase?= null
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()

        initLocation()
        initPassengers()
        initDatePickup()
        setVariable()
    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener { finish() }
            category = intent.getStringExtra("cat")!!
            when (category) {
                "1"-> {
                    logoImg.setImageResource(R.drawable.train)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_green)
                }

                "2"-> {
                    logoImg.setImageResource(R.drawable.airplane)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_blue)
                }

                "3"-> {
                    logoImg.setImageResource(R.drawable.boat)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_purple)
                }

                "4"-> {
                    logoImg.setImageResource(R.drawable.bus)
                    mainLayout.setBackgroundResource(R.drawable.gradient_bg_orange)
                }
            }

            searchBtn.setOnClickListener {
                val intent = Intent(this@SearchActivity, ResultActivity::class.java)
                intent.putExtra("from",(binding.fromSp.selectedItem as Location).Name)
                intent.putExtra("to",(binding.toSp.selectedItem as Location).Name)
                intent.putExtra("date",binding.departDateTxt.text.toString())
                intent.putExtra("numPassenger", adultPassenger)
                startActivity(intent)

            }
        }
    }

    private fun initDatePickup() {
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        binding.apply {
            departDateTxt.text = dateFormat.format(today.time)
            arrivalDateTxt.text = dateFormat.format(tomorrow.time)
            arrivalDateTxt.setOnClickListener { showDatePickerDialog(departDateTxt) }
            arrivalDateTxt.setOnClickListener { showDatePickerDialog(arrivalDateTxt) }
        }
    }

    private fun showDatePickerDialog(text: TextView) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, {_,selectedYear,selectedMonth, selectedDay->
            calendar.set(selectedYear,selectedMonth,selectedDay)
            text.text = dateFormat.format(calendar.time)
        },year,month,day).show()
    }

    private fun initPassengers() {
        binding.apply {
            plusAdultBtn.setOnClickListener {
                adultPassenger ++
                adultTxt.text = "$adultPassenger Adult"
            }
            minusAdultBtn.setOnClickListener {
                if(adultPassenger > 1){
                    adultPassenger--
                    adultTxt.text = "$adultPassenger Adult"
                }
            }
        }
    }

    private fun initLocation() {
        binding.apply {
            progressBarFrom.visibility = View.VISIBLE
            progressBarTo.visibility = View.VISIBLE
            val myRef: DatabaseReference = database!!.getReference("Locations")
            val list = ArrayList<Location>()
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(issue in snapshot.children){
                            issue.getValue(Location::class.java)?.let{list.add(it)}
                        }
                        val adapter = ArrayAdapter(this@SearchActivity, R.layout.sp_item, list).also {
                            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        }
                        binding.apply {
                            fromSp.adapter = adapter
                            toSp.adapter = adapter
                            fromSp.setSelection(1)
                            progressBarFrom.visibility = View.GONE
                            progressBarTo.visibility = View.GONE
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}

