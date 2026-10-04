package com.example.pocketledger.ui.screens.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketledger.PocketLedgerApplication

@Composable
fun TransactionRoute(){
    val context = LocalContext.current
    val app = context.applicationContext as PocketLedgerApplication

    val viewModel: TransactionViewModel =
        viewModel(
            factory = TransactionViewModelFactory(
                 app.container.transactionRepository
            )
        )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TransactionsScreen(
        uiState = uiState,
        createTransaction = {
            viewModel.createTransaction {  }
        },
        onTypeChange = viewModel::onTypeChange,
        onDateChanged =  viewModel::onDateChanged,
        onTitleChange =  viewModel::onTitleChange,
        onAmountChanged =  viewModel::onAmountChanged,
        onRemarkChanged =  viewModel::onRemarkChanged,
        onCategoryChange =  viewModel::onCategoryChange
    )
}