package com.example.pocketledger.ui.screens.transaction.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocketledger.PocketLedgerApplication
import com.example.pocketledger.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

@Composable
fun TransactionDetailsRoute(
    transactionId: Long,
    onCallBack:() -> Unit
){
    val context = LocalContext.current

    val app = context.applicationContext as PocketLedgerApplication

    val repository = app.container.transactionRepository

    val transactionFlow: Flow<List<Transaction>?> =
        remember(repository) {
            repository.observeTransactions()
        }

    val transactions by transactionFlow.collectAsStateWithLifecycle(initialValue = null)

    when(val loadedTransaction = transactions){
        null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else ->{
            val selectedTransaction = loadedTransaction.firstOrNull{
                it.id == transactionId
            }
        if(selectedTransaction != null){
            SingleTransactionScreen(
                transaction = selectedTransaction,
                onBackClick = onCallBack
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Transaction not found")

                    TextButton(onClick = onCallBack) {
                        Text("Go Back")
                    }
                }
            }
        }
        }
    }


}