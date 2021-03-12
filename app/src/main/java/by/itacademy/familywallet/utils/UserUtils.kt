package by.itacademy.familywallet.utils

import com.google.firebase.auth.FirebaseAuth

object UserUtils {
    fun getUsersUid(): String? = FirebaseAuth.getInstance().currentUser?.uid
}