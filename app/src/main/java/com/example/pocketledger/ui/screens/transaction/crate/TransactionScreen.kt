package com.example.pocketledger.ui.screens.transaction.crate


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.ui.components.Transaction.TransactionForm
import com.example.pocketledger.ui.screens.transaction.TransactionFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    uiState: TransactionFormUiState,
    onTypeChange: (TransactionType) -> Unit,
    onCategoryChange: (TransactionCategory) -> Unit,
    onTitleChange: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onRemarkChanged: (String) -> Unit,
    onDateChanged: (Long) -> Unit,
    createTransaction: () -> Unit,
    submitLabel: String = "Create Transaction",
//    onDelete: (() -> Unit)? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Create Transaction",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            TransactionForm(
                uiState = uiState,
                onTypeChange = onTypeChange,
                onCategoryChange = onCategoryChange,
                onTitleChange = onTitleChange,
                onAmountChanged = onAmountChanged,
                onRemarkChanged = onRemarkChanged,
                onDateChanged = onDateChanged
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = createTransaction,
                    enabled = !uiState.isSaving,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) {
                    Text(submitLabel)
                }

//                if (onDelete != null) {
//                    Button(
//                        onClick = onDelete,
//                        enabled = !uiState.isSaving,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.error
//                        ),
//                        modifier = Modifier
//                            .weight(1f)
//                            .height(52.dp)
//                    ) {
//                        Text("Delete")
//                    }
//                }
            }
        }
    }
}
