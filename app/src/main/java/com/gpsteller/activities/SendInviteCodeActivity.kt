package com.gpsteller.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.R
import java.util.*
import android.content.Intent




class SendInviteCodeActivity : AppCompatActivity() {


    private var txtRandomCode: CustomTextView? = null
    private var txtSendCode: CustomTextView? = null
    private var txtDone: CustomTextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_invite_code)

        txtRandomCode = findViewById(R.id.txtRandomCode)
        txtSendCode = findViewById(R.id.txtSendCode)
        txtDone = findViewById(R.id.txtDone)

        val chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray()
        val sb1 = StringBuilder()
        val random1 = Random()
        for (i in 0..5) {
            val c1 = chars1[random1.nextInt(chars1.size)]
            sb1.append(c1)
        }
        val random_string = sb1.toString()
        txtRandomCode!!.setText(random_string)


        txtDone!!.setOnClickListener {
            finish()
        }

        txtSendCode!!.setOnClickListener {
            shareTextUrl(txtRandomCode!!.getText().toString())

        }

    }

    private fun shareTextUrl(text: CharSequence) {
        val share = Intent(android.content.Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Invite Code")
        share.putExtra(Intent.EXTRA_TEXT,text )

        startActivity(Intent.createChooser(share, "Share link!"))
    }

}
