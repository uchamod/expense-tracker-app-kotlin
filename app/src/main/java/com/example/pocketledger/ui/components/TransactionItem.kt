package com.example.pocketledger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.ui.theme.AppIncomeColor
import com.example.pocketledger.ui.theme.AppRed

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background).clickable(
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (transaction.type == TransactionType.INCOME){
                Icons.Default.TrendingUp
            } else {
                Icons.Default.TrendingDown
            },
            tint = if (transaction.type == TransactionType.INCOME){
                AppIncomeColor
            } else {
                AppRed
            },
            contentDescription = null,
            modifier = Modifier.padding(4.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = transaction.category.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = if (transaction.type == TransactionType.INCOME) {
                "+ ${transaction.amount}"
            } else {
                "- ${transaction.amount}"
            },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = if (transaction.type == TransactionType.INCOME) AppIncomeColor else AppRed
        )
    }

}


