package com.gpsteller.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import android.widget.ImageView
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.R
import android.graphics.drawable.Drawable
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.os.Environment
import java.io.File
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.path
import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import com.gpsteller.AppConstants
import com.orhanobut.hawk.Hawk
import java.io.FileInputStream
import java.io.FileNotFoundException
import android.widget.FrameLayout
import android.view.animation.AlphaAnimation
import java.util.concurrent.TimeUnit


class HomeAddActivity : AppCompatActivity() {


    private var imgBack_homeadd: ImageView? = null
    private var mapImage: ImageView? = null
    private var txtEdit: CustomTextView? = null
    private var txtInviteFamMem: Button? = null
    private var txtRemovePla: CustomTextView? = null
    private var view: View? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_add)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_acbar_homeadd)
        view = supportActionBar!!.customView

        findIds()

        clickListeners()

        val cw = ContextWrapper(applicationContext)

        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)

        loadImageFromStorage(directory.toString())

    }

    private fun loadImageFromStorage(path: String) {

        try {
            val f = File(path, "map.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            mapImage!!.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    private fun findIds() {
        imgBack_homeadd = view!!.findViewById(R.id.imgBack_homeadd)
        txtEdit = view!!.findViewById(R.id.txtEdit)
        mapImage = findViewById(R.id.mapImage)
        txtInviteFamMem = findViewById(R.id.txtInviteFamMem)
        txtRemovePla = findViewById(R.id.txtRemovePla)

    }

    private fun clickListeners() {

        imgBack_homeadd!!.setOnClickListener {
            finish()
        }

        txtEdit!!.setOnClickListener {

            val i = Intent(this, AddPlaceActivity::class.java)
            i.putExtra(AppConstants.EDIT_PLACE,"Edit Place")
            startActivity(i)

        }

        txtInviteFamMem!!.setOnClickListener {

            startActivity(Intent(this, SendInviteCodeActivity::class.java))
        }
        txtRemovePla!!.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to delete Home?")


            builder.setPositiveButton("YES") { dialog, which ->

                val i = Intent(applicationContext, PlacesActivity::class.java)
                Hawk.delete(AppConstants.PLACE_SAVED)
                startActivity(i)
                finish()

            }
            builder.setNegativeButton("NO") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }



    }





}
