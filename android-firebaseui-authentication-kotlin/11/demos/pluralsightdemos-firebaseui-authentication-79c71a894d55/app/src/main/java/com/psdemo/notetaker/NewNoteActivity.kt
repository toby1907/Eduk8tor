package com.psdemo.notetaker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.psdemo.notetaker.MainActivity.Companion.USER_ID
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        signInMessage = "Sign-in to create a new note!"

        btnSave.setOnClickListener {
            val resultIntent = Intent()

            if (TextUtils.isEmpty(etTitle.text) || TextUtils.isEmpty(etBody.text)) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            } else {
                val title = etTitle.text.toString()
                val body = etBody.text.toString()

                resultIntent.putExtra(NEW_TITLE, title)
                resultIntent.putExtra(NEW_BODY, body)
                resultIntent.putExtra(USER_ID, userId)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }

    companion object {
        const val NEW_TITLE = "new_title"
        const val NEW_BODY = "new_body"
    }
}
