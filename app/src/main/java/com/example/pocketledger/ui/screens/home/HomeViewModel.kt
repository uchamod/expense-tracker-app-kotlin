package com.example.pocketledger.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    repository: TransactionRepository
) : ViewModel(){

    val uiState : StateFlow<HomeUiState> =
        repository.observeTransactions().map {
            transactions ->
                val income = transactions.filter {
                    it.type == TransactionType.INCOME
                }.sumOf {
                    it.amount
                }
            val expense = transactions.filter {
                it.type == TransactionType.EXPENSE
            }.sumOf {
                it.amount
            }

            HomeUiState(
                balance = income - expense,
                totalIncome = income,
                totalExpense = expense,
                transactions = transactions.take(6),
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(isLoading = true)
        )

    }


