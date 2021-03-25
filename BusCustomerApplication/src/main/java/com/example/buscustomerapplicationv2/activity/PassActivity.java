package com.example.buscustomerapplicationv2.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.buscustomerapplicationv2.R;

import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.helper.MoveToLogin;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.CustomTypeface;
import com.example.buscustomerapplicationv2.utils.MenuActivity;

import java.util.List;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class PassActivity extends MenuActivity implements AdapterView.OnItemSelectedListener {

    Spinner fromSpinner, toSpinner;
    RadioGroupPlus rg;
    ConstraintLayout cl1, cl2, cl3;
    RadioButton rb1, rb2, rb3;
    Button previewBtn, backBtn;
    TextView tvAmount1, tvAmount2, tvAmount3, tvActualAmt;

    ConnectionDetector cd;
//    PassFareResponse passFareResponse;

    String amount;
//    ApiInterface apiInterface;
//    PassFareRequest passFareRequest;
//
//    PassSerialNumberRequest passSerialNumberRequest;

    int totalAmount;
    String totalAmt;
    String discountAmt;
    String AmoutToPay;
    String passDetails;
    CustomTypeface ct;


    @Override       // for font style
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/caviar_dreams.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_pass);
        cd = new ConnectionDetector(this);
        ct = new CustomTypeface(this);

        previewBtn = findViewById(R.id.pay_now_btn_pass);

        fromSpinner = findViewById(R.id.spinner_from);
        toSpinner = findViewById(R.id.spinner_to);
        backBtn = findViewById(R.id.back_btn_pass);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        cl1 = findViewById(R.id.layout1);
        cl2 = findViewById(R.id.layout2);
        cl3 = findViewById(R.id.layout3);
        tvAmount1 = findViewById(R.id.textView1);
        tvAmount2 = findViewById(R.id.textView2);
        tvAmount3 = findViewById(R.id.textView3);
        tvActualAmt = findViewById(R.id.actual_amount_pass);
        rg = findViewById(R.id.rg1);

        amount = getResources().getString(R.string.Rs);

        cl1.setVisibility(View.INVISIBLE);
        cl2.setVisibility(View.INVISIBLE);
        cl3.setVisibility(View.INVISIBLE);

        loadSpinnerData();

        rg.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb1:
                        tvActualAmt.setText(tvAmount1.getText().toString());
//                        totalAmt = String.valueOf(passFareResponse.getPayload().get(0).getPassAmt());
//                        discountAmt = String.valueOf(passFareResponse.getPayload().get(0).getDiscAmt());
//                        AmoutToPay = String.valueOf(passFareResponse.getPayload().get(0).getAmtToPay());
//                        passDetails = String.valueOf(passFareResponse.getPayload().get(0).getPassDetails());
                        break;
                    case R.id.rb2:
                        tvActualAmt.setText(tvAmount2.getText().toString());
//                        totalAmt = String.valueOf(passFareResponse.getPayload().get(1).getPassAmt());
//                        discountAmt = String.valueOf(passFareResponse.getPayload().get(1).getDiscAmt());
//                        AmoutToPay = String.valueOf(passFareResponse.getPayload().get(1).getAmtToPay());
//                        passDetails = String.valueOf(passFareResponse.getPayload().get(1).getPassDetails());
                        break;
                    case R.id.rb3:
                        tvActualAmt.setText(tvAmount3.getText().toString());
