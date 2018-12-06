package com.gpsteller.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.R

class MessagesActivity : AppCompatActivity() {

    private var txtAddMember: CustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtAddMember = findViewById(R.id.txtAddMember)

        txtAddMember!!.setOnClickListener {
            startActivity(Intent(this,SendInviteCodeActivity::class.java))

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein,R.anim.fadeout)
        return true
    }
}
