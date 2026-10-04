package com.example.pocketledger.ui.screens.transaction

import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType

data class TransactionFormUiState(
    val type: TransactionType = TransactionType.INCOME,

    val category: TransactionCategory? = null,

    val title: String = "",
    val amount: String = "",
    val date: Long? = null,
    val remark: String = "",

    //validation

    val categoryError: String? = null,
    val titleError: String? = null,
    val amountError: String? = null,
    val dateError: String? = null,
    val remarkError: String? = null,
    val isSaving: Boolean = false

)