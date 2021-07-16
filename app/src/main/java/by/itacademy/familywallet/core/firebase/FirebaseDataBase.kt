package by.itacademy.familywallet.core.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDataBase {
    val instance = FirebaseFirestore.getInstance()
}