package com.example.sillyapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SillyTelegramApi {
    @GET("sendMessage")
    suspend fun sendSillyMessage(
        @Query("chat_id") chatId: String,
        @Query("text") text: String
    ): Response<Unit>
}
