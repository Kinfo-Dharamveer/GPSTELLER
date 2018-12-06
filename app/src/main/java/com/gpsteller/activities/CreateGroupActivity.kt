package com.gpsteller.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.View
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R
import com.gpsteller.adapters.SlidingImageAdapter
import kotlinx.android.synthetic.main.activity_places.*
import java.util.*

class CreateGroupActivity : BaseActivity() {

    private var currentPage = 0
    private var mPager: ViewPager? = null
    private var NUM_PAGES = 0
    private val IMAGES = arrayOf<Int>(R.drawable.group_1, R.drawable.group_2, R.drawable.group_3, R.drawable.group_1)
    private val ImagesArray = ArrayList<Int>()
    private var txtAddGroup: CustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtAddGroup = findViewById(R.id.txtAddGroup)

        txtAddGroup!!.setOnClickListener {

            startActivity(Intent(this,AddCircleActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        initImages()




    }

    private fun initImages() {

        for (i in 0 until IMAGES.size)
            ImagesArray.add(IMAGES[i])

        mPager = findViewById(R.id.pager)
        mPager!!.setPageTransformer(true, FadePageTransformer())

        mPager!!.setAdapter(SlidingImageAdapter(this, ImagesArray))


        NUM_PAGES = IMAGES.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage === NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)

        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }


}

class FadePageTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.translationX = view.width * -position

        if (position <= -1.0f || position >= 1.0f) {
            view.alpha = 0.0f
        } else if (position == 0.0f) {
            view.alpha = 1.0f
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.alpha = 1.0f - Math.abs(position)
        }
    }

}