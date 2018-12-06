package com.gpsteller.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R
import `in`.mayanknagwanshi.imagepicker.imagePicker.ImagePicker
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import `in`.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener
import android.Manifest
import android.app.Activity
import java.nio.file.Files.size
import android.support.v4.app.ActivityCompat
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.support.annotation.NonNull
import android.view.View
import com.gpsteller.AppConstants
import com.gpsteller.SharedPref
import com.orhanobut.hawk.Hawk
import de.hdodenhof.circleimageview.CircleImageView


class ProfileActivity : BaseActivity() {

    var edName: EditText? = null
    var txtNextProfile: CustomTextView? = null

    var imgPlus: ImageView? = null
    var imageProfile: CircleImageView? = null
    var framePickImage: FrameLayout? = null
    var imagePicker = ImagePicker()
    var imageFilePath: String? = null
    var  imageFileHawk: String? = null
    var selectedImage: Bitmap? = null
    var selectedImageHawk: Bitmap? = null
    var sharedPreferences: SharedPref? = null
    val REQUEST_WRITE_STORAGE_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        edName = findViewById(R.id.edName)
        txtNextProfile = findViewById(R.id.txtNextProfile)

        imageProfile = findViewById(R.id.imageProfile)
        imgPlus = findViewById(R.id.imgPlus)
        framePickImage = findViewById(R.id.framePickImage)

        requestAppPermissions()


        txtNextProfile!!.setOnClickListener {

            if (edName!!.text.toString()==""){
                edName!!.setError("Enter your name")
            }
            else {
                Hawk.put(AppConstants.USER_NAME, edName!!.text.toString())
                startActivity(Intent(this, GroupcodeActivity::class.java))
            }
        }
        sharedPreferences  = SharedPref(applicationContext)

        if (sharedPreferences!!.getImageString()!=null){

            imageFileHawk = sharedPreferences!!.getImageString()

            selectedImageHawk = BitmapFactory.decodeFile(imageFileHawk)

            imageProfile!!.setImageBitmap(selectedImageHawk)

        }

        framePickImage!!.setOnClickListener {
            imagePicker.withActivity(this).chooseFromGallery(true).withCompression(true).start();


        }

    }

    private fun requestAppPermissions() {

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return
        }

        ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code

    }

    private fun hasWritePermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);

    }

    private fun hasReadPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            imagePicker.addOnCompressListener(object : ImageCompressionListener {
                override fun onStart() {}
                override fun onCompressed(filePath: String) {
                    imageFilePath = filePath
                    selectedImage = BitmapFactory.decodeFile(filePath)
                    saveImageFilePath(imageFilePath!!)
                    sharedPreferences!!.setImageString(imageFilePath)
                    imageProfile!!.setImageBitmap(selectedImage)
                    imgPlus!!.visibility = View.GONE
                }
            })
            imageFilePath = imagePicker.getImageFilePath(data)
            if (imageFilePath != null) {
                saveImageFilePath(imageFilePath!!)
                selectedImage = BitmapFactory.decodeFile(imageFilePath)
                imageProfile!!.setImageBitmap(selectedImage)
                imgPlus!!.visibility = View.GONE

            }

        }
    }

    private fun saveImageFilePath(imageFilePath: String) {
        Hawk.put(AppConstants.IMAGE_PATH,imageFilePath)
    }
}
