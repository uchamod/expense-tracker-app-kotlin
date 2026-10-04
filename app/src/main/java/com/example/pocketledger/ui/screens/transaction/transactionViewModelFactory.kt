package com.example.pocketledger.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pocketledger.domain.repository.TransactionRepository

class TransactionViewModelFactory(
    private val repository:
    TransactionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel>
            create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                TransactionViewModel::class.java
            )
        ) {

            return TransactionViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel"
        )
    }
}