package com.kjprojects.tripapp.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kjprojects.tripapp.Model.Place
import com.kjprojects.tripapp.Model.Trip
import com.kjprojects.tripapp.Repository.TripsRepository

class MainViewModel (
    private val repository: TripsRepository = TripsRepository()
): ViewModel() {
    val upcomingTrips: LiveData<List<Trip>> = repository.getUpcomingTrips()
    val recommendedPlaces: LiveData<List<Place>> = repository.getRecommendedTrips()
}