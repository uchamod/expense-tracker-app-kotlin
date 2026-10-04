package com.example.pocketledger.data.repository

import com.example.pocketledger.data.local.TransactionDao
import com.example.pocketledger.data.local.TransactionEntity
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun observeTransactions(): Flow<List<Transaction>> {
       return dao.observeTransactions()
           .map {
               entities ->
               entities.map {
                   it.toDomain()
               }
           }
    }

    override suspend fun createTransaction(transaction: Transaction) {
        dao.insertTransaction(
            transaction.toEntity()
        )
    }

    override suspend fun deleteTransaction(id: Long) {
       dao.deleteTransaction(id)
    }

    private fun Transaction.toEntity() =
        TransactionEntity(
            id = id,
            type = type.name,
            category = category.name,
            title = title,
            amount = amount,
            date = date,
            remarks = remarks
        )

    private fun TransactionEntity.toDomain() =
        Transaction(
            id = id,
            type = TransactionType.valueOf(type),
            category = TransactionCategory.valueOf(category),
            title = title,
            amount = amount,
            date = date,
            remarks = remarks
        )

}