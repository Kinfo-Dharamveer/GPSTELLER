package com.gpsteller.dialogs

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.R
import android.support.v4.content.ContextCompat.startActivity



class InviteMemberDialog : Activity(){

    private var button_close: ImageView? = null
    private var btnSendCode: CustomTextView? = null
    private var txtRandomCode: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_member_dialog)
        this.setFinishOnTouchOutside(false)


        button_close  = findViewById(R.id.button_close)
        btnSendCode  = findViewById(R.id.tvSendCode)
        txtRandomCode  = findViewById(R.id.tvRandomCode)

        button_close!!.setOnClickListener {
            finish()
        }
        btnSendCode!!.setOnClickListener {

            sendCode()
        }

    }

    private fun sendCode() {


        try {
            val sendIntent = Intent()

            var code = txtRandomCode!!.text.toString()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out invite Code: = $code")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        } catch (e: Exception) {
        }

    }
}