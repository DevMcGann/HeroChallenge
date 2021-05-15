package com.intermedia.challenge.ui.login


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.intermedia.challenge.R
import com.intermedia.challenge.databinding.ActivityLoginBinding
import com.intermedia.challenge.ui.main.MainScreenActivity
import kotlinx.android.synthetic.main.custom_login_error_message.*


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val firebaseAuth by lazy {FirebaseAuth.getInstance()}
    private lateinit var callbackManager: CallbackManager
    private lateinit var buttonFacebookLogin: LoginButton
    val TAG = "LoginActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        callbackManager = CallbackManager.Factory.create()
        buttonFacebookLogin = binding.loginButton
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                //TODO: custom message
            }
        })

        binding.tEmailfield.addTextChangedListener(loginTextWatcher);
        binding.tPasswordfield.addTextChangedListener(loginTextWatcher);

        doLogin()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameInput: String = binding.tEmailfield.getText().toString().trim()
            val passwordInput: String = binding.tPasswordfield.getText().toString().trim()
            binding.btnlogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d(TAG, user.toString())
        if (user !== null){
            navigateToMainScreen()
        }

    }

    private fun doLogin(){
        binding.btnlogin.setOnClickListener {
            val email = binding.tEmailfield.text.toString().trim()
            val password = binding.tPasswordfield.text.toString().trim()
            signIn(email, password)
        }
    }


    private fun signIn(email: String, password: String){
        binding.progressBarLogin.visibility = View.VISIBLE
         FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = it.user
                binding.progressBarLogin.visibility = View.GONE
                navigateToMainScreen()
            }
             .addOnFailureListener {
                 binding.progressBarLogin.visibility = View.GONE
                 binding.emailField.isErrorEnabled  = true
                 binding.emailField.error = " Error "
                 binding.passField.isErrorEnabled  = true
                 binding.passField.error = " Error "
                 showToast()
             }
    }

    private fun showToast(){
        Log.d("LoginActivity", "anda esto?")
        val customToastLayout : View = layoutInflater.inflate(R.layout.custom_login_error_message,custom_error_message)
        Toast(this).apply {
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.BOTTOM, 0,10)
            view = customToastLayout
        }.show()
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, MainScreenActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }



}