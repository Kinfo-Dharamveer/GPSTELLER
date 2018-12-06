package com.gpsteller.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.EditText
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import java.nio.file.Files.size


class SignInActivity : BaseActivity() {

    var edPsw: EditText? = null
    var txtNext: CustomTextView? = null
    val REQUEST_LOCATION_REQUEST_CODE = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        edPsw = findViewById(R.id.edPsw)
        txtNext = findViewById(R.id.txtNext)

        txtNext!!.setOnClickListener {

            if (edPsw!!.text.toString() == "") {
                edPsw!!.setError("Enter password first")
            } else {
                startActivity(Intent(this, EmailActivity::class.java))
            }
        }

        requestAppPermissions()




    }

    private fun requestAppPermissions() {


        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        if (hasFineLocation() && hasCoarsLoc() && hasCamera()) {
            return
        }

        ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA), REQUEST_LOCATION_REQUEST_CODE); // your request code

    }

    private fun hasCamera(): Boolean {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)

    }

    private fun hasFineLocation(): Boolean {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun hasCoarsLoc(): Boolean {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)

    }



}
