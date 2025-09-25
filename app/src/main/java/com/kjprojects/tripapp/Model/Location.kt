package com.kjprojects.tripapp.Model

data class Location(
    val Id: Int = 0,
    val Name: String = ""

){
    override fun toString(): String {
        return Name
    }
}