//                        totalAmt = String.valueOf(passFareResponse.getPayload().get(2).getPassAmt());
//                        discountAmt = String.valueOf(passFareResponse.getPayload().get(2).getDiscAmt());
//                        AmoutToPay = String.valueOf(passFareResponse.getPayload().get(2).getAmtToPay());
//                        passDetails = String.valueOf(passFareResponse.getPayload().get(2).getPassDetails());
                        break;
                }
            }
        });


        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cd.isConnectingToInternet()) {
                    final ProgressDialog progressdialog = ProgressDialog.show(
                            PassActivity.this, "Please wait",
                            "Loading please wait..", true);
                    progressdialog.show();
                    progressdialog.setCancelable(true);

                    String text = tvActualAmt.getText().toString();
                    String num = text.replaceAll("[^0-9]", "");
                    totalAmount = Integer.parseInt(num);
                    Toast.makeText(PassActivity.this, "" + num, Toast.LENGTH_LONG).show();

                    loadData1();
//                    Call<PassSerialNumberResponse> call = apiInterface.getPassSerial(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, PassActivity.this), passSerialNumberRequest);
//
//                    call.enqueue(new Callback<PassSerialNumberResponse>() {
//                        @Override
//                        public void onResponse(Call<PassSerialNumberResponse> call, Response<PassSerialNumberResponse> response) {
//
//                            if (response.code() == 200) {
//                                PassSerialNumberResponse passSerialNumberResponse = response.body();
//
//                                if (passSerialNumberResponse.getStatus() == 200) {
//
//                                    Intent intent = new Intent(PassActivity.this, PassPreviewActivity.class);
//                                    intent.putExtra("data", passSerialNumberResponse);
//                                    intent.putExtra("amount", totalAmount);
//
//                                    startActivity(intent);
//                                    progressdialog.dismiss();
//                                } else {
//                                    progressdialog.dismiss();
//                                    Toast.makeText(PassActivity.this, "Server Error : " + passSerialNumberResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                progressdialog.dismiss();
//                                Toast.makeText(PassActivity.this, "Server Error : " + response.message(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<PassSerialNumberResponse> call, Throwable t) {
//                            progressdialog.dismiss();
//                            Toast.makeText(PassActivity.this, "Failed : " + t.getCause(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });

                } else {
                    Toast.makeText(PassActivity.this, " No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }


        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadData1() {


//        String userId = passFareRequest.getPayload().getUserId();
//        String deviceId = passFareRequest.getPayload().getDeviceId();
//        String deviceIp = passFareRequest.getPayload().getDeviceIp();
        String passType = "ltdTripPass";
        String passDetails = this.passDetails;
        String documentType = "null";
        String documentRequired = "No";
        String documentAttached = "null";
//        int srcStpId = Integer.parseInt(passFareRequest.getPayload().getSrcStpId());
//        int destStpId = Integer.parseInt(passFareRequest.getPayload().getDestStpId());
        String totalAmt = this.totalAmt;
        String discountAmt = this.totalAmt;
        String AmoutToPay = this.totalAmt;

//        PassSerialNumberRequestData passSerialNumberRequestData = new PassSerialNumberRequestData(userId, deviceId, deviceIp, passType, passDetails, documentType, documentRequired, documentAttached, srcStpId, destStpId, totalAmt, discountAmt, AmoutToPay);

        int channelID = 1;
        String token = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);

//        passSerialNumberRequest = new PassSerialNumberRequest(channelID, token, passSerialNumberRequestData);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void loadSpinnerData() {
        MYdb db = new MYdb(getApplicationContext());
        List<String> labels = db.getAllLabels();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        fromSpinner.setSelection(0);
        toSpinner.setSelection(0);
        fromSpinner.setAdapter(dataAdapter);
        toSpinner.setAdapter(dataAdapter);
        fromSpinner.setOnItemSelectedListener(this);
        toSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (fromSpinner.getSelectedItemPosition() != 0) {
            if (toSpinner.getSelectedItemPosition() != 0) {
                if (fromSpinner.getSelectedItemPosition() != toSpinner.getSelectedItemPosition()) {
                    if (cd.isConnectingToInternet()) {
                        final ProgressDialog progressdialog = ProgressDialog.show(
                                PassActivity.this, "Please wait",
                                "Loading please wait..", true);
                        progressdialog.show();
                        progressdialog.setCancelable(true);
//
//                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//                        loadData();
//
//                        Call<PassFareResponse> call = apiInterface.getPassFare(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, PassActivity.this), passFareRequest);
//
//                        call.enqueue(new Callback<PassFareResponse>() {
//                            @SuppressLint("SetTextI18n")
//                            @Override
//                            public void onResponse(Call<PassFareResponse> call, Response<PassFareResponse> response) {
//
//                                if (response.code() == 200) {
//                                    passFareResponse = response.body();
//                                    assert passFareResponse != null;
//                                    if (passFareResponse.getStatus() == 200) {
//                                        if (passFareResponse.getPayload().size() > 0) {
//                                            String pass = passFareResponse.getPayload().get(0).getPassDetails();
//                                            String validity = passFareResponse.getPayload().get(0).getPassPeriod();
//                                            String text = String.format(getResources().getString(R.string.pass_details), pass, validity);
//                                            Spannable str = new SpannableString(text);
//                                            str.setSpan(ct.getTypeface(), 0, pass.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                                            cl1.setVisibility(View.VISIBLE);
//                                            rb1.setText(str);
//                                            tvAmount1.setText(amount + " " + passFareResponse.getPayload().get(0).getPassAmt());
//
//                                            pass = passFareResponse.getPayload().get(1).getPassDetails();
//
//                                            validity = passFareResponse.getPayload().get(1).getPassPeriod();
//                                            text = String.format(getResources().getString(R.string.pass_details), pass, validity);
//                                            str = new SpannableString(pass + "\nValid for " + validity);
//                                            str.setSpan(ct.getTypeface(), 0, pass.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                                            amount = getResources().getString(R.string.Rs);
//
//                                            cl2.setVisibility(View.VISIBLE);
//                                            rb2.setText(str);
//                                            tvAmount2.setText(amount + " " + passFareResponse.getPayload().get(1).getPassAmt());
//
//                                            if (passFareResponse.getPayload().size() > 2) {
//
//                                                pass = passFareResponse.getPayload().get(2).getPassDetails();
//                                                validity = passFareResponse.getPayload().get(2).getPassPeriod();
//                                                text = String.format(getResources().getString(R.string.pass_details), pass, validity);
//                                                str = new SpannableString(text);
//                                                str.setSpan(ct.getTypeface(), 0, pass.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                                                cl3.setVisibility(View.VISIBLE);
//                                                rb3.setText(str);
//                                                tvAmount3.setText(amount + " " + passFareResponse.getPayload().get(2).getPassAmt());
//
//                                            }
//                                            progressdialog.dismiss();
//
//                                        } else {
//                                            progressdialog.dismiss();
//                                            Toast.makeText(PassActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        progressdialog.dismiss();
//                                        Toast.makeText(PassActivity.this, "Server Error : " + passFareResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    progressdialog.dismiss();
//                                    MoveToLogin.moveToLogin(PassActivity.this);
//                                    Toast.makeText(PassActivity.this, "Token Expired : " + response.message(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<PassFareResponse> call, Throwable t) {
//                                progressdialog.dismiss();
//                                Toast.makeText(PassActivity.this, "Failed : " + t.getCause(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });


                    }

                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadData() {

        String userId = AppPreferences.getAppPrefrences(VariablesConstant.EMAIL, this);
        String deviceId = Constants.imei;
        String deviceIp = Constants.ipAddress;
        String passType = "ltdTripsPass";
        MYdb mYdb = new MYdb(this);
        String srcStpId = ""; //String.valueOf(mYdb.getStationId(fromSpinner.getSelectedItem().toString()));
        String destStpId = "";//String.valueOf(mYdb.getStationId(toSpinner.getSelectedItem().toString()));
//        PassFareRequestData passFareRequestData = new PassFareRequestData(userId, deviceId, deviceIp, passType, srcStpId, destStpId);
        int channelId = 1;
        String tokenId = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);

//        passFareRequest = new PassFareRequest(channelId, tokenId, passFareRequestData);

    }


}
