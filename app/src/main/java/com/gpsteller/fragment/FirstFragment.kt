package com.gpsteller.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.drivingschool.android.customviews.CustomTextView
import com.gpsteller.R
import com.gpsteller.activities.PaymentActivity
import com.gpsteller.activities.PremiumActivity

class FirstFragment : Fragment() {

    private var title: String? = null
    private var page: Int = 0
    private var txtLeft: CustomTextView? = null
    private var txtRight: CustomTextView? = null
    private var btn_payment: CustomTextView? = null
    private var arrow_forward: ImageView? = null

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getInt("someInt", 0)
        title = arguments!!.getString("someTitle")
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        txtLeft = view.findViewById(R.id.txtLeft)
        txtRight = view.findViewById(R.id.txtRight)
        arrow_forward = view.findViewById(R.id.arrow_forward)
        btn_payment = view.findViewById(R.id.btn_payment)


        arrow_forward!!.setOnClickListener {
            (activity as PremiumActivity).viewPager!!.setCurrentItem(1)

        }

        txtLeft?.setOnClickListener {

            txtLeft!!.setBackgroundResource(R.drawable.round_bg_white)
            txtLeft!!.setTextColor(resources.getColor(R.color.darkyellow))

            txtRight!!.setBackgroundResource(0)
            txtRight!!.setTextColor(resources.getColor(R.color.darkgray))


        }
        txtRight?.setOnClickListener {

            txtRight!!.setBackgroundResource(R.drawable.round_bg_white)
            txtRight!!.setTextColor(resources.getColor(R.color.darkyellow))

            txtLeft!!.setBackgroundResource(0)
            txtLeft!!.setTextColor(resources.getColor(R.color.darkgray))


        }

        btn_payment?.setOnClickListener {
            startActivity(Intent(context,PaymentActivity::class.java))
        }

        return view
    }

    companion object {

        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int, title: String): FirstFragment {
            val fragmentFirst = FirstFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            fragmentFirst.arguments = args
            return fragmentFirst
        }
    }
}
