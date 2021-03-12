package by.itacademy.familywallet.utils

import com.google.firebase.auth.FirebaseAuth

class UserUtils {
    companion object{
        fun getUsersUid():String?= FirebaseAuth.getInstance().currentUser?.uid
    }
}