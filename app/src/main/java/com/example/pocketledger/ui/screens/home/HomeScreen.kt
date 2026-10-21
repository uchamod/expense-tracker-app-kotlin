package com.example.pocketledger.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocketledger.ui.components.SummaryCard
import com.example.pocketledger.ui.components.SwipeToDeleteTransactionItem
import com.example.pocketledger.ui.theme.AppBlue
import com.example.pocketledger.ui.theme.AppExpenseColor
import com.example.pocketledger.ui.theme.AppIncomeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    showAllTransaction : () -> Unit,
    deleteTransaction : (Long) -> Unit,
    onTransactionClick: (Long) -> Unit
){

    val displayedTransactions =
        if (uiState.showAllTransactions) {
            uiState.transactions
        } else {
            uiState.transactions.take(6)
        }

    Scaffold(
       modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF)),
        topBar = {
            TopAppBar(
                title = { Text("PocketLeger")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
        modifier = Modifier
            .fillMaxSize().padding(innerPadding)
            .padding(horizontal =16.dp)
    ) {
//total balance row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Rs. ${uiState.balance}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
          item {
              Spacer(
                  modifier = Modifier.height(16.dp)
              )

              Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(12.dp)
              )   {
                  SummaryCard(
                      title = "Income",
                      name = "Your total income in last 30 days",
                      amont = "Rs. ${uiState.totalIncome}",
                      icon = Icons.Default.TrendingUp,
                      containerColors = AppIncomeColor,
                      modifier = Modifier.weight(1f)
                  )

                  SummaryCard(
                      title = "Expenses",
                      name = "Your total expense in last 30 days",
                      amont = "Rs. ${uiState.totalExpense}" ,
                      icon = Icons.Default.TrendingDown,
                      containerColors = AppExpenseColor,
                      modifier = Modifier.weight(1f)
                  )
              }
          }
           item {
               Spacer(
                   modifier = Modifier.height(28.dp)
               )

               Row(
                   modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceBetween,
                   verticalAlignment = Alignment.CenterVertically
               ) {

                   Text(
                       text = "Recent Transactions",
                       style = MaterialTheme.typography.titleMedium,
                       fontWeight = FontWeight.SemiBold
                   )

                   TextButton(
                       onClick = showAllTransaction,
                       enabled = !uiState.isLoading
                   ) {
                       Text( text = if(uiState.showAllTransactions) {
                           "See Less"
                       }else{
                           "See All"
                       } ,

                               color = AppBlue

                       )
                   }
               }
           }
            items(displayedTransactions, key = {
                transition ->
                transition.id
            }){
                transactions ->
                Column() {
                    Spacer(modifier = Modifier.height(8.dp))
                    SwipeToDeleteTransactionItem(
                        transaction = transactions,
                        onDelete = deleteTransaction,
                        onClick = {
                            onTransactionClick(transactions.id)
                        }

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                HorizontalDivider()
            }

    }
    }
}