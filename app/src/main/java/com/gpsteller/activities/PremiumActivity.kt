package com.gpsteller.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.widget.Button
import com.gpsteller.R
import com.gpsteller.adapters.MyPagerAdapter
import me.relex.circleindicator.CircleIndicator

class PremiumActivity : FragmentActivity() {

    var viewPager: ViewPager? = null
    var btnCross: Button? = null
    var adapterViewPager: MyPagerAdapter? = null
    var indicator: CircleIndicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        viewPager = findViewById(R.id.viewPager)
        btnCross = findViewById(R.id.btnCross)
        indicator = findViewById(R.id.indicator)

        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = adapterViewPager

        indicator!!.setViewPager(viewPager)

        btnCross!!.setOnClickListener {
            finish()
        }

    }

}
