package com.gpsteller.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.gpsteller.R
import android.widget.EditText
import com.drivingschool.android.customviews.CustomTextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.gpsteller.AppConstants
import android.location.Geocoder
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import java.io.IOException
import com.orhanobut.hawk.Hawk
import android.graphics.Bitmap
import android.os.Environment
import java.io.FileOutputStream
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.Intent


class AddPlaceActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var txtSave: CustomTextView? = null
    private var toolbarTitle: CustomTextView? = null
    private var imgBackAP: ImageView? = null
    private var txtArea: CustomTextView? = null
    private var seekBar: SeekBar? = null
    private var latitude: Double? = null
    private var longitute: Double? = null
    var edPlaceName: EditText? = null
    var edCenterLoc: EditText? = null
    private var onCameraIdleListener: GoogleMap.OnCameraIdleListener? = null
    private var bitmap: Bitmap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar_addplace)
        val view = supportActionBar!!.customView

        txtSave = view.findViewById(R.id.txtSave)
        imgBackAP = view.findViewById(R.id.imgBackAP)
        toolbarTitle = view.findViewById(R.id.toolbarTitle)

        edPlaceName = findViewById(R.id.edPlaceName)
        edCenterLoc = findViewById(R.id.edCenterLoc)

        txtArea = findViewById(R.id.txtArea)
        seekBar = findViewById(R.id.seekBar)


        val extras = intent.extras
        val edit_place: String?

        if (extras != null) {
            edit_place = extras.getString(AppConstants.EDIT_PLACE)
            toolbarTitle!!.setText(edit_place)
        }


        val mapFragment = getSupportFragmentManager().findFragmentById(R.id.mapSec) as SupportMapFragment
        mapFragment.getMapAsync(this)

        configureCameraIdle()

        seekBar!!.max = 3000
        seekBar!!.progress = 200

        imgBackAP!!.setOnClickListener {
            finish()
        }

        txtSave!!.setOnClickListener {

            if (edPlaceName!!.text.toString()==""){
                Toast.makeText(this,"Enter the place name",Toast.LENGTH_SHORT).show()
            }
            else{

                val callback = object : GoogleMap.SnapshotReadyCallback {
                    override fun onSnapshotReady(snapshot: Bitmap) {
                        bitmap = snapshot

                        try {
                          saveToInternalStorage(bitmap!!)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
                mMap!!.snapshot(callback)

            }
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap): String {

        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "map.jpg")

        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            Toast.makeText(this,"Place saved successfully",Toast.LENGTH_SHORT).show()
            Hawk.put(AppConstants.PLACE_SAVED,"place_saved")
            val i  = Intent(this,HomeAddActivity::class.java)
            startActivity(i)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return directory.absolutePath
    }

    private fun configureCameraIdle() {

        onCameraIdleListener = GoogleMap.OnCameraIdleListener {
            val latLng = mMap!!.getCameraPosition().target
            val geocoder = Geocoder(this)

            try {
                val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addressList != null && addressList.size > 0) {
                    val locality = addressList[0].getAddressLine(0)
                    val country = addressList[0].countryName
                    if (!locality.isEmpty() && !country.isEmpty())
                        edCenterLoc!!.setText("$locality  $country")
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap

        mMap!!.setOnCameraIdleListener(onCameraIdleListener)
        mMap!!.setMyLocationEnabled(true)

        mMap!!.getUiSettings().setZoomGesturesEnabled(true);
        mMap!!.getUiSettings().setRotateGesturesEnabled(true);

        latitude = Hawk.get(AppConstants.LATITUDE)
        longitute = Hawk.get(AppConstants.LONGITUDE)

        val latLong = LatLng(latitude!!, longitute!!)


        mMap!!.clear()

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {


               /* if(progress <=SOME_LIMIT!!){

                }*/

                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, seekBar!!.progress.toFloat()))
                txtArea!!.setText(seekBar!!.progress.toString()+ "m area")

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 16f))


    }


}
