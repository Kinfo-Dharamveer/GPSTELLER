package com.gpsteller.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.AppConstants
import com.gpsteller.R
import com.orhanobut.hawk.Hawk
import de.hdodenhof.circleimageview.CircleImageView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DriveDetectionActivity : AppCompatActivity() {

    private var txtUserName: CustomTextView? = null
    private var imageUser: CircleImageView? = null
    var selectedImage: Bitmap?  = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drive_detection)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        txtUserName = findViewById(R.id.txtUserName)
        imageUser = findViewById(R.id.imageUser)

        selectedImage = BitmapFactory.decodeFile(Hawk.get(AppConstants.IMAGE_PATH))
        imageUser!!.setImageBitmap(selectedImage)

        txtUserName!!.setText(Hawk.get(AppConstants.USER_NAME,"Name"))

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
