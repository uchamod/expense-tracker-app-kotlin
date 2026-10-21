package com.example.pocketledger.ui.components.Transaction


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class TransactionConfirmAction {
    UPDATE,
    DELETE
}

@Composable
fun TransactionConfirmDialog(
    action: TransactionConfirmAction,
    onConfirm:()-> Unit,
    onDismiss:()-> Unit,
){
    val isDelete = action == TransactionConfirmAction.DELETE

    val actionColor = if (isDelete) {
        MaterialTheme.colorScheme.error
    } else {
        Color(0xFF21834A)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = if (isDelete) {
                    Icons.Default.Delete
                } else {
                    Icons.Default.Edit
                },
                contentDescription = null,
                tint = actionColor
            )
        },
        title = {
            Text(
                if (isDelete) {
                    "Delete Transaction?"
                } else {
                    "Update Transaction?"
                }
            )
        },
        text = {
            Text(
                if (isDelete) {
                    "Are you sure you want to permanently delete this transaction?"
                } else {
                    "Are you sure you want to save these changes?"
                }
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = actionColor,
                    contentColor = Color.White
                )
            ) {
                Text(if (isDelete) "Delete" else "Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}