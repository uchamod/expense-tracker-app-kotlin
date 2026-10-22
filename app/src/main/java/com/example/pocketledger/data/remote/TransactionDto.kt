package com.example.pocketledger.data.remote

import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.google.gson.annotations.SerializedName

data class TransactionResponseDto(
    val id: Long,
    val title: String,
    val category: String,
    val amount: Double,
    val type: String,
    val date: Long,
    val remark: String?
)

data class TransactionRequestDto(
    val title: String,
    val category: String,
    val amount: Double,
    val type: String,
    val date: Long,
    val remark: String?
)

fun TransactionResponseDto.toDomain() =
    Transaction(
        id = id,
        title = title,
        category = TransactionCategory.valueOf(category),
        amount = amount,
        type = TransactionType.valueOf(type),
        date = date,
        remark = remark
    )

fun Transaction.toRequestDto() =
    TransactionRequestDto(
        title = title,
        category = category.name,
        amount = amount,
        type = type.name,
        date = date,
        remark = remark
    )
