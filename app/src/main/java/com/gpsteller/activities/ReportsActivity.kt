package com.gpsteller.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.gpsteller.R
import com.gpsteller.adapters.LocationCountAdapter
import com.gpsteller.models.LocationCountList
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.gpsteller.AppConstants
import com.gpsteller.adapters.AlertDetailAdapter
import com.gpsteller.adapters.LocationDetailAdapter
import com.gpsteller.models.AlertDetailList
import com.gpsteller.models.LocationDetailList
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ReportsActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    var mLayoutManager: LinearLayoutManager? = null

    var loc_count: LinearLayout? = null
    var loc_detail: LinearLayout? = null
    var alert_detail: LinearLayout? = null


    private var mAdapter: LocationCountAdapter? = null
    private val locaArrayList = ArrayList<LocationCountList>()

    private var locationDetailAdapter: LocationDetailAdapter? = null
    private val locationDetailListList = ArrayList<LocationDetailList>()


    private var alertDetailAdap: AlertDetailAdapter? = null
    private val alertDetailArrayList = ArrayList<AlertDetailList>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerViewReports)

        loc_count = findViewById(R.id.loc_count)
        loc_detail = findViewById(R.id.loc_detail)
        alert_detail = findViewById(R.id.alert_detail)

        val extras = intent.extras
        val reportType: String?

        if (extras != null) {
            reportType = extras.getString(AppConstants.SPIN_KEY)

            if (reportType.equals("Location Count Report")){

                loc_count!!.visibility = View.VISIBLE
                mAdapter = LocationCountAdapter(this, locaArrayList)
                mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView!!.setLayoutManager(mLayoutManager)
                recyclerView!!.setItemAnimator(DefaultItemAnimator())
                recyclerView!!.setAdapter(mAdapter)
                prepareLocaCountData()

            } else if (reportType.equals("Location Detail Report")){

                loc_detail!!.visibility = View.VISIBLE
                locationDetailAdapter = LocationDetailAdapter(this, locationDetailListList)
                mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView!!.setLayoutManager(mLayoutManager)
                recyclerView!!.setItemAnimator(DefaultItemAnimator())
                recyclerView!!.setAdapter(locationDetailAdapter)
                prepareLocaDetailData()

            }

            else if (reportType.equals("Alert Count Report")){

                loc_count!!.visibility = View.VISIBLE
                mAdapter = LocationCountAdapter(this, locaArrayList)
                mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView!!.setLayoutManager(mLayoutManager)
                recyclerView!!.setItemAnimator(DefaultItemAnimator())
                recyclerView!!.setAdapter(mAdapter)
                prepareLocaCountData()
            }

            else if (reportType.equals("Alert Detail Report")){

                alert_detail!!.visibility = View.VISIBLE

                alertDetailAdap = AlertDetailAdapter(alertDetailArrayList,this)
                mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerView!!.setLayoutManager(mLayoutManager)
                recyclerView!!.setItemAnimator(DefaultItemAnimator())
                recyclerView!!.setAdapter(alertDetailAdap)
                prepareAlertDataData()
            }





        }


    }

    private fun prepareAlertDataData() {

        val alertDetailList = AlertDetailList("Jack","Warn","No Activity","Sun,14 Oct 2018 16:03:04","Active")
        alertDetailArrayList.add(alertDetailList)

        val alertDetailList1 = AlertDetailList("Jack","Warn","No Activity","Sun,14 Oct 2018 16:03:04","Active")
        alertDetailArrayList.add(alertDetailList1)

        val alertDetailList2 = AlertDetailList("Jack","Warn","No Activity","Sun,14 Oct 2018 16:03:04","Active")
        alertDetailArrayList.add(alertDetailList2)

        val alertDetailList3 = AlertDetailList("Jack","Warn","No Activity","Sun,14 Oct 2018 16:03:04","Active")
        alertDetailArrayList.add(alertDetailList3)

        val alertDetailList4 = AlertDetailList("Jack","Warn","No Activity","Sun,14 Oct 2018 16:03:04","Active")
        alertDetailArrayList.add(alertDetailList4)



    }

    private fun prepareLocaDetailData() {

        val locatioDetailList = LocationDetailList("Mon, 12 Nov 2018 15:29:49", "Ignition Off", "0.00", "0.01", "Yaokrom, Yaokrom, GH")
        locationDetailListList.add(locatioDetailList)

        val locatioDetailList1 = LocationDetailList("Mon, 12 Nov 2018 15:29:49", "Ignition Off", "0.00", "0.01", "Yaokrom, Yaokrom, GH")
        locationDetailListList.add(locatioDetailList1)

        val locatioDetailList2 = LocationDetailList("Mon, 12 Nov 2018 15:29:49", "Ignition Off", "0.00", "0.01", "Yaokrom, Yaokrom, GH")
        locationDetailListList.add(locatioDetailList2)

        val locatioDetailList3 = LocationDetailList("Mon, 12 Nov 2018 15:29:49", "Ignition Off", "0.00", "0.01", "Yaokrom, Yaokrom, GH")
        locationDetailListList.add(locatioDetailList3)

        locationDetailAdapter!!.notifyDataSetChanged()


    }

    private fun prepareLocaCountData() {

        val locationCountList = LocationCountList("Excavator NAP", "11283")
        locaArrayList.add(locationCountList)

        val locationCountList1 = LocationCountList("Excavator NAP", "11283")
        locaArrayList.add(locationCountList1)


        val locationCountList2 = LocationCountList("Excavator NAP", "11283")
        locaArrayList.add(locationCountList2)

        mAdapter!!.notifyDataSetChanged()

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }

}
