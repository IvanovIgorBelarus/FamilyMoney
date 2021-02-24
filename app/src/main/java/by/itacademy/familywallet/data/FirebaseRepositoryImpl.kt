package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.model.TransactionModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(private val db: FirebaseFirestore) : DataRepository {
    override suspend fun doTransaction(transactionModel: TransactionModel) {
        db.collection(TRANSACTIONS).add(
            mapOf(
                UID to transactionModel.uid,
                TRANSACTION_TYPE to transactionModel.transactionType,
                CATEGORY to transactionModel.transactionCategory,
                VALUE to transactionModel.value,
                DATE to transactionModel.date
            )
        )
    }

    override suspend fun addNewCategory(category: String, type:String) {
        db.collection(CATEGORIES).add(
            mapOf(
                CATEGORY to category,
                TRANSACTION_TYPE to type
            )
        )
    }

    override suspend fun getTransactionsList(): List<TransactionModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<TransactionModel>()
        db.collection(TRANSACTIONS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                list.add(
                    TransactionModel(
                        uid = doc.getString(UID),
                        transactionType = doc.getString(TRANSACTION_TYPE),
                        transactionCategory = doc.getString(CATEGORY),
                        value = doc.getDouble(VALUE),
                        date = doc.getLong(DATE)
                    )
                )
            }
            continuation.resume(list)
        }
    }

    override suspend fun getCategoriesList(): List<CategoryModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<CategoryModel>()
        db.collection(CATEGORIES).get()
            .addOnSuccessListener { result ->
                result.forEach { doc ->
                    list.add(
                        CategoryModel(
                            doc.getString(CATEGORY),
                            doc.getString(TRANSACTION_TYPE)
                        )
                    )
                }
                continuation.resume(list)
            }
    }
}
