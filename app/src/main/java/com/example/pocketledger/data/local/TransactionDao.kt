package com.example.pocketledger.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao{

    @Insert
    suspend fun insertTransaction(
        transaction: TransactionEntity
    )

    @Query(
        """
        SELECT *
        FROM transactions
        ORDER BY date DESC, id DESC
        """
    )
    fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query(
        "DELETE FROM transactions WHERE id = :id"
    )
    suspend fun deleteTransaction(
        id: Long
    )
}