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
import com.gpsteller.R.id.pager
import com.gpsteller.activities.PaymentActivity
import com.gpsteller.activities.PremiumActivity


class SecondFragment : Fragment() {

    private var title: String? = null
    private var page: Int = 0
    private var txtLeftSec: CustomTextView? = null
    private var txtRightSec: CustomTextView? = null
    private var btn_payment2: CustomTextView? = null
    private var arrow_back: ImageView? = null


    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getInt("someInt", 0)
        title = arguments!!.getString("someTitle")
    }


    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        txtLeftSec = view.findViewById(R.id.txtLeftSec)
        txtRightSec = view.findViewById(R.id.txtRightSec)
        arrow_back = view.findViewById(R.id.arrow_back)
        btn_payment2 = view.findViewById(R.id.btn_payment2)


        arrow_back!!.setOnClickListener {
            (activity as PremiumActivity).viewPager!!.setCurrentItem(0)

        }

        txtLeftSec?.setOnClickListener {

            txtLeftSec!!.setBackgroundResource(R.drawable.round_bg_white)
            txtLeftSec!!.setTextColor(resources.getColor(R.color.darkyellow))

            txtRightSec!!.setBackgroundResource(0)
            txtRightSec!!.setTextColor(resources.getColor(R.color.darkgray))


        }
        txtRightSec?.setOnClickListener {

            txtRightSec!!.setBackgroundResource(R.drawable.round_bg_white)
            txtRightSec!!.setTextColor(resources.getColor(R.color.darkyellow))

            txtLeftSec!!.setBackgroundResource(0)
            txtLeftSec!!.setTextColor(resources.getColor(R.color.darkgray))


        }

        btn_payment2?.setOnClickListener {
            startActivity(Intent(context, PaymentActivity::class.java))
        }


        return view

    }

    companion object {

        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int, title: String): SecondFragment {
            val secondFragment = SecondFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            secondFragment.arguments = args
            return secondFragment
        }
    }
}
