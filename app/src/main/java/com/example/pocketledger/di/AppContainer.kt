package com.example.pocketledger.di

import android.content.Context
import com.example.pocketledger.data.remote.TransactionAPI
import com.example.pocketledger.data.repository.TransactionRepositoryImpl
import com.example.pocketledger.domain.repository.TransactionRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(
    context: Context
){
    companion object{
        private const val BASE_URL ="http://127.0.0.1:5214/"
    }

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    private val transactionApi: TransactionAPI= retrofit.create(TransactionAPI::class.java)

    val transactionRepository: TransactionRepository = TransactionRepositoryImpl(transactionApi)
}