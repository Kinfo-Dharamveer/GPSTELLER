package com.gpsteller.activities

import `in`.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener
import `in`.mayanknagwanshi.imagepicker.imagePicker.ImagePicker
import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.*
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import com.drivingschool.android.customviews.CustomTextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.gpsteller.BaseActivity
import com.gpsteller.R

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.LocationRequest


import com.gpsteller.customviews.CustomTypefaceSpan
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.gpsteller.AppConstants
import com.gpsteller.SharedPref
import com.gpsteller.dialogs.InviteMemberDialog
import com.gpsteller.service.LocationService
import com.orhanobut.hawk.Hawk
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.text.DecimalFormat
import java.util.ArrayList


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private var mIsServiceStarted = false
    private var action: String? = null

    private var navigationView: NavigationView? = null
    var mDrawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var imgopenDrawer: ImageView? = null
    var txtBasic: CustomTextView? = null
    var txtUserName: CustomTextView? = null
    var txtSpeed: CustomTextView? = null
    var txtDistance: CustomTextView? = null
    var txtJoinGroup: CustomTextView? = null
    var txtCreateGroup: CustomTextView? = null
    var tvGName: CustomTextView? = null
    var linearInviteMem: LinearLayout? = null
    var imageProfileBott: CircleImageView? = null
    private var mMap: GoogleMap? = null

    var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var bottomSheet: View


    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null

    private val TAG = "gps"
    val REQUEST_CHECK_SETTINGS = 123
    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()
    private var notifID: Int = 0
    val EXTRA_NOTIFICATION_ID = "notification_id"
    val ACTION_FROM_NOTIFICATION = "isFromNotification"
    internal lateinit var mMessageReceiver: BroadcastReceiver
    private var latlngs: ArrayList<LatLng>? = null
    internal var customMarker: Marker? = null
    //val options = MarkerOptions()
    val builder = LatLngBounds.Builder()
    var selectedImage: Bitmap? = null
    var radiusM: Float = 80f
    var handler: Handler? = null
    var imagePicker = ImagePicker()
    var imageFilePath: String? = null
    var imageCamera: ImageView? = null
    var sharedPreferences: SharedPref? = null
    var mapprofileImg: ImageView? = null
    var driverImage: ImageView? = null
    var fab: FloatingActionButton? = null
    var toolbarTop: Toolbar? = null
    var toolbarTitle: CustomTextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findIds()

        //////////////////////////////////////////////Progress Dialog Start//////////////////////////////////
        progressDialog.show()
        handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                progressDialog!!.dismiss()
                handler!!.postDelayed(this, AppConstants.delayTime)
            }
        }
        handler!!.postDelayed(runnable, AppConstants.delayTime)
        //////////////////////////////////////////////Progress Dialog End//////////////////////////////////


        ///////////////////////////////////////////Bottom Sheet Start////////////////////////////////////////////
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        fab!!.setOnClickListener {
            bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        //Handling movement of bottom sheets from sliding
        bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    // fab!!.animate().scaleX(0f).scaleY(0f).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    // fab!!.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //fab!!.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }
        })
        //////////////////////////////////////////////Bottom Sheet End/////////////////////////////////////////////////


        displayLocationSettingsRequest(this)


        txtUserName!!.setText(Hawk.get(AppConstants.USER_NAME, "Name"))


        sharedPreferences = SharedPref(applicationContext)


        /////////////////////////////////////////Navigation Drawer Start/////////////////////////////////////

        val m = navigationView!!.getMenu()

        val header = navigationView!!.getHeaderView(0)
        txtJoinGroup = header.findViewById(R.id.txtJoinGroup)
        txtCreateGroup = header.findViewById(R.id.txtCreateGroup)
        tvGName = header.findViewById(R.id.tvGName)

        if (Hawk.get(AppConstants.GROUP_NAME, "") != "") {
            tvGName!!.setText(Hawk.get(AppConstants.GROUP_NAME, ""))
            toolbarTitle!!.setText(Hawk.get(AppConstants.GROUP_NAME, ""))
        }


        val element = m.findItem(R.id.txtBasic)

        for (i in 0 until m.size()) {
            val mi = m.getItem(i)

            //for applying a font to subMenu ...
            val subMenu = mi.getSubMenu()
            if (subMenu != null && subMenu!!.size() > 0) {
                for (j in 0 until subMenu!!.size()) {
                    val subMenuItem = subMenu!!.getItem(j)
                    applyFontToMenuItem(subMenuItem)
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi)
        }

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = object : ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.open_drawer, /* "open drawer" description for accessibility */
                R.string.close_drawer)    /* "close drawer" description for accessibility */ {
            override fun onDrawerClosed(drawerView: View) {
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView)

            }

            override fun onDrawerOpened(drawerView: View) {
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
            }
        }
        mDrawerLayout!!.setDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle!!.syncState()
        navigationView!!.setNavigationItemSelectedListener(this)

        /////////////////////////////////////////Navigation Drawer End/////////////////////////////////////


        val mapFragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        selectedImage = BitmapFactory.decodeFile(Hawk.get(AppConstants.IMAGE_PATH))

        imageProfileBott!!.setImageBitmap(selectedImage)

        // Initializing
        latlngs = ArrayList()

        startService()

        clickListeners()

    }

    private fun findIds() {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        imgopenDrawer = findViewById(R.id.imgopenDrawer)
        imageProfileBott = findViewById(R.id.imageProfileBott)
        linearInviteMem = findViewById(R.id.linearInviteMem)
        bottomSheet = findViewById(R.id.bottom_sheet)
        fab = findViewById(R.id.fab)
        txtUserName = findViewById(R.id.txtUserName)
        txtSpeed = findViewById(R.id.txtSpeed)
        txtDistance = findViewById(R.id.txtDistance)

        toolbarTop = findViewById(R.id.main_toolbar) as Toolbar
        // val txtInout = toolbarTop.findViewById(R.id.txtInout) as TextView
        toolbarTitle = toolbarTop!!.findViewById(R.id.toolbarText) as CustomTextView
        imageCamera = toolbarTop!!.findViewById(R.id.imageCamera) as ImageView
    }

    private fun clickListeners() {

        imgopenDrawer?.setOnClickListener {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
        }

        txtInout!!.setOnClickListener {
        }

        imageCamera!!.setOnClickListener {
            imagePicker.withActivity(this).chooseFromGallery(true).withCompression(true).start();

        }


        linearInviteMem!!.setOnClickListener {

            startActivity(Intent(this, InviteMemberDialog::class.java))
        }

        txtJoinGroup!!.setOnClickListener {
            startActivity(Intent(this, JoinGroupActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        }
        txtCreateGroup!!.setOnClickListener {
            startActivity(Intent(this, CreateGroupActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }
    }

    override fun onConnected(bundle: Bundle?) {
        createLocationRequest()
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
                    imageProfileBott!!.setImageBitmap(selectedImage)

                }
            })
            imageFilePath = imagePicker.getImageFilePath(data)
            if (imageFilePath != null) {
                saveImageFilePath(imageFilePath!!)
                selectedImage = BitmapFactory.decodeFile(imageFilePath)
                imageProfileBott!!.setImageBitmap(selectedImage)


            }

        }
    }

    private fun saveImageFilePath(imageFilePath: String) {
        Hawk.put(AppConstants.IMAGE_PATH, imageFilePath)
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onLocationChanged(p0: Location?) {}

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }
    }

    private fun displayLocationSettingsRequest(context: Context) {

        val googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> Log.i(TAG, "All location settings are satisfied.")
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")

                    try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                    } catch (e: Exception) {

                    }


                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.")
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {}

    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap


        if (customMarker != null) {
            customMarker!!.remove();
        }


        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.setMyLocationEnabled(false)
            }
        } else {
            buildGoogleApiClient()
            mMap!!.setMyLocationEnabled(false)
        }


        mMessageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val b = intent.getBundleExtra("Location")
                val lastKnownLoc = b.getParcelable<Location>("Location")
                val speed = intent.getDoubleExtra(AppConstants.SPEED, 25.0)
                val distance = intent.getDoubleExtra(AppConstants.DISTANCE, 20.0)
                if (lastKnownLoc != null) {

                    //    Toast.makeText(applicationContext, "Lat: =" + lastKnownLoc.latitude + " Longitude:=" + lastKnownLoc.longitude, Toast.LENGTH_SHORT).show();
                    //    Toast.makeText(applicationContext, "Speed: =" + speed + " Distance:=" + distance, Toast.LENGTH_SHORT).show();

                    if (speed > 0.0)
                        txtSpeed!!.setText(DecimalFormat("#.##").format(speed) + " km/hr")
                    else
                        txtSpeed!!.setText("---")
                    txtDistance!!.setText(DecimalFormat("#.###").format(distance) + " Km's.")

                    latitude = lastKnownLoc.latitude
                    longitude = lastKnownLoc.longitude

                    val latLng = LatLng(latitude, longitude)

                    latlngs!!.add(latLng)
                    latlngs!!.add(LatLng(30.715260, 76.707770))

                    Hawk.put(AppConstants.LATITUDE, latitude)
                    Hawk.put(AppConstants.LONGITUDE, longitude)



                    for (i in 0 until latlngs!!.size) {


                        if (i == 0)
                            markerCodeCustomer(latlngs!!.get(i).latitude, latlngs!!.get(i).longitude, "Customer", "Dharam")

                        else
                            createMarkerDriver(latlngs!!.get(i).latitude, latlngs!!.get(i).longitude, "Driver", "Any")

                    }
                    val bounds = builder.build()
                    val padding = 20 // offset from edges of the map in pixels
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)

                    mMap!!.moveCamera(cu)

                    /*   val mapView = supportFragmentManager.findFragmentById(R.id.map)?.getView()
                       if (mapView!!.getViewTreeObserver().isAlive) {
                           mapView!!.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                               @SuppressLint("NewApi")
                               override// We check which build version we are using.
                               fun onGlobalLayout() {
                                   val bounds = LatLngBounds.Builder().include(latLng).build()
                                   if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                       mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                                   } else {
                                       mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                                   }
                                   mMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
                               }
                           })
                       }*/
                }

            }

        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("GPSLocationUpdates"))

    }

    private fun createMarkerDriver(latitude: Double, longitude: Double, title: String, snippet: String) {

        val latLng = LatLng(latitude, longitude)

        var marker = (getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater).inflate(R.layout.marker_layout_driver, null)
        driverImage = marker.findViewById(R.id.driverImage) as CircleImageView
        // var homeImg = marker.findViewById(R.id.homeImg) as CircleImageView

        //selectedImage = BitmapFactory.decodeFile(imageFilePath)
        // driverImage!!.setImageBitmap(selectedImage)

        driverImage!!.setBackgroundResource(R.drawable.drivermarker)

        customMarker = mMap!!.addMarker(MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(marker))))

        customMarker!!.showInfoWindow()

        //   mMap!!.addMarker(options).showInfoWindow()
        builder.include(customMarker!!.position)


    }

    private fun markerCodeCustomer(latitude: Double, longitude: Double, title: String, snippet: String) {

        val latLng = LatLng(latitude, longitude)
        // draw circle
        /*  val d = 700 // diameter
          val bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888)
          val c = Canvas(bm)
          val p = Paint()
          p.setColor(resources.getColor(R.color.green))
          c.drawCircle((d / 2).toFloat(), (d / 2).toFloat(), (d / 2).toFloat(), p)

          // generate BitmapDescriptor from circle Bitmap
          var bmD = BitmapDescriptorFactory.fromBitmap(bm)*/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            mMap!!.setMyLocationEnabled(true);

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

        var marker = (getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater).inflate(R.layout.marker_layout, null)
        mapprofileImg = marker.findViewById(R.id.profileImg) as CircleImageView
        // var homeImg = marker.findViewById(R.id.homeImg) as CircleImageView

        //selectedImage = BitmapFactory.decodeFile(imageFilePath)
        mapprofileImg!!.setImageBitmap(selectedImage)

        customMarker = mMap!!.addMarker(MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(marker))))


        customMarker!!.showInfoWindow()
        // mMap!!.addMarker(options).showInfoWindow()
        builder.include(customMarker!!.position)

        /*  // mapView is the GoogleMap
          mMap!!.addGroundOverlay(GroundOverlayOptions()
                  .position(latLng, radiusM * 2, radiusM * 2)
                  .transparency(0.7f))*/

    }

    // Convert a view to bitmap
    fun createDrawableFromView(view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    private fun createLocationRequest() {
        //Creating location request object
        mLocationRequest = LocationRequest()
        mLocationRequest!!.setInterval(AppConstants.UPDATE_INTERVAL)
        mLocationRequest!!.setFastestInterval(AppConstants.FATEST_INTERVAL)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    @Synchronized
    private fun buildGoogleApiClient() {

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mGoogleApiClient!!.connect()
    }

    fun startService() {
        if (!mIsServiceStarted) {
            mIsServiceStarted = true
            // OnGoingLocationNotification(this);
            startService(Intent(this, LocationService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (intent.action != null) {
            action = intent.action
            notifID = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
            if (action.equals(ACTION_FROM_NOTIFICATION, ignoreCase = true)) {
                mIsServiceStarted = true

            }
        }
    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        val font = Typeface.createFromAsset(assets, "fonts/Montserrat-Regular.ttf")
        val mNewTitle = SpannableString(mi.title)
        mNewTitle.setSpan(CustomTypefaceSpan("", font), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        mi.title = mNewTitle
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    override fun onBackPressed() {
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {

            R.id.nav_alerts -> startActivity(Intent(this, AlertsTypeActivity::class.java))
            R.id.nav_messages -> startActivity(Intent(this, MessagesActivity::class.java))
            R.id.nav_help -> startActivity(Intent(this, HelpActivity::class.java))
            R.id.nav_premium -> startActivity(Intent(this, PremiumActivity::class.java))
            R.id.nav_locationsharing -> startActivity(Intent(this, LocationSharingActivity::class.java))
            R.id.nav_places -> startActivity(Intent(this, PlacesActivity::class.java))
            R.id.nav_driving -> startActivity(Intent(this, DrivingActivity::class.java))
            R.id.nav_reports -> startActivity(Intent(this, ReportsButtonActivity::class.java))


        }
        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true

    }

}
