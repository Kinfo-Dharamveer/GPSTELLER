package com.gpsteller.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import com.gpsteller.BaseActivity
import com.gpsteller.R
import kotlinx.android.synthetic.main.activity_add_circle.*
import android.text.Editable
import android.view.View
import com.gpsteller.AppConstants
import com.orhanobut.hawk.Hawk


class AddCircleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_circle)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        edGroupName.addTextChangedListener(textWatcher())

        btnClear.setOnClickListener {
            edGroupName.setText("")
        }

        btnFriend.setOnClickListener {
            edGroupName.setText(btnFriend.text.toString())
        }

        btnSibling.setOnClickListener {
            edGroupName.setText(btnSibling.text.toString())
        }
        btnFamily.setOnClickListener {
            edGroupName.setText(btnFamily.text.toString())
        }
        btnSpecial.setOnClickListener {
            edGroupName.setText(btnSpecial.text.toString())
        }
        btnVacation.setOnClickListener {
            edGroupName.setText(btnVacation.text.toString())
        }

        btnDone.setOnClickListener {
            Hawk.put(AppConstants.GROUP_NAME,edGroupName.text.toString())
            startActivity(Intent(this,HomeActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        }

    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!edGroupName.getText().toString().equals("")) { //if edittext include text
                    btnClear.setVisibility(View.VISIBLE)
                   // textView.setText(edGroupName.getText().toString())
                } else { //not include text
                    btnClear.setVisibility(View.GONE)
                   // textView.setText("Edittext cleared!")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        return true
    }


}
