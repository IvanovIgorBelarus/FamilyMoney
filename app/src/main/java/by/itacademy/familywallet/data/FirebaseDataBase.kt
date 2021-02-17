package by.itacademy.familywallet.data

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDataBase {
    val instance = FirebaseFirestore.getInstance()
}