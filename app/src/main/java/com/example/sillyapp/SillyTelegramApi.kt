package com.example.sillyapp

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface SillyTelegramApi {
    @POST("bot7476810555:AAFjmNHR1KG6wKtcLa11Ol37kWULCrY-J1M/sendMessage")
    suspend fun sendSillyMessage(@Query("chat_id") chatId: String, @Query("text") text: String): Response<Unit>
}
