package com.kjprojects.tripapp.Model
import java.io.Serializable

data class Trip(
    val companyLogo: String = "",
    val companyName: String = "",
    val arriveTime: String = "",
    val date: String = "",
    val from: String = "",
    val fromShort: String = "",
    val price: Double = 0.0,
    val time: String = "",
    val to: String = "",
    val score: Int = 0,
    val toShort: String = ""
): Serializable
