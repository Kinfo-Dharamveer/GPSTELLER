package com.gpsteller.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.AppConstants
import com.gpsteller.R
import com.orhanobut.hawk.Hawk

class PlacesActivity : Activity() {

    private var imgBack: ImageView?  = null
    private var txtAdd: CustomTextView?  = null
    private var homeLayout: RelativeLayout?  = null
    private var hideLayout: LinearLayout?  = null
    private var addPlaceLayout: LinearLayout?  = null
    private var btnAddPlace: Button?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        imgBack = findViewById(R.id.imgBack)
        txtAdd = findViewById(R.id.txtAdd)
        homeLayout = findViewById(R.id.homeLayout)
        hideLayout = findViewById(R.id.hideLayout)
        addPlaceLayout = findViewById(R.id.addPlaceLayout)
        btnAddPlace = findViewById(R.id.btnAddPlace)

        if (Hawk.get(AppConstants.PLACE_SAVED,"")!=null){

            if(Hawk.get(AppConstants.PLACE_SAVED,"").equals("place_saved")){
                addPlaceLayout!!.visibility = View.GONE
                hideLayout!!.visibility = View.VISIBLE
            }
            else{
                addPlaceLayout!!.visibility = View.VISIBLE
                hideLayout!!.visibility = View.GONE
            }
        }

        imgBack!!.setOnClickListener {
            finish()
        }

        txtAdd!!.setOnClickListener {
            startActivity(Intent(this,AddPlaceActivity::class.java))
        }

        btnAddPlace!!.setOnClickListener {
            startActivity(Intent(this,AddPlaceActivity::class.java))
        }

        homeLayout!!.setOnClickListener {
            startActivity(Intent(this,HomeAddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (Hawk.get(AppConstants.PLACE_SAVED,"")!=null){

            if(Hawk.get(AppConstants.PLACE_SAVED,"").equals("place_saved")){
                addPlaceLayout!!.visibility = View.GONE
                hideLayout!!.visibility = View.VISIBLE
            }
            else{
                addPlaceLayout!!.visibility = View.VISIBLE
                hideLayout!!.visibility = View.GONE
            }
        }
    }



}
