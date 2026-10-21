package com.example.pocketledger.navigations

sealed class AppDestination(
    val route: String,
    val title: String
) {

    data object Home : AppDestination(
        route = "home",
        title = "Home"
    )

    data object Transactions : AppDestination(
        route = "transactions",
        title = "Transactions"
    )

    data object Settings : AppDestination(
        route = "settings",
        title = "Settings"
    )

    data object TransactionDetails  : AppDestination(
        route = "transaction/{transactionId}",
        title = "Transaction Details"
    ){
        fun createRoute(
            transactionId: Long
        ) : String {
            return "transaction/$transactionId"
        }
    }
}