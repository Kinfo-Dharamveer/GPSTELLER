package com.gpsteller.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gpsteller.R
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.text.Spanned
import android.text.TextPaint
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.style.ClickableSpan
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import com.drivingschool.android.customviews.CustomTextView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class DrivingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driving)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        val ss = SpannableString("Don't want GPSTeller to log your own drives? Toggle Drive Detection in com.gpsteller.Settings")


        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(applicationContext, DriveDetectionActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 70, 79, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = findViewById(R.id.textClick) as CustomTextView
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
        ss.setSpan( ForegroundColorSpan(Color.BLUE), 70, 79, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    }
}
