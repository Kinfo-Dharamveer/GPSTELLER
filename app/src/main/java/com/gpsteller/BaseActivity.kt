package com.gpsteller

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.drivingschool.android.customviews.CustomTextView
import dmax.dialog.SpotsDialog


open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = SpotsDialog(this, R.style.Custom)


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    }


    fun SuccessDialog(statusSuccess: String, statusSuccessMsg: String) {
        val factory = LayoutInflater.from(this)
        val SuccessDialogView = factory.inflate(R.layout.success_layout_dialog, null)
        val successDialog = AlertDialog.Builder(this).create()
        successDialog.setView(SuccessDialogView)

        val txtStatus = SuccessDialogView.findViewById<TextView>(R.id.txtStatus)
        val txtStatusMsg = SuccessDialogView.findViewById<TextView>(R.id.txtStatusMsg)

        txtStatus.setText(statusSuccess)
        txtStatusMsg.setText(statusSuccessMsg)

        SuccessDialogView.findViewById<CustomTextView>(R.id.txtOk).setOnClickListener {
            successDialog.dismiss()
        }
        successDialog.show()
    }


    fun ErrorDialog(statusError: String, statusErrorMsg: String) {

        val factory = LayoutInflater.from(this)
        val ErrorDialogView = factory.inflate(R.layout.error_layout_dialog, null)
        val errorDialog = AlertDialog.Builder(this).create()
        errorDialog.setView(ErrorDialogView)

        val txtStatusError = ErrorDialogView.findViewById<TextView>(R.id.txtStatusError)
        val txtStatusMsgError = ErrorDialogView.findViewById<TextView>(R.id.txtStatusMsgError)

        txtStatusError.setText(statusError)
        txtStatusMsgError.setText(statusErrorMsg)

        ErrorDialogView.findViewById<CustomTextView>(R.id.txtOk).setOnClickListener {
            errorDialog.dismiss()
        }

        errorDialog.show()
    }


    fun ErrorAlertDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(msg)

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}