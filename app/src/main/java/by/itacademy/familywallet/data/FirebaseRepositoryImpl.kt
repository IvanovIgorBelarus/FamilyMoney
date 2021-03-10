package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.UIModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(private val db: FirebaseFirestore) : DataRepository {
    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel) {
        db.collection(TRANSACTIONS).add(
            mapOf(
                UID to transactionModel.uid,
                TRANSACTION_TYPE to transactionModel.type,
                CATEGORY to transactionModel.category,
                MONEY_TYPE to transactionModel.moneyType,
                VALUE to transactionModel.value,
                DATE to transactionModel.date
            )
        )
    }

    override suspend fun addNewCategory(category: String, type: String) {
        db.collection(CATEGORIES).add(
            mapOf(
                CATEGORY to category,
                TRANSACTION_TYPE to type
            )
        )
    }

    override suspend fun getTransactionsList(): List<UIModel.TransactionModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<UIModel.TransactionModel>()
        db.collection(TRANSACTIONS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                list.add(
                    UIModel.TransactionModel(
                        uid = doc.getString(UID),
                        type = doc.getString(TRANSACTION_TYPE),
                        category = doc.getString(CATEGORY),
                        moneyType = doc.getString(MONEY_TYPE),
                        value = doc.getDouble(VALUE),
                        date = doc.getLong(DATE)
                    )
                )
            }
            continuation.resume(list)
        }
    }

    override suspend fun getCategoriesList(): List<UIModel.CategoryModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<UIModel.CategoryModel>()
        db.collection(CATEGORIES).get()
            .addOnSuccessListener { result ->
                result.forEach { doc ->
                    list.add(
                        UIModel.CategoryModel(
                            doc.getString(CATEGORY),
                            doc.getString(TRANSACTION_TYPE)
                        )
                    )
                }
                continuation.resume(list)
            }
    }
}
