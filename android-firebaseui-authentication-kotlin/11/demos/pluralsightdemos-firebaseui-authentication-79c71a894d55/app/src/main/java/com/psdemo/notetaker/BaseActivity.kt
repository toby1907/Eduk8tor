package com.psdemo.notetaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected var userId = "-1"
        private set
    protected var user: FirebaseUser? = null
        private set
    protected var signInMessage = "This action requires signing-in"

    override fun onResume() {
        super.onResume()
        user = FirebaseAuth.getInstance().currentUser
        if (user == null || user?.isAnonymous != false || user?.providers?.size ?:0 == 0) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.SIGNIN_MESSAGE,signInMessage)
            startActivityForResult(intent, ATTEMPT_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ATTEMPT_SIGN_IN && resultCode == Activity.RESULT_CANCELED) {
            finish()
        } else {
            if (data != null && data.hasExtra(MainActivity.USER_ID)) {
                userId = data.getStringExtra(MainActivity.USER_ID)
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object{
        const val ATTEMPT_SIGN_IN = 10
    }
}