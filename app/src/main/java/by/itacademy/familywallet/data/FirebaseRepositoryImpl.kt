package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.CategoryModel
import by.itacademy.familywallet.model.TransactionModel
import com.google.android.gms.common.util.CollectionUtils.mapOf
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(private val db: FirebaseFirestore) : DataRepository {
    override fun doTransaction(transactionType: String?, transactionModel: TransactionModel) {
        db.collection(transactionType!!).add(
            mapOf(
                "date" to transactionModel.date,
                "value" to transactionModel.value,
                "uid" to transactionModel.uid
            )
        )
    }

    override suspend fun getCategories(): List<CategoryModel> {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<CategoryModel>()
            db.collection(CATEGORIES).get()
                .addOnSuccessListener { result ->
                    result.forEach { doc ->
                        list.add(CategoryModel(doc.getString("category"), doc.getString("type")))
                    }
                    continuation.resume(list)
                }
        }
    }
}
