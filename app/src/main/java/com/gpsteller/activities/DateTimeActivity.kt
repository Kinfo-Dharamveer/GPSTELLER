package com.gpsteller.activities

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gpsteller.R
import android.widget.EditText
import java.util.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.app.TimePickerDialog
import android.content.Intent
import com.drivingschool.android.customviews.CustomTextView


class DateTimeActivity : AppCompatActivity() {

    var btnDatePicker: Button? = null
    var btnTimePicker:Button? = null
    var txtDate: EditText? = null
    var txtTime:EditText? = null
    var getReports:CustomTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datetime)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        btnDatePicker=findViewById(R.id.btn_date);
        btnTimePicker=findViewById(R.id.btn_time);
        txtDate=findViewById(R.id.in_date);
        txtTime=findViewById(R.id.in_time);
        getReports=findViewById(R.id.txtReports);

        btnDatePicker!!.setOnClickListener{

            // Get Current date
            val calendar = Calendar.getInstance()
           val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)
            val mHour = calendar.get(Calendar.HOUR_OF_DAY);
            val mMinute = calendar.get(Calendar.MINUTE);
            val datePickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        txtDate!!.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)
            datePickerDialog.show()

        }

        btnTimePicker!!.setOnClickListener{
            // Get Current Time
            val calendar = Calendar.getInstance();
            val mHour = calendar.get(Calendar.HOUR_OF_DAY);
            val mMinute = calendar.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        txtTime!!.setText(hourOfDay.toString() + ":" + minute) },
                    mHour, mMinute, false)
            timePickerDialog.show()
        }

        getReports!!.setOnClickListener {

            overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }

}
