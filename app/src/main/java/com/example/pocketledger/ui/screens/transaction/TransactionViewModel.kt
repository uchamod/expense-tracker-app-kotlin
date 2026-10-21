package com.example.pocketledger.ui.screens.transaction

import androidx.lifecycle.ViewModel
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class TransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel(){

    private val _uiState =
        MutableStateFlow(
            TransactionFormUiState()
        )

    val uiState = _uiState.asStateFlow()

    val availableCategories get() = TransactionCategory.entries.filter {
        it.type == _uiState.value.type
    }

    fun onTypeChange(
        type : TransactionType
    ){
        _uiState.update {
            it.copy(
                type = type,
                // reset because old category
                // may belong to other type
                category = null,
                categoryError = null
            )
        }
    }

    fun onCategoryChange(
        category: TransactionCategory
    ){
        _uiState.update {
            it.copy(
                category = category,
                categoryError = null
            )
        }
    }

    fun onTitleChange(
        value: String
    ){
        if(value.length <= 60){
            _uiState.update {
              it.copy(
                  title = value,
                  titleError = null
              )
            }
        }
    }

    fun onAmountChanged(
        value: String
    ){
        val validFormat = value.matches(
            Regex( "^\\d{0,9}(\\.\\d{0,2})?$")
        )
        if(validFormat){
            _uiState.update {
                it.copy(
                    amount = value,
                    amountError = null
                )
            }
        }
    }

    fun onRemarkChanged(
        value: String
    ){
        if(value.length <= 250){
            _uiState.update {
                it.copy(
                    remark = value,
                    remarkError = null
                )
            }
        }
    }

    fun onDateChanged(
        value: Long
    ){
        _uiState.update {
            it.copy(
                date = value,
                dateError = null
            )
        }
    }

    private fun validate(): Boolean {
        val state = _uiState.value

        val categoryError =
            if (state.category == null)
                "Please select a category"
            else
                null

        val titleError =
            when {
                state.title.isBlank() ->
                    "Title is required"
                state.title.trim().length < 2 ->
                    "Title is too short"
                else ->
                    null
            }

        val parsedAmount =
            state.amount.toDoubleOrNull()

        val amountError =
            when {
                state.amount.isBlank() ->
                    "Amount is required"
                parsedAmount == null ->
                    "Enter a valid amount"
                parsedAmount <= 0 ->
                    "Amount must be greater than 0"
                else ->
                    null
            }

        val dateError =
            when {
                state.date == null ->
                    "Please select transaction date"
                state.date > System.currentTimeMillis() ->
                    "Transaction date cannot be in the future"
                else ->
                    null
            }

        _uiState.update {
            it.copy(
                categoryError = categoryError,
                titleError = titleError,
                amountError = amountError,
                dateError = dateError
            )
        }

        return categoryError == null &&
                titleError == null &&
                amountError == null &&
                dateError == null
    }

   suspend fun createTransaction(): Result<Unit>{
        if(!validate()){
            return Result.failure(
                Exception("Please check the required fields.")
            )
        }

            val state = _uiState.value


            _uiState.update {
                it.copy(
                    isSaving = true
                )
            }
           return try {
                val transaction = Transaction(
                    title = state.title.trim(),
                    category = requireNotNull(state.category),
                    amount = state.amount.toDouble(),
                    type = state.type,
                    date =  requireNotNull(state.date),
                    remarks = state.remark.trim().takeIf { it.isNotBlank() }
                )

                repository.createTransaction(transaction)
                resetForm()
               Result.success(Unit)
            }catch (exception: Exception){
               Result.failure(exception)
            }finally {
                _uiState.update {
                    it.copy(
                        isSaving = false
                    )
                }
            }
   }

    private fun resetForm() {
        _uiState.value =
            TransactionFormUiState(
                type = TransactionType.INCOME
            )
    }
}