package by.itacademy.familywallet.data

import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.UserUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(private val db: FirebaseFirestore) : DataRepository {

    override suspend fun addPartner(accountModel: UIModel.AccountModel) {
        db.collection(USERS).add(
            mapOf(
                UID to accountModel.uid,
                PARTNER_UID to accountModel.partnerUid
            )
        )
    }

    override suspend fun doTransaction(transactionModel: UIModel.TransactionModel) {
        db.collection(TRANSACTIONS).add(
            mapOf(
                UID to transactionModel.uid,
                TRANSACTION_TYPE to transactionModel.type,
                CATEGORY to transactionModel.category,
                CURRENCY to transactionModel.currency,
                MONEY_TYPE to transactionModel.moneyType,
                VALUE to transactionModel.value,
                DATE to transactionModel.date
            )
        )
    }

    override suspend fun addNewCategory(categoryItem: UIModel.CategoryModel) {
        db.collection(CATEGORIES).add(
            mapOf(
                UID to categoryItem.uid,
                CATEGORY to categoryItem.category,
                TRANSACTION_TYPE to categoryItem.type
            )
        )
    }

    override suspend fun getPartner(): UIModel.AccountModel = suspendCoroutine { continuation ->
        var partner = UIModel.AccountModel()
        db.collection(USERS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                if (doc.getString(UID) == UserUtils.getUsersUid()) {
                    partner.id = doc.id
                    partner.uid = UserUtils.getUsersUid()
                    partner.partnerUid = doc.getString(PARTNER_UID)
                }
            }
            continuation.resume(partner)
        }
    }

    override suspend fun getTransactionsList(): List<UIModel.TransactionModel> = suspendCoroutine { continuation ->
        val list = mutableListOf<UIModel.TransactionModel>()
        db.collection(TRANSACTIONS).get().addOnSuccessListener { result ->
            result.forEach { doc ->
                list.add(
                    UIModel.TransactionModel(
                        id = doc.id,
                        uid = doc.getString(UID),
                        type = doc.getString(TRANSACTION_TYPE),
                        category = doc.getString(CATEGORY),
                        currency = doc.getString(CURRENCY),
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
                            id = doc.id,
                            uid = doc.getString(UID),
                            category = doc.getString(CATEGORY),
                            type = doc.getString(TRANSACTION_TYPE)
                        )
                    )
                }
                continuation.resume(list)
            }
    }

    override suspend fun deleteItem(item: Any?) {
        when (item) {
            is UIModel.CategoryModel -> db.collection(CATEGORIES).document("${item.id}").delete()
            is UIModel.AccountModel -> db.collection(USERS).document("${item.id}").delete()
            is UIModel.TransactionModel -> db.collection(TRANSACTIONS).document("${item.id}").delete()
        }
    }
}
