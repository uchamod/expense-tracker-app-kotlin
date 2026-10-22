package com.example.pocketledger.data.repository


import com.example.pocketledger.data.local.TransactionEntity
import com.example.pocketledger.data.remote.TransactionAPI
import com.example.pocketledger.data.remote.toDomain
import com.example.pocketledger.data.remote.toRequestDto
import com.example.pocketledger.domain.model.Transaction
import com.example.pocketledger.domain.model.TransactionCategory
import com.example.pocketledger.domain.model.TransactionType
import com.example.pocketledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException

class TransactionRepositoryImpl(
    private val api: TransactionAPI
) : TransactionRepository {

    private val _transaction = MutableStateFlow<List<Transaction>>(emptyList())

    override fun observeTransactions() = _transaction.asStateFlow()


    //getall
    override suspend fun refreshTransactions() {
       val result = api.getAllTransaction()

       _transaction.value = sortTransactions(
           result.map { it.toDomain() }
       )
    }
    //getbyid
    override suspend fun getTransactionById(id: Long): Transaction? {
       return try {
           api.getTransaction(id).toDomain()
       }catch (e: HttpException){
           if (e.code() == 404) null else throw e
       }
    }
   //create
    override suspend fun createTransaction(transaction: Transaction) {
       val result = api.crateTransaction(transaction.toRequestDto()).toDomain()

    _transaction.update{
        current -> sortTransactions(current + result)
    }
    }
    //update
    override suspend fun updateTransaction(transaction: Transaction) {
       val result = api.updateTransaction(
           transaction.id,
           transaction.toRequestDto()
       ).toDomain()

    _transaction.update { current ->
        sortTransactions(
            current.map {
                if(it.id == result.id) result else it
            }
        )
    }
    }

    override suspend fun deleteTransaction(id: Long) {
       val result = api.deleteTransaction(id)

        if(!result.isSuccessful){
            throw HttpException(result)
        }

        _transaction.update { current ->
            current.filterNot { it.id == id }
        }
    }

    private fun sortTransactions(
        items: List<Transaction>
    ): List<Transaction> {
        return items.sortedWith(
            compareByDescending<Transaction> { it.date }
                .thenByDescending { it.id }
        )
    }

    private fun Transaction.toEntity() =
        TransactionEntity(
            id = id,
            type = type.name,
            category = category.name,
            title = title,
            amount = amount,
            date = date,
            remark = remark
        )

    private fun TransactionEntity.toDomain() =
        Transaction(
            id = id,
            type = TransactionType.valueOf(type),
            category = TransactionCategory.valueOf(category),
            title = title,
            amount = amount,
            date = date,
            remark = remark
        )

}