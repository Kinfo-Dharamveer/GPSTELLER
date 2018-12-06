package com.gpsteller.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gpsteller.R
import com.gpsteller.SharedPref
import de.hdodenhof.circleimageview.CircleImageView

class LocationSharingActivity : AppCompatActivity() {

    var imageProfile: CircleImageView? = null
    var sharedPreferences: SharedPref? = null
    var  imageFileHawk: String? = null
    var selectedImageHawk: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_sharing)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        imageProfile = findViewById(R.id.imageViewLoc)

        sharedPreferences  = SharedPref(applicationContext)

        if (sharedPreferences!!.getImageString()!=null){

            imageFileHawk = sharedPreferences!!.getImageString()

            selectedImageHawk = BitmapFactory.decodeFile(imageFileHawk)

            imageProfile!!.setImageBitmap(selectedImageHawk)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }

}
