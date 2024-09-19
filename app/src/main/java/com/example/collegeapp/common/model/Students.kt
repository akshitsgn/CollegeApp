package com.example.collegeapp.common.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Students(
    val id: String="",
    val late: Boolean=false,
    val leave: Boolean = false,
    val name: String="",
    val regNumber: String="",
    val hosteler: Boolean=false,
    val hostel : String="",
    val inTime: String="",
    val date: String = getCurrentDate(),
    val imageUrl: String?=""
){
    companion object {
        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}