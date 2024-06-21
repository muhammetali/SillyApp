package com.example.sillyapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var sillyPreferences: SharedPreferences
    private val sillyChatId = "-1002151750178" // Kendi chat ID'nizi buraya ekleyin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sillyPreferences = getSharedPreferences("silly_prefs", Context.MODE_PRIVATE)
        val sillyUniqueId = sillyPreferences.getString("silly_unique_id", null)

        if (sillyUniqueId == null) {
            val newSillyUniqueId = UniqueIdGenerator.generateUniqueId()
            sillyPreferences.edit().putString("silly_unique_id", newSillyUniqueId).apply()
            sendSillyUniqueIdToTelegram(newSillyUniqueId)
        } else {
            Log.d("MainActivity", "Unique ID already exists: $sillyUniqueId")
        }
    }

    private fun sendSillyUniqueIdToTelegram(sillyUniqueId: String) {
        Log.d("MainActivity", "Sending message to chat ID: $sillyChatId with API key: ${BuildConfig.TELEGRAM_API_KEY}")

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("MainActivity", "Attempting to send message: $sillyUniqueId")
                val response = WackyRetrofitInstance.api.sendSillyMessage(sillyChatId, "New silly device ID: $sillyUniqueId")
                Log.d("MainActivity", "Response Code: ${response.code()}")
                Log.d("MainActivity", "Response Message: ${response.message()}")
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Message sent successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainActivity", "Failed to send message: $errorBody, Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception occurred while sending message", e)
            }
        }
    }
}
