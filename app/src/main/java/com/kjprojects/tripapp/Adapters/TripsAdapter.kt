package com.kjprojects.tripapp.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kjprojects.tripapp.Model.Trip
import com.kjprojects.tripapp.databinding.ViewholderTripBinding

class TripsAdapter (private val trips: List<Trip>)
    :RecyclerView.Adapter<TripsAdapter.Viewholder>() {

        private lateinit var context: Context
    class Viewholder(val binding: ViewholderTripBinding):
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderTripBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: TripsAdapter.Viewholder, position: Int) {
        val trip = trips[position]
        Glide.with(context)
            .load(trip.companyLogo)
            .into(holder.binding.logo)

        holder.binding.companyTxt.text = trip.companyName
        holder.binding.fromTxt.text = trip.from
        holder.binding.fromShortTxt.text = trip.fromShort
        holder.binding.toTxt.text = trip.to
        holder.binding.toShortTxt.text = trip.toShort
        holder.binding.arrivalTxt.text = trip.arriveTime
        holder.binding.scoreTxt.text = trip.score.toString()
        holder.binding.priceTxt.text = "\$${trip.price}/per"
    }

    override fun getItemCount(): Int = trips.size
}