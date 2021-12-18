package org.wit.cryptocurrency.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.wit.cryptocurrency.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.wit.cryptocurrency.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        checkUser()

        binding.continuetolist.setOnClickListener {
            Firebase.auth.signOut()
            val launcherIntent = Intent(this, CryptocurrencyListActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        binding.signout.setOnClickListener {
            signOut()
            finish()
        }

        registerRefreshCallback()

    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            val email = firebaseUser.email
            binding.email.text = email
        }
    }

    private fun signOut() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Firebase sign out
        auth.signOut()
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
            OnCompleteListener<Void?> {
                val launcherIntent = Intent(this, LoginActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            })
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}