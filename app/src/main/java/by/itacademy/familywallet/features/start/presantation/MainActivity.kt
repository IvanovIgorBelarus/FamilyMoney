package by.itacademy.familywallet.features.start.presantation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.itacademy.familywallet.R
import by.itacademy.familywallet.core.firebase.FirebaseDataBase
import by.itacademy.familywallet.core.firebase.FirebaseRepositoryImpl
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
    private val repo = FirebaseRepositoryImpl(FirebaseDataBase.instance)
    private val dialog by inject<Dialogs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginIn.setOnClickListener {
            signIn()
            binding.progressBar.visibility= View.VISIBLE
            binding.loginIn.visibility = View.INVISIBLE
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        setTheme()
        checkPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                dialog.createNegativeDialog(this, getString(R.string.alert_negative_auth_message2))
                Log.w(by.itacademy.familywallet.core.others.TAG, "Google signin failed", e)
                binding.progressBar.visibility= View.GONE
                binding.loginIn.visibility = View.VISIBLE
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
                    Log.w(by.itacademy.familywallet.core.others.TAG, "signInWithCredential:failure", task.exception)
                    dialog.createNegativeDialog(this, getString(R.string.alert_negative_auth_message))
                    binding.progressBar.visibility= View.INVISIBLE
                    binding.loginIn.visibility = View.VISIBLE
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

    private fun setTheme() {
        val isChecked = getSharedPreferences("1", Context.MODE_PRIVATE).getBoolean("1", true)
        if (!isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun checkPermissions() {
        val appPermission = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.INTERNET)
        appPermission.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED)
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) {
                }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}