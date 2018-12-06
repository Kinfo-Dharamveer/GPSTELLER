package com.gpsteller.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.gpsteller.AppConstants
import com.gpsteller.models.LocationData
import java.text.DateFormat
import java.util.*


class LocationService: Service(),  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    protected val TAG = "LocationUpdateService"
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 500000

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    // Keys for storing activity state in the Bundle.
    protected val REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key"
    protected val LOCATION_KEY = "location-key"
    protected val LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key"
    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    var mRequestingLocationUpdates: Boolean? = null
    /**
     * Time when the location was updated represented as a String.
     */
    protected lateinit var mLastUpdateTime: String
    /**
     * Provides the entry point to Google Play services.
     */
    protected lateinit var mGoogleApiClient: GoogleApiClient

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected lateinit var mLocationRequest: LocationRequest

    /**
     * Represents a geographical location.
     */
    protected lateinit var mCurrentLocation: Location
    protected  var lStart: Location? =null
    protected  var lEnd: Location? = null
    var distance1 = 0.0
    var speed: Double = 0.toDouble()

    var isEnded = false
    private var mLocationData: ArrayList<LocationData>? = null

    override fun onCreate() {
        super.onCreate()
        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("LOC", "Service init...")
        isEnded = false
        mRequestingLocationUpdates = false
        mLastUpdateTime = ""
        buildGoogleApiClient()
        if (mGoogleApiClient.isConnected && mRequestingLocationUpdates as Boolean) {
            startLocationUpdates()
        }
        return Service.START_REDELIVER_INTENT
    }

    protected fun startLocationUpdates() {
        if (!mRequestingLocationUpdates!!) {
            mRequestingLocationUpdates = true


            if (checkPermission(applicationContext))
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)


            Log.i(TAG, " startLocationUpdates===")
            isEnded = true

        }
    }

    fun checkPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient===")
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        createLocationRequest()
    }

    protected fun createLocationRequest() {
        mGoogleApiClient.connect()
        mLocationRequest = LocationRequest()

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS

        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()

    }

    override fun onConnectionSuspended(p0: Int) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended==")
        mGoogleApiClient.connect()    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | com.gpsteller.Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {
        mCurrentLocation = location!!
        mLastUpdateTime = DateFormat.getTimeInstance().format(Date())

        if (lStart == null) {
            lStart = mCurrentLocation;
            lEnd = mCurrentLocation;
        } else
            lEnd = mCurrentLocation;

        updateUI()

        //calculating the speed with getSpeed method it returns speed in m/s so we are converting it into kmph
        speed = (location.getSpeed() * 18 / 5).toDouble()
    }

    private fun updateUI() {

        setLocationData()
        Log.d(TAG, "Latitude:==" + mCurrentLocation.latitude + "\n Longitude:==" + mCurrentLocation.longitude)
        //  LocationDBHelper.getInstance(this).insertLocationDetails(mLocationData);
        distance1 = distance1 + lStart!!.distanceTo(lEnd) / 1000.00
      //  HomeActivity.endTime = System.currentTimeMillis()
      //  var diff = HomeActivity.endTime - HomeActivity.startTime
       // diff = TimeUnit.MILLISECONDS.toMinutes(diff)
      //  HomeActivity.time.setText("Total Time: $diff minutes")
        if (speed > 0.0)
          //  HomeActivity.speed.setText("Current speed: " + DecimalFormat("#.##").format(speed) + " km/hr")
        else
          //  HomeActivity.speed.setText(".......")

       // HomeActivity.dist.setText(DecimalFormat("#.###").format(distance1) + " Km's.")

        lStart = lEnd

        sendMessageToActivity(mCurrentLocation,speed,distance1)
    }


    private fun sendMessageToActivity(l: Location, speed: Double, distance1: Double) {

        val intent = Intent("GPSLocationUpdates")

        val b = Bundle()
        b.putParcelable("Location", l)
        intent.putExtra("Location", b)
        intent.putExtra(AppConstants.SPEED, speed)
        intent.putExtra(AppConstants.DISTANCE, distance1)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

    }

    fun setLocationData() {

        mLocationData = ArrayList<LocationData>()
        val mLocVo = LocationData()
        mLocVo.setmLongitude(mCurrentLocation.longitude)
        mLocVo.setmLatitude(mCurrentLocation.latitude)
        mLocVo.setmLocAddress(AppConstants.getCompleteAddressString(this, mCurrentLocation.latitude, mCurrentLocation.longitude))
        mLocationData!!.add(mLocVo)

    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected fun stopLocationUpdates() {
        if (mRequestingLocationUpdates!!) {
            mRequestingLocationUpdates = false
            distance1 = 0.0
            // It is a good practice to remove location requests when the activity is in a paused or
            // stopped state. Doing so helps battery speed and is especially
            // recommended in applications that request frequent location updates.

            Log.d(TAG, "stopLocationUpdates();==")
            // The final argument to {@code requestLocationUpdates()} is a LocationListener
            // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }



}