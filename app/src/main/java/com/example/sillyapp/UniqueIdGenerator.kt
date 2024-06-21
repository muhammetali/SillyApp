package com.example.sillyapp

import java.text.SimpleDateFormat
import java.util.*

object UniqueIdGenerator {
    fun generateUniqueId(): String {
        val dateFormat = SimpleDateFormat("ddMM", Locale.getDefault())
        val date = dateFormat.format(Date())
        val randomString1 = (1..6).map { ('a'..'z').random() }.joinToString("")
        val randomString2 = (1..6).map { ('a'..'z').random() }.joinToString("")
        return "$date-v0id-$randomString1-$randomString2-v0id"
    }
}
