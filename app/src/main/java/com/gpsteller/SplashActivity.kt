package com.gpsteller

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gpsteller.activities.FirstActivity
import com.gpsteller.activities.HomeActivity
import com.gpsteller.activities.PhoneActivity
import com.orhanobut.hawk.Hawk
import java.lang.Exception

class SplashActivity : Activity() {

    internal lateinit var mSPF: SharedPreferences
    internal lateinit var mEDT: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSPF = getSharedPreferences("AppData",0)
        mEDT = mSPF.edit()

        val timerThread = object :Thread(){
            override fun run() {
                try {

                    Thread.sleep(3000)
                    if (Hawk.get(AppConstants.PHONE_NO,"")==""){
                        val i = Intent(applicationContext, PhoneActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    else{
                        val i = Intent(applicationContext,HomeActivity::class.java)
                        startActivity(i)
                        finish()

                    }
                }
                catch (i: Exception){
                    i.printStackTrace()
                }
            }
        }

        timerThread.start()

    }
}
