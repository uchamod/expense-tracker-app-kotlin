package com.example.pocketledger.ui.components.Transaction

import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.pocketledger.domain.model.TransactionType

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun TransactionTypeDropDown(
    selectedType: TransactionType,
    onSelected: (TransactionType) -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded =!expanded
        }
    ) {
        OutlinedTextField(
            value = selectedType.name,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Type")
            },
            placeholder = {
                Text(
                    "Select category"
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded=expanded
                )
            },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            TransactionType.entries.forEach {
                DropdownMenuItem(
                    text = {
                        Text(it.name)
                    },
                    onClick = {
                        onSelected(it)
                        expanded = false

                    }
                )
            }
        }
    }
}