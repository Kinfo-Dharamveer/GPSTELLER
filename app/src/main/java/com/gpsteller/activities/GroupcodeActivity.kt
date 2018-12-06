package com.gpsteller.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.chaos.view.PinView
import com.gpsteller.BaseActivity
import com.gpsteller.R
import android.text.Editable
import android.text.TextWatcher
import android.support.v4.content.res.ResourcesCompat
import com.drivingschool.android.customviews.CustomTextView


class GroupcodeActivity : BaseActivity() {

    lateinit var pinView: PinView
    var txtSubmit: CustomTextView? = null
    var txtNewcircle: CustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupcode)

        pinView =  findViewById(R.id.pinView)
        txtSubmit =  findViewById(R.id.txtSubmit)
        txtNewcircle =  findViewById(R.id.txtNewcircle)

        pinViewCode()


        txtNewcircle!!.setOnClickListener {
            progressDialog!!.show()
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        if (progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog!!.dismiss()
    }


    private fun pinViewCode() {

        pinView.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccent, theme))
        pinView.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.black, theme))
        pinView.setLineColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
        pinView.setLineColor(ResourcesCompat.getColorStateList(resources, R.color.white, theme))
        pinView.setItemCount(5)
        pinView.setItemHeight(resources.getDimensionPixelSize(R.dimen.pv_pin_view_item_size))
        pinView.setItemWidth(resources.getDimensionPixelSize(R.dimen.pv_pin_view_item_size))
        pinView.setItemRadius(resources.getDimensionPixelSize(R.dimen.pv_pin_view_item_radius))
        pinView.setItemSpacing(resources.getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing))
        pinView.setLineWidth(resources.getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width))
        pinView.setAnimationEnable(true)// start animation when adding text
        pinView.setCursorVisible(true)
        pinView.setCursorColor(ResourcesCompat.getColor(resources, R.color.lightgrey, theme))
        pinView.setCursorWidth(resources.getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width))

        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        pinView.setItemBackgroundColor(Color.BLACK)
        pinView.setItemBackground(resources.getDrawable(R.drawable.code_bg))
        pinView.setItemBackgroundResources(R.drawable.code_bg)
        pinView.setHideLineWhenFilled(false)
    }
}
