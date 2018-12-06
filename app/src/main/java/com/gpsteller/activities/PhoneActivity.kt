package com.gpsteller.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.AppConstants
import com.gpsteller.BaseActivity
import com.gpsteller.R
import com.orhanobut.hawk.Hawk
import com.rilixtech.CountryCodePicker

class PhoneActivity : BaseActivity() {

    private var edPhoneNo: EditText? = null
    private var txtNext: CustomTextView? = null
    private var ccp: CountryCodePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        edPhoneNo = findViewById(R.id.edPhoneNo)
        txtNext = findViewById(R.id.txtNext)
        ccp = findViewById(R.id.ccp)

        txtNext!!.setOnClickListener {

            if (edPhoneNo!!.text.toString()==""){
                edPhoneNo!!.setError("Enter your number")

            }
            else
            {
                progressDialog!!.show()
                val intent = Intent(applicationContext,SignInActivity::class.java)
                Hawk.put(AppConstants.PHONE_NO,edPhoneNo!!.text.toString())
                startActivity(intent)
                finish()
            }



        }

    }

    override fun onResume() {
        super.onResume()
        if (progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog!!.dismiss()
    }
}
