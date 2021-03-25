package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JourneyPreviewActivity extends MenuActivity {

    Button payNow, backBtn;

    TextView preview;

    TextView tvType, tvnOfT, tvAmount, tvDis, tvTotal, tvFrom, tvTo;

    LinearLayout adultLayout;

    ConnectionDetector cd;

//    ApiInterface apiInterface;
    MYdb mYdb;
//    SJTicketRequest sjTicketRequest;

    String pssType;
    int noOfTkt;
    float tktAmount;
    float discount;
    float amtPaid;
    float totalAmt;
    String type;
    int from;
    int to;

//    SJTTicketGenerateResponse sjtTicketGenerateResponse;
//    SJTTicketGenerateRequest sjtTicketGenerateRequest;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

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
        setContentView(R.layout.activity_journey_preview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Preview");
        } else {
            getSupportActionBar().setTitle("Preview");
        }

        cd = new ConnectionDetector(this);
        mYdb = new MYdb(this);

//        sjtTicketGenerateResponse = (SJTTicketGenerateResponse) getIntent().getSerializableExtra("data");

        pssType = "Adult";
//        noOfTkt = sjtTicketGenerateResponse.getPayload().get(0).getPsgList().get(0).getNoOfTkt();
//        tktAmount = sjtTicketGenerateResponse.getPayload().get(0).getPsgList().get(0).getTktAmount();
//        discount = sjtTicketGenerateResponse.getPayload().get(0).getPsgList().get(0).getDiscount();
//        amtPaid = sjtTicketGenerateResponse.getPayload().get(0).getPsgList().get(0).getAmtPaid();
//        totalAmt = sjtTicketGenerateResponse.getPayload().get(0).getPsgList().get(0).getTotalAmt();
//        from = Integer.parseInt(sjtTicketGenerateResponse.getPayload().get(0).getsrcStpId() == null ? getIntent().getStringExtra("srcid") : sjtTicketGenerateResponse.getPayload().get(0).getsrcStpId());
//        to = Integer.parseInt(sjtTicketGenerateResponse.getPayload().get(0).getdestStpId() == null ? getIntent().getStringExtra("destid") : sjtTicketGenerateResponse.getPayload().get(0).getdestStpId());
//        type = sjtTicketGenerateResponse.getPayload().get(0).getTktType();

        payNow = findViewById(R.id.pay_now_journey_preview);
        backBtn = findViewById(R.id.back_journey_preview);

        tvType = findViewById(R.id.ticket_type_journey_preview);
        tvnOfT = findViewById(R.id.number_of_ticket_journey_preview);
        tvAmount = findViewById(R.id.adult_journey_preview);
        tvDis = findViewById(R.id.dis_amount_journey_preview);
        tvTotal = findViewById(R.id.total_amount_journey_preview);
        tvFrom = findViewById(R.id.from_journey_preview);
        tvTo = findViewById(R.id.to_journey_preview);

        adultLayout = findViewById(R.id.adultLayout);

        preview = findViewById(R.id.preive_preview);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/CaviarDreams_Bold.ttf");
        preview.setTypeface(typeface);

        tvType.setText(type);
        tvnOfT.setText(String.valueOf(noOfTkt));
        tvAmount.setText(String.valueOf(totalAmt));
        tvDis.setText(String.valueOf(discount));
        tvTotal.setText(String.valueOf(amtPaid));
//        tvFrom.setText(mYdb.getStationName(from));
//        tvTo.setText(mYdb.getStationName(to));

        if (noOfTkt == 0) {
            adultLayout.setVisibility(View.GONE);
        }

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    final ProgressDialog progressdialog = ProgressDialog.show(
                            JourneyPreviewActivity.this, "Please wait",
                            "Loading please wait..", true);
                    progressdialog.show();
                    progressdialog.setCancelable(true);
                    loadData();
//                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
//                    Call<SjtQrResponse> call = apiInterface.sjtBook(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, JourneyPreviewActivity.this), sjTicketRequest);
//
//                    call.enqueue(new Callback<SjtQrResponse>() {
//                        @Override
//                        public void onResponse(Call<SjtQrResponse> call, Response<SjtQrResponse> response) {
//                            if (response.code() == 200) {
//                                //Toast.makeText(JourneyPreviewActivity.this, "Success" + response.body(), Toast.LENGTH_SHORT).show();
//                                SjtQrResponse sjtQrResponse = response.body();
//                                assert sjtQrResponse != null;
//                                if (sjtQrResponse.getStatus() == 200) {
//                                    Intent intent = new Intent(JourneyPreviewActivity.this, PostPaymentActivity.class);
//                                    intent.putExtra("data", sjtQrResponse);
////                                    intent.putExtra("srcid",String.valueOf(from));
////                                    intent.putExtra("destid",String.valueOf(to));
//                                    intent.putExtra("flag", 1);
//                                    progressdialog.dismiss();
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    progressdialog.dismiss();
//                                    Toast.makeText(JourneyPreviewActivity.this, "Server Error : " + sjtQrResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            } else {
//                                progressdialog.dismiss();
//                                MoveToLogin.moveToLogin(JourneyPreviewActivity.this);
//                                Toast.makeText(JourneyPreviewActivity.this, "Token Expired.", Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<SjtQrResponse> call, Throwable t) {
//
//                        }
//                    });

                }
//                Intent intent = new Intent(JourneyPreviewActivity.this, PostPaymentActivity.class);
//                startActivity(intent);

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {

        MYdb mYdb = new MYdb(this);
//        PassengerInfoSJTRequestData passengerInfoSJTRequestData = new PassengerInfoSJTRequestData(pssType, noOfTkt, tktAmount, discount, amtPaid, totalAmt);
//
//        List<PassengerInfoSJTRequestData> llst = new ArrayList<>();
//        llst.add(passengerInfoSJTRequestData);
//
//        String userId = sjtTicketGenerateResponse.getPayload().get(0).getUserId();
//        String tktBookingDtTime = sjtTicketGenerateResponse.getPayload().get(0).getTktRequestDateTime();
//        String srcStpId = sjtTicketGenerateResponse.getPayload().get(0).getSrcStpId();
//        String destStpId = sjtTicketGenerateResponse.getPayload().get(0).getDestStpId();
//
//        long tktNo = Long.parseLong(sjtTicketGenerateResponse.getPayload().get(0).getTktSrNo());
        String cust_IPaddress = Constants.ipAddress;
        String cust_IMIE_No = Constants.imei;
        String tktType = type;
        String paymentMode = "Credit Card";
        String paidAmt = String.valueOf(amtPaid);

//        String PaymentId = "P" + tktNo;

        String token = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);

//        SJTicketRequestData sjTicketRequestData = new SJTicketRequestData(userId, tktBookingDtTime, srcStpId, destStpId, tktNo, cust_IPaddress, cust_IMIE_No, tktType, llst, paymentMode, paidAmt, PaymentId);
//
//        sjTicketRequest = new SJTicketRequest(1, token, sjTicketRequestData);
    }

}
