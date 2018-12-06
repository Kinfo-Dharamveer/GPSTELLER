package com.gpsteller.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R


class FirstActivity : BaseActivity() {

    var txtGetStarted: CustomTextView? = null
    var textLoginIn: CustomTextView? = null

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)


        txtGetStarted = findViewById(R.id.txtGetStarted)
        textLoginIn = findViewById(R.id.textLoginIn)


        txtGetStarted!!.setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }
        textLoginIn!!.setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verifyPermissions()

        }

    }


    private fun verifyPermissions() {
        Log.d("Profile", "verifyPermissions: asking user for permissions")
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[2]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                    permissions[3]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.applicationContext,
                permissions[4]) == PackageManager.PERMISSION_GRANTED)
        {
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CODE ->{
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "permission was granted, :)",
                            Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this,
                            "permission denied, ...:(",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
