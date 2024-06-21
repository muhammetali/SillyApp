package com.example.sillyapp

import java.text.SimpleDateFormat
import java.util.*

object UniqueIdGenerator {
    fun generateUniqueId(): String {
        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        val date = dateFormat.format(Date())

        val randomLetters = (1..2).map { ('a'..'z').random() }.joinToString("")
        val randomDigits = (1..2).map { ('0'..'9').random() }.joinToString("")
        val randomLetters2 = (1..2).map { ('a'..'z').random() }.joinToString("")
        val randomDigits2 = (1..2).map { ('0'..'9').random() }.joinToString("")
        val randomLetters3 = (1..2).map { ('a'..'z').random() }.joinToString("")

        return "v0id-$date-$randomLetters-$randomDigits-$randomLetters2-$randomDigits2-$randomLetters3-bot"
    }
}
