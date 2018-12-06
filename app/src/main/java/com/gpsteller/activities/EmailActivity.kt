package com.gpsteller.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R
import android.util.Patterns
import android.text.TextUtils
import android.widget.Toast


class EmailActivity : BaseActivity() {


    var edEmail: EditText? = null
    var txtNextEmail: CustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        edEmail = findViewById(R.id.edEmail)
        txtNextEmail = findViewById(R.id.txtNextEmail)

        txtNextEmail!!.setOnClickListener {

            if (edEmail!!.text.toString()==""){
                edEmail!!.setError("Enter email first")
            }
            else if (isValidEmail(edEmail!!.text.toString())){
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            else {
                Toast.makeText(this,"Not a valid emial",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
