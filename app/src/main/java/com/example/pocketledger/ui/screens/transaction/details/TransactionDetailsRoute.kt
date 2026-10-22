package com.example.pocketledger.ui.screens.transaction.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketledger.PocketLedgerApplication
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.ui.screens.transaction.TransactionFormUiState
import com.example.pocketledger.ui.screens.transaction.TransactionViewModel
import com.example.pocketledger.ui.screens.transaction.TransactionViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun TransactionDetailsRoute(
    transactionId: Long,
    onCallBack:() -> Unit
){

    val context = LocalContext.current

    val app = context.applicationContext as PocketLedgerApplication

    val vm: TransactionViewModel = viewModel(
        key = "detail-$transactionId",
        factory = TransactionViewModelFactory(
            app.container.transactionRepository
        )
    )

    val uiState by vm.uiState.collectAsStateWithLifecycle()
    fun uiStateToTransaction(state: TransactionFormUiState) : Transaction{
        return Transaction(
            title = state.title.trim(),
            category = requireNotNull(state.category),
            amount = state.amount.toDouble(),
            type = state.type,
            date =  requireNotNull(state.date),
            remark = state.remark.trim().takeIf { it.isNotBlank() }

        )
    }
    var loading by remember(transactionId) { mutableStateOf(true) }
    var loadError by remember(transactionId) { mutableStateOf<String?>(null) }
    var actionError by remember { mutableStateOf<String?>(null) }
    var confirmDelete by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(transactionId) {
        val result = vm.loadTransaction(transactionId)
        loadError = result.exceptionOrNull()?.let {
            "Unable to load transaction."
        }
        loading = false
    }

    when{
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }loadError != null->{
            Text(loadError!!)
        }
        else -> {
            SingleTransactionScreen(
                transaction = uiStateToTransaction(uiState),
                onBackClick = onCallBack,
                onUpdateCallBack = {
                    scope.launch {
                        vm.updateTransaction()
                    }

                },
                onDeleteConfirmed = {
                    scope.launch {
                        vm.deleteCurrentTransaction()
                    }
                }

            )
        }
    }


}