package com.example.pocketledger.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketledger.PocketLedgerApplication

@Composable
fun HomeRoute(
    onTransactionClick:(Long) -> Unit
){
    val context = LocalContext.current
    val app = context.applicationContext as PocketLedgerApplication

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            app.container.transactionRepository
        )
    )

    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        showAllTransaction = {
            viewModel.isShowAllTransaction()
        },
        deleteTransaction ={ id ->
            viewModel.deleteTransaction(id)
        },
        onTransactionClick = onTransactionClick,
        onRefresh = viewModel::refreshTransactions
    )
}
