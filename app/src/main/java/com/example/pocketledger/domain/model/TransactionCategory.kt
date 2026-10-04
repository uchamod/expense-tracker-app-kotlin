package com.example.pocketledger.domain.model

enum class TransactionCategory(
    val displayName: String,
    val type: TransactionType
){
    // Income
    SALARY(
        "Salary",
        TransactionType.INCOME
    ),

    PROFIT(
        "Profit",
        TransactionType.INCOME
    ),

    TIP(
        "Tip",
        TransactionType.INCOME
    ),

    // Expense
    PERSONAL(
        "Personal",
        TransactionType.EXPENSE
    ),

    FOOD(
        "Food",
        TransactionType.EXPENSE
    ),

    TRANSPORT(
        "Transport",
        TransactionType.EXPENSE
    )
}