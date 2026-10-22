package com.example.pocketledger.ui.screens.transaction.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.ui.components.Transaction.TransactionConfirmAction
import com.example.pocketledger.ui.components.Transaction.TransactionConfirmDialog
import com.example.pocketledger.ui.components.Transaction.TransactionForm
import com.example.pocketledger.ui.screens.transaction.TransactionFormUiState

private val amountPattern =
    Regex("^\\d{0,9}(\\.\\d{0,2})?$")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleTransactionScreen(
    transaction: Transaction,
    onBackClick:()-> Unit,

    onUpdateCallBack:() -> Unit,
    onDeleteConfirmed: () -> Unit
){

    var showDatePicker by remember { mutableStateOf(false) }

    var formState by remember(transaction.id) {
        mutableStateOf(
            TransactionFormUiState(
                type = transaction.type,
                category = transaction.category,
                title = transaction.title,
                amount = transaction.amount
                    .toString()
                    .removeSuffix(".0"),
                date = transaction.date,
                remark = transaction.remark.orEmpty()
            )
        )
    }

    var confirmAction by remember {
        mutableStateOf<TransactionConfirmAction?>(null)
    }

   confirmAction?.let { action ->
       TransactionConfirmDialog(
           action = action,
           onDismiss = {
               confirmAction=null
           },
           onConfirm = {
               confirmAction=null

               when(action){
                    TransactionConfirmAction.DELETE -> {
                            onDeleteConfirmed()
                    }
                    TransactionConfirmAction.UPDATE -> {
                           onUpdateCallBack()
                   }
               }
           }
       )
   }

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBar(
                title = {
                    Text("Transaction Details")
                },
                navigationIcon = {
                    IconButton(
                        onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        innerPadding ->
        LazyColumn(  modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
        ) {
            item{
                Text(
                    text =
                        "Edit Transaction",
                         style = MaterialTheme.typography.headlineSmall,
                         fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text =
                        "Review and modify your transaction information.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                )
            }
            item {
                TransactionForm(
                    uiState = formState,
                    onTypeChange = { newType ->
                        formState = formState.copy(
                            type = newType,
                            category = null,
                            categoryError = null
                        )
                    },

                        onCategoryChange = { newCategory ->
                            formState = formState.copy(
                                category = newCategory,
                                categoryError = null
                            )
                        },

                        onTitleChange = { value ->
                            if (value.length <= 60) {
                                formState = formState.copy(
                                    title = value,
                                    titleError = null
                                )
                            }
                        },
                    onAmountChanged = { value ->
                        if (amountPattern.matches(value)) {
                            formState = formState.copy(
                                amount = value,
                                amountError = null
                            )
                        }
                    },

                    onRemarkChanged = { value ->
                        if (value.length <= 250) {
                            formState = formState.copy(
                                remark = value,
                                remarkError = null
                            )
                        }
                    },

                    onDateChanged = { newDate ->
                        formState = formState.copy(
                            date = newDate,
                            dateError = null
                        )
                    }
                )
            }
                //////////////////////////
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                confirmAction =
                                    TransactionConfirmAction.UPDATE
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF21834A),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text("Update")
                        }
                        Button(
                            onClick = {
                                confirmAction =
                                    TransactionConfirmAction.DELETE
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    MaterialTheme.colorScheme.error,
                                contentColor =
                                    MaterialTheme.colorScheme.onError
                            )
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text("Delete")
                        }

                    }
                }



        }
    }


}