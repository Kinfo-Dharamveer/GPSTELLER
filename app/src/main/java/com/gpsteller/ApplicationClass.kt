package com.gpsteller

import android.app.Application
import com.gpsteller.R
import com.orhanobut.hawk.Hawk
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class ApplicationClass: Application(){

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}