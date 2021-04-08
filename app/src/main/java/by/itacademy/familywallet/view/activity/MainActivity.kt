package by.itacademy.familywallet.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.itacademy.familywallet.R
import by.itacademy.familywallet.data.DataRepository
import by.itacademy.familywallet.databinding.ActivityMainBinding
import by.itacademy.familywallet.model.UIModel
import by.itacademy.familywallet.utils.Dialogs
import by.itacademy.familywallet.utils.UserUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val repo: DataRepository by inject()
    private val dialog by inject<Dialogs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginIn.setOnClickListener {
            signIn()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        setTheme()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(by.itacademy.familywallet.data.TAG, "Google signin failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // val user = auth.currentUser
                    addUserInCloud()
                    startActivity(Intent(this, FragmentsActivity::class.java))
                    finish()
                    //updateUI(user)
                } else {
                    Log.w(by.itacademy.familywallet.data.TAG, "signInWithCredential:failure", task.exception)
                    dialog.createNegativeDialog(this, getString(R.string.alert_negative_auth_message))
                    //updateUI(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun addUserInCloud() {
        CoroutineScope(Dispatchers.IO).launch {
            val isPartner = repo.getPartner().uid.isNullOrEmpty()
            if (isPartner) {
                repo.addPartner(UIModel.AccountModel(uid = UserUtils.getUsersUid()))
            }
        }
    }

    private fun setTheme(){
        val isChecked=getSharedPreferences("1", Context.MODE_PRIVATE).getBoolean("1",true)
        if (!isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}