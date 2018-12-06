package com.gpsteller.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.gpsteller.R
import com.skyfishjy.library.RippleBackground

class HelpActivity : AppCompatActivity() {

    private var rippleBackground: RippleBackground? = null
    private var centerImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        rippleBackground = findViewById(R.id.rippleBg)
        centerImage = findViewById(R.id.centerImage)

        rippleBackground?.startRippleAnimation();



    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }


}
