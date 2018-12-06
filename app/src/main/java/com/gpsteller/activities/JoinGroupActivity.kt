package com.gpsteller.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.chaos.view.PinView
import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.BaseActivity
import com.gpsteller.R

class JoinGroupActivity : BaseActivity() {

    lateinit var pinView: PinView
    lateinit var txtSubmit: CustomTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_group)


        pinView =  findViewById(R.id.pinViewJoin)
        txtSubmit =  findViewById(R.id.txtSubmit)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        pinViewCode()


        txtSubmit.setOnClickListener {

            progressDialog.show()

            Toast.makeText(this,pinView.text,Toast.LENGTH_SHORT).show()

            progressDialog.dismiss()

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
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
