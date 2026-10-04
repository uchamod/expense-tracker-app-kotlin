package com.example.pocketledger.domain.repository

import com.example.pocketledger.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository{

    fun observeTransactions():
            Flow<List<Transaction>>

    suspend fun createTransaction(
        transaction: Transaction
    )

    suspend fun deleteTransaction(
        id: Long
    )
}