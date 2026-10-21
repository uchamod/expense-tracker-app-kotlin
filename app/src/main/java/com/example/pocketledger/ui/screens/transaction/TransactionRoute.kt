package com.example.pocketledger.ui.screens.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketledger.PocketLedgerApplication
import com.example.pocketledger.ui.components.Transaction.TransactionCreatePopUp
import com.example.pocketledger.ui.screens.transaction.crate.TransactionsScreen
import kotlinx.coroutines.launch

@Composable
fun TransactionRoute(){

    var showPopup by remember {
        mutableStateOf(false)
    }

    var popupSuccess by remember {
        mutableStateOf(false)
    }

    var popupMessage by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

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
            scope.launch {

                val result = viewModel.createTransaction()

                if (result.isSuccess) {
                    popupSuccess = true

                    popupMessage = "Transaction created successfully."
                } else {
                    popupSuccess = false

                    popupMessage = "Failed to create transaction."
                }
                showPopup = true
            }
        },
        onTypeChange = viewModel::onTypeChange,
        onDateChanged = viewModel::onDateChanged,
        onTitleChange = viewModel::onTitleChange,
        onAmountChanged = viewModel::onAmountChanged,
        onRemarkChanged = viewModel::onRemarkChanged,
        onCategoryChange = viewModel::onCategoryChange
    )
    if (showPopup) {

        TransactionCreatePopUp(
            isSuccess = popupSuccess,
            message = popupMessage,
            onDismiss = {
                showPopup = false
            }
        )
    }
}