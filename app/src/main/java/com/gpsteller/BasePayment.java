package com.gpsteller;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.braintreepayments.api.BraintreeFragment;
import com.paypal.android.sdk.onetouch.core.PayPalOneTouchCore;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public abstract class BasePayment extends AppCompatActivity {

    private static final String EXTRA_AUTHORIZATION = "com.gpsteller.EXTRA_AUTHORIZATION";
    private static final String EXTRA_CUSTOMER_ID = "com.gpsteller.EXTRA_CUSTOMER_ID";

    protected String mAuthorization;
    protected String mCustomerId;
    protected BraintreeFragment mBraintreeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);

        if (savedInstanceState != null) {
            mAuthorization = savedInstanceState.getString(EXTRA_AUTHORIZATION);
            mCustomerId = savedInstanceState.getString(EXTRA_CUSTOMER_ID);
        }
    }







}
