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

        setupSillyUniqueId()
    }

    private fun setupSillyUniqueId() {
        sillyPreferences = getSharedPreferences("silly_prefs", Context.MODE_PRIVATE)
        val sillyUniqueId = sillyPreferences.getString("silly_unique_id", null)

        if (sillyUniqueId == null) {
            val newSillyUniqueId = UniqueIdGenerator.generateUniqueId()
            sillyPreferences.edit().putString("silly_unique_id", newSillyUniqueId).apply()
            sendSillyUniqueIdToTelegram(newSillyUniqueId)
        } else {
            Log.d("MainActivity", "Benzersiz ID zaten var: $sillyUniqueId")
        }
    }

    private fun sendSillyUniqueIdToTelegram(sillyUniqueId: String) {
        Log.d("MainActivity", "Mesajı şu sohbet ID'sine gönderiliyor: $sillyChatId ve API anahtarı: ${BuildConfig.TELEGRAM_API_KEY}")

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("MainActivity", "Mesaj gönderilmeye çalışılıyor: $sillyUniqueId")
                val response = WackyRetrofitInstance.api.sendSillyMessage(sillyChatId, "YENI BOT ID: $sillyUniqueId")
                Log.d("MainActivity", "Yanıt Kodu: ${response.code()}")
                Log.d("MainActivity", "Yanıt Mesajı: ${response.message()}")
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Mesaj başarıyla gönderildi")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainActivity", "Mesaj gönderilemedi: $errorBody, Kod: ${response.code()}, Mesaj: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Mesaj gönderilirken hata oluştu", e)
            }
        }
    }
}
