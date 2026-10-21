package com.example.pocketledger.ui.screens.transaction.crate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.ui.components.Transaction.CategoryDropdown
import com.example.pocketledger.ui.components.Transaction.TransactionForm
import com.example.pocketledger.ui.components.Transaction.TransactionTypeDropDown
import com.example.pocketledger.ui.screens.transaction.TransactionFormUiState
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
    createTransaction: () -> Unit
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
            Button(
                onClick = createTransaction,
                enabled = !uiState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Create Transaction")
                }
            }


        }
    }
}
