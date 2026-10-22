package com.example.pocketledger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pocketledger.domain.model.Transaction

@Composable
fun SwipeToDeleteTransactionItem(
    transaction: Transaction,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState (
        confirmValueChange = {
            dismissValue ->
                if (dismissValue == SwipeToDismissBoxValue.StartToEnd){
                    onDelete(transaction.id)
                }
            false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        modifier = modifier,

        backgroundContent = {
            SwipeDeleteBackground()
        }

    ) {
        TransactionItem(
            transaction = transaction,
            onClick = onClick
        )

    }
}

@Composable
private fun SwipeDeleteBackground(){
    Row(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 20.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete transaction",
            tint = MaterialTheme.colorScheme.onErrorContainer
        )

    }
}