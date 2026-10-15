package com.example.pocketledger.ui.screens.home

import com.example.pocketledger.domain.model.Transaction

data class HomeUiState(

    val balance: Double = 0.0,

    val totalIncome: Double = 0.0,

    val totalExpense: Double = 0.0,

    val transactions: List<Transaction> = emptyList(),

    val isLoading: Boolean = false,

    val showAllTransactions: Boolean = false,

    val errorMessage: String? = null
)