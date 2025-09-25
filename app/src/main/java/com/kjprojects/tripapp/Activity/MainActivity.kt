package com.kjprojects.tripapp.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjprojects.tripapp.Adapters.RecommendedAdapter
import com.kjprojects.tripapp.Adapters.TripsAdapter
import com.kjprojects.tripapp.ViewModel.MainViewModel
import com.kjprojects.tripapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupCategoryListener()
    }

    private fun setupCategoryListener() {
        binding.cat1.setOnClickListener {
            startActivity(Intent(
                this, SearchActivity::class.java)
                .putExtra("cat", "1")
            )
        }

        binding.cat2.setOnClickListener {
            startActivity(Intent(
                this, SearchActivity::class.java)
                .putExtra("cat", "2")
            )
        }

        binding.cat3.setOnClickListener {
            startActivity(Intent(
                this, SearchActivity::class.java)
                .putExtra("cat", "3")
            )
        }

        binding.cat4.setOnClickListener {
            startActivity(Intent(
                this, SearchActivity::class.java)
                .putExtra("cat", "4")
            )
        }
    }

    private fun observeViewModel() {
        viewModel.upcomingTrips.observe(this){
            list ->
            binding.progressBarUpcoming.visibility =
                if(list.isEmpty()) View.GONE else View.GONE
            binding.viewUpcoming.apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )
                adapter = TripsAdapter(list)
            }
        }

        viewModel.recommendedPlaces.observe(this) {list->
            binding.progressBarRecommended.visibility = View.GONE
            binding.viewRecommended.apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = RecommendedAdapter(list)
            }
        }
    }
}