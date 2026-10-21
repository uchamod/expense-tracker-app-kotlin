package com.example.pocketledger.ui.components.Transaction

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    type: TransactionType,
    selectedCategory: TransactionCategory?,
    error: String?,
    onSelected: (TransactionCategory) -> Unit
){

    var expanded by remember {
        mutableStateOf(false)
    }

    val categories = TransactionCategory.entries.filter {
        it.type == type
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedCategory?.displayName ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Category") },
            placeholder = { Text("Select category") },
            isError = error != null,
            supportingText = {
                error?.let { Text(it) }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categories.forEach {
                DropdownMenuItem(
                    text = { Text(it.displayName) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}