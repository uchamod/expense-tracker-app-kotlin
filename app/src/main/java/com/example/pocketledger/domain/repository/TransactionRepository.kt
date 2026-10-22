package com.example.pocketledger.domain.repository

import com.example.pocketledger.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository{

    fun observeTransactions():
            Flow<List<Transaction>>

    suspend fun refreshTransactions()

    suspend fun getTransactionById(id: Long) : Transaction?


    suspend fun createTransaction(
        transaction: Transaction
    )

    suspend fun updateTransaction(
        transaction: Transaction
    )


    suspend fun deleteTransaction(
        id: Long
    )
}