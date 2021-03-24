package by.itacademy.familywallet.utils

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth

object UserUtils {
    private val user = FirebaseAuth.getInstance().currentUser
    fun getUsersUid(): String? = user?.uid
    fun getUserName(): String? = user?.displayName
    fun getUserPhoto(): Uri? = user?.photoUrl
}