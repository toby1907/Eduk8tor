package com.psdemo.notetaker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        signInMessage = "Sign-in to see your settings"

        val authUI = AuthUI.getInstance()
        btnSignOut.setOnClickListener {
            authUI
                .signOut(this)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Log.e(TAG, "Sign-out failed", task.exception)
                        Toast.makeText(this, "Sign-out failed", Toast.LENGTH_LONG).show()
                    }

                }
        }

        btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this, R.style.DialogTheme)
                .setTitle("Delete Account")
                .setMessage("This is permanent, are you sure?")
                .setPositiveButton("Yes") { _, _ ->
                    authUI.delete(this)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Log.e(TAG, "Delete account failed", task.exception)
                                Toast.makeText(this, "Delete account failed", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        etName.setText(user?.displayName)
    }

    override fun onPause() {
        super.onPause()
        val profile =
            UserProfileChangeRequest.Builder()
                .setDisplayName(etName.text.toString())
                .build()

        if (user != null) {
            user!!.updateProfile(profile)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e(TAG, "Failed to update display name", task.exception)
                        Toast.makeText(this, "Name update failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    companion object {
        private val TAG = SettingsActivity::class.qualifiedName
    }
}
