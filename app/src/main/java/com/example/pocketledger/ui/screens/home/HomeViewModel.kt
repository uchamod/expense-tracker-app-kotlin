package com.example.pocketledger.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class HomeViewModel(
   private val repository: TransactionRepository
) : ViewModel(){


    private val _showAllTransaction = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val uiState : StateFlow<HomeUiState> =
        combine(
            repository.observeTransactions(),
            _showAllTransaction,
            _isLoading,
            _errorMessage
        ){
                transactions,showAll,loading,error ->
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
                isLoading = loading,
                errorMessage = error
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(isLoading = true)
        )
   init {
       refreshTransactions()
   }
    fun refreshTransactions() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                repository.refreshTransactions()
                _errorMessage.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _errorMessage.value =
                    "Unable to load transactions. Check your connection.${e}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun isShowAllTransaction(){
        _showAllTransaction.value =!_showAllTransaction.value
    }

    fun deleteTransaction(id: Long) {

        viewModelScope.launch {
            try {
                repository.deleteTransaction(id)
                _errorMessage.value=null
            }catch (e: CancellationException){
                throw e
            }catch (e: Exception){
                _errorMessage.value =
                    "Unable to delete the transaction."
            }

        }

    }

}


