package com.example.pocketledger.domain.model



data class Transaction(
    val id: Long = 0,
    val title: String,
    val category: TransactionCategory,
    val amount: Double,
    val type: TransactionType,
    val date: Long,
    val remarks: String?
)