package com.example.pocketledger.data.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransactionAPI{

    @GET("api/transcation")
    suspend fun getAllTransaction(): List<TransactionResponseDto>

    @GET("api/transcation/{id}")
    suspend fun getTransaction( @Path("id") id : Long) : TransactionResponseDto

    @POST("api/transcation")
    suspend fun crateTransaction( @Body request: TransactionRequestDto) : TransactionResponseDto

    @PUT("api/transcation/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Long,
        @Body request: TransactionRequestDto
    ): TransactionResponseDto

    @DELETE("api/transcation/{id}")
    suspend fun deleteTransaction(
        @Path("id") id: Long
    ): Response<Unit>
}