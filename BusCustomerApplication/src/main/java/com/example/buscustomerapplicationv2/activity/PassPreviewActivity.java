package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.helper.MoveToLogin;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.MenuActivity;

import java.io.Serializable;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PassPreviewActivity extends MenuActivity {

    Button Backbtn, PaynowBtn;

    TextView tvPassDetails, tvTripsAllowed, tvValidity, tvSource, tvDestination, tvAmount;

//    PassSerialNumberResponse passSerialNumberResponse;

    ConnectionDetector cd;

//    ApiInterface apiInterface;
    MYdb mYdb;
//    PassBookQrRequest passBookQrRequest;

    String passDetails, tripsAllowed, validity, source, destination, amountTopay;


    @Override       // for font style
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/caviar_dreams.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_pass_preview);

        mYdb = new MYdb(this);
        cd = new ConnectionDetector(this);
        Backbtn = findViewById(R.id.back_pass_preview);
        PaynowBtn = findViewById(R.id.pay_now_pass_preview);

        tvPassDetails = findViewById(R.id.pass_details_pass_preview);
        tvTripsAllowed = findViewById(R.id.trips_allowed_pass_preview);
        tvValidity = findViewById(R.id.validity_pass_preview);
        tvSource = findViewById(R.id.src_pass_preview);
        tvDestination = findViewById(R.id.des_pass_preview);
        tvAmount = findViewById(R.id.total_amount_pass_preview);

//        passSerialNumberResponse = (PassSerialNumberResponse) getIntent().getSerializableExtra("data");

//        passDetails = passSerialNumberResponse.getPayload().get(0).getPassDetails();
//        tripsAllowed = passSerialNumberResponse.getPayload().get(0).getPassDetails().replaceAll("[^0-9]", "");
//        validity = passSerialNumberResponse.getPayload().get(0).getDuration();
        source = "";//mYdb.getStationName(Integer.parseInt(passSerialNumberResponse.getPayload().get(0).getSrcStpId()));
        destination = "";//mYdb.getStationName(Integer.parseInt(passSerialNumberResponse.getPayload().get(0).getDestStpId()));
        amountTopay = String.valueOf(getIntent().getIntExtra("amount", 0));

        tvPassDetails.setText(passDetails);
        tvTripsAllowed.setText(tripsAllowed);
        tvValidity.setText(validity);
        tvSource.setText(source);
        tvDestination.setText(destination);
        tvAmount.setText(amountTopay);


        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PaynowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    final ProgressDialog progressdialog = ProgressDialog.show(
                            PassPreviewActivity.this, "Please wait",
                            "Loading please wait..", true);
                    progressdialog.show();
                    progressdialog.setCancelable(true);
//                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    loadData();
//                    Call<PassBookQrResponse> call = apiInterface.getPassQR(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, PassPreviewActivity.this), passBookQrRequest);
//
//                    call.enqueue(new Callback<PassBookQrResponse>() {
//                        @Override
//                        public void onResponse(Call<PassBookQrResponse> call, Response<PassBookQrResponse> response) {
//                            if (response.code() == 200) {
//                                PassBookQrResponse passBookQrResponse = response.body();
//                                if (passBookQrResponse.getStatus() == 200) {
//
//                                    Intent intent = new Intent(PassPreviewActivity.this, PostPaymentActivity.class);
//                                    intent.putExtra("data", (Serializable) passBookQrResponse);
//                                    intent.putExtra("flag", 3);
//                                    progressdialog.dismiss();
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    progressdialog.dismiss();
//                                    Toast.makeText(PassPreviewActivity.this, "SERVER ERROR " + passBookQrResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                progressdialog.dismiss();
//                                MoveToLogin.moveToLogin(PassPreviewActivity.this);
//                                Toast.makeText(PassPreviewActivity.this, "Token Expired.", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<PassBookQrResponse> call, Throwable t) {
//                            progressdialog.dismiss();
//                            Toast.makeText(PassPreviewActivity.this, "Failed " + t.getCause(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });


                }
            }
        });
    }

    private void loadData() {

        String userId = AppPreferences.getAppPrefrences(VariablesConstant.EMAIL, this);
        String deviceId = Constants.imei;
        String deviceIp = Constants.ipAddress;
//        String passType = passSerialNumberResponse.getPayload().get(0).getPassType();
        String passDetails = this.passDetails;
//        String passSerialNum = passSerialNumberResponse.getPayload().get(0).getPassSerialNum();
        String documentType = "null";
        String documentRequired = "No";
        String documentAttached = "null";
//        int srcStnId = Integer.parseInt(passSerialNumberResponse.getPayload().get(0).getSrcStpId());
//        int destStnId = Integer.parseInt(passSerialNumberResponse.getPayload().get(0).getDestStpId());
//        String pmtId = passSerialNumberResponse.getPayload().get(0).getPassSerialNum();
//        String trsctId = passSerialNumberResponse.getPayload().get(0).getPassId();
        String paidAmt = this.amountTopay;

//        PassBookQrRequestData passBookQrRequestData = new PassBookQrRequestData(userId, deviceId, deviceIp, passType, passDetails, passSerialNum, documentType, documentRequired, documentAttached, srcStnId, destStnId, pmtId, trsctId, paidAmt);

        int channelId = 1;
        String tokenId = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);

//        passBookQrRequest = new PassBookQrRequest(channelId, tokenId, passBookQrRequestData);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
