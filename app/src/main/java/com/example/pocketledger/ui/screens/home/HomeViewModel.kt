package com.example.pocketledger.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
   private val repository: TransactionRepository
) : ViewModel(){


    private val _showAllTransaction =MutableStateFlow(false)
    val uiState : StateFlow<HomeUiState> =
        combine(
            repository.observeTransactions(),
            _showAllTransaction
        ){
                transactions,showAll ->
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
                transactions = transactions,
                showAllTransactions = showAll,
                isLoading = false
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(isLoading = true)
        )

    fun isShowAllTransaction(){
        _showAllTransaction.value =!_showAllTransaction.value
    }

    fun deleteTransaction(id: Long) {

        viewModelScope.launch {
            repository.deleteTransaction(id)
        }

    }

}


