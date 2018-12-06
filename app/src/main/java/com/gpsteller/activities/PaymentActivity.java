package com.gpsteller.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.drivingschool.android.customviews.CustomTextView;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.gpsteller.BasePayment;
import com.gpsteller.Config;
import com.gpsteller.R;
import com.gpsteller.Settings;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;

public class PaymentActivity extends BasePayment {

    // In emulator , localhost = 10.0.2.2
    private static final int DROP_IN_REQUEST = 1;
    final String API_GET_TOKEN = "http://kinfoitsolutions.com/braintree/main.php";
    final String API_CHECK_OUT = "http://kinfoitsolutions.com/braintree/checkout.php";

    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_5qnz8jzt_qkdbwz9nb2kn7ysp";
    private static final String EXTRA_AUTHORIZATION = "com.gpsteller.EXTRA_AUTHORIZATION";

    String mClientToken, amount;
    HashMap<String, String> paramsHash;

    private CustomTextView btn_pay;
    private ImageButton btn_paypal;
    private EditText edt_amount;
    private LinearLayout group_waiting, group_payment;

    private static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Start Paypal Service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);


        if (savedInstanceState != null) {
            mClientToken = savedInstanceState.getString(EXTRA_AUTHORIZATION);
        }


        btn_pay = findViewById(R.id.btn_pay);
        edt_amount = findViewById(R.id.edt_amount);
        btn_paypal = findViewById(R.id.btn_paypal);

        group_waiting = findViewById(R.id.waiting_group);
        group_payment = findViewById(R.id.payment_group);


        new getToken().execute();


        //Event
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_amount.getText().toString().isEmpty()) {
                    edt_amount.setError("Enter amount first");
                } else {

                    submitPayment();
                }
            }
        });

        //Event
        btn_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_amount.getText().toString().isEmpty()) {
                    edt_amount.setError("Enter amount first");
                } else {

                    processPayment();
                }



            }
        });


    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    private void processPayment() {
        amount = edt_amount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Donate for EDMTDev",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    private void submitPayment() {

        DropInRequest dropInRequest = new DropInRequest().clientToken(mClientToken);
        startActivityForResult(dropInRequest.getIntent(this), DROP_IN_REQUEST);
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                if (!edt_amount.getText().toString().isEmpty()) {
                    amount = edt_amount.getText().toString();
                    paramsHash = new HashMap<>();
                    paramsHash.put("amount", amount);
                    paramsHash.put("nonce", strNonce);

                    sendPayments();
                } else {
                    Toast.makeText(this, "Please enter valid amount", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == RESULT_CANCELED)
                Toast.makeText(this, "User cancel the transaction", Toast.LENGTH_SHORT).show();
            else {
                //Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                // Log.d("EDMT_ERROR", error.toString());
            }

        }
        else if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation !=null){

                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this,PaymentDetail.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("PaymentAmount",amount)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPayments() {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().contains("Successful"))
                            Toast.makeText(PaymentActivity.this, "Transaction successful!", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(PaymentActivity.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_LOG", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_LOG", error.toString());

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramsHash.keySet()) {
                    params.put(key, paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private class getToken extends AsyncTask {

        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(PaymentActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please wait");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Hide group waiting
                            group_waiting.setVisibility(View.GONE);
                            //Show group payment
                            group_payment.setVisibility(View.VISIBLE);

                            //Set token
                            mClientToken = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Toast.makeText(PaymentActivity.this, exception.toString(), Toast.LENGTH_LONG);
                }
            });
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }
    }
}
