package com.example.pocketledger.di

import android.content.Context
import androidx.room.Room
import com.example.pocketledger.data.local.PocketLedgerDatabase
import com.example.pocketledger.data.repository.TransactionRepositoryImpl
import com.example.pocketledger.domain.repository.TransactionRepository

class AppContainer(
    context: Context
){
    private val database = Room.databaseBuilder(
        context, 
        PocketLedgerDatabase::class.java,
        "pocket_ledger.db"
    )
    .fallbackToDestructiveMigration()
    .build()

    val transactionRepository: TransactionRepository = TransactionRepositoryImpl(database.transactionDao())
}