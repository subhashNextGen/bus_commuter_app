package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.helper.IncreamentDecreament;
import com.example.buscustomerapplicationv2.helper.MoveToLogin;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.MenuActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends MenuActivity implements AdapterView.OnItemSelectedListener {

    Spinner fromSpinner, toSpinner;
    Button addAdult, previewBtn, back;
    TextView discountFare, totalFare, actualFare;
    TextView joourneyTIck, amountToPay;

    float disc = 0, Actual;
    float Total;
    int adult = 0;

    String pssType;
    int noOfTkt;
    float tktAmount;
    float discount;
    float amtPaid;
    float totalAmt;
    String type, from, to;

    String srcStpId;
    String destStpId;

    ConnectionDetector cd;

    String jType;

    MYdb mYdb;


    int fare = 0;

    int typeT;

    RadioButton singleTrip, returnTrip;

    IncreamentDecreament mID;

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
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Ticket");

        mID = new IncreamentDecreament(this);

        singleTrip = findViewById(R.id.single_radio_home);
        returnTrip = findViewById(R.id.return_radio_home);
//        addAdult = findViewById(R.id.increase_adult_home);
//        minusAdult = findViewById(R.id.decrease_adult_home);
//        paxSpinner = findViewById(R.id.spinner_paxtype);
//
//        adultCount = findViewById(R.id.adult_number_home);

        previewBtn = findViewById(R.id.preview_home);
//        adultFare = findViewById(R.id.adult_amount_home);

        discountFare = findViewById(R.id.discounted_fare_home);
        totalFare = findViewById(R.id.total_amount_home);
        actualFare = findViewById(R.id.actual_amount_home);
        back = findViewById(R.id.back_home);
        joourneyTIck = findViewById(R.id.juorneyTick);
        fromSpinner = findViewById(R.id.spinner_from);
        toSpinner = findViewById(R.id.spinner_to);
        amountToPay = findViewById(R.id.amounttopay_home);

        loadSpinnerData();
        fromSpinner.setOnItemSelectedListener(this);
        toSpinner.setOnItemSelectedListener(this);

        int type = getIntent().getIntExtra("type", 0);

        if (type == 2) {
            returnTrip.setChecked(true);
            jType = "RJT";
            typeT = 2;

        } else {
            jType = "SJT";
            typeT = 1;
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/CaviarDreams_Bold.ttf");
        joourneyTIck.setTypeface(typeface);
        actualFare.setTypeface(typeface);
        amountToPay.setTypeface(typeface);

//        adultFare.setText("Rs 0.00");

        totalFare.setText("Rs 0.00");

        cd = new ConnectionDetector(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        addAdult.setOnClickListener(new View.OnClickListener() {
//             @Override
//            public void onClick(View v) {
//                int from = fromSpinner.getSelectedItemPosition();
//                int to = toSpinner.getSelectedItemPosition();
//                if (from != 0 && to != 0 && from != to) {
////                    if (paxSpinner.getSelectedItemPosition() != 0) {
////                        mID.increase(adultCount);
//
//                } else {
//                    Toast.makeText(HomeActivity.this, "Please select Pax Type", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

//        minusAdult.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int from = fromSpinner.getSelectedItemPosition();
//                int to = toSpinner.getSelectedItemPosition();
//
//                if (from != 0 && to != 0 && from != to) {
//                    mID.decrease(adultCount);
//                    if (!adultCount.getText().toString().equals("0")) {
//                        dataUpdate();
//                    } else {
//                        refreshData();
//                        Toast.makeText(HomeActivity.this, "Check Source and Destination", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }
//        });

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isvalidation()) {
//                    int adultCounting = Integer.parseInt(adultCount.getText().toString().trim());

                    final ProgressDialog progressdialog = ProgressDialog.show(
                            HomeActivity.this, "Please wait",
                            "Loading please wait..", true);
                    progressdialog.show();
                    progressdialog.setCancelable(true);
//                    String srcRoute = mYdb.getRouteNum(String.valueOf(fromSpinner.getSelectedItem()));
//                    String desRoute = mYdb.getRouteNum(String.valueOf(toSpinner.getSelectedItem()));
//                    if (srcRoute.equals(desRoute)) {
//                        loadData1();
//                        Call<SJTTicketGenerateResponse> call = apiInterface.generateTicketID(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, HomeActivity.this), sjtTicketGenerateRequest);
//
//                        call.enqueue(new Callback<SJTTicketGenerateResponse>() {
//                            @Override
//                            public void onResponse(Call<SJTTicketGenerateResponse> call, Response<SJTTicketGenerateResponse> response) {
//                                if (response.code() == 200) {
//                                    SJTTicketGenerateResponse sjtTicketGenerateResponse = response.body();
//                                    assert sjtTicketGenerateResponse != null;
//                                    if (sjtTicketGenerateResponse.getStatus() == 200) {
//                                        Intent intent = new Intent(HomeActivity.this, JourneyPreviewActivity.class);
//                                        intent.putExtra("data", (Serializable) sjtTicketGenerateResponse);
////                                        intent.putExtra("srcid",srcStpId);
////                                        intent.putExtra("destid",destStpId);
//                                        progressdialog.dismiss();
//                                        startActivity(intent);
//                                    }
//                                } else {
//                                    progressdialog.dismiss();
//                                    MoveToLogin.moveToLogin(HomeActivity.this);
//                                    Toast.makeText(HomeActivity.this, "Token Expired.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<SJTTicketGenerateResponse> call, Throwable t) {
//
//                            }
//                        });

//                    }
//                    else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                            }
//                        });
//                        builder.setTitle("Error ");
//                        builder.setMessage("There is no direct Bus available \nKindly change stops");
//                        builder.setCancelable(true);
//                        builder.show();
//                        progressdialog.dismiss();
//                    }

                }

            }
        });

    }

    private void UpdateFare() {

        Total = (float) fare;
        Actual = Total - disc;
//        this.adultFare.setText(String.format("Rs %.2f", adultFare));
        this.totalFare.setText(String.format("Rs %.2f", Total));
        this.discountFare.setText(String.format("Rs %.2f", disc));
        this.actualFare.setText(String.format("Rs %.2f", Actual));
        Log.e("Data_Spinner", fromSpinner.getSelectedItem().toString());
//        Toast.makeText(this, ""+fromSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

    }

    private void refreshData() {
        float adultFare = 0;
        Total = 0;
        Actual = 0;
        disc = 0;
//        this.adultFare.setText(String.format("Rs %.2f", adultFare));
        this.totalFare.setText(String.format("Rs %.2f", Total));
        this.discountFare.setText(String.format("Rs %.2f", disc));
        this.actualFare.setText(String.format("Rs %.2f", Actual));
//        adultCount.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public boolean isvalidation() {

        String name1 = fromSpinner.getSelectedItem().toString();
        String name2 = toSpinner.getSelectedItem().toString();

        if (name1.equals("0")) {
            fromSpinner.setFocusable(true);
            return false;
        }
        if (name2.equals("0")) {
            toSpinner.setFocusable(true);
            return false;
        }
//        if (adultCount.getText().toString().trim().equals("0")) {
//            adultCount.setFocusable(true);
//            return false;
//        }

        return true;
    }

    private void dataUpdate() {
        int fromname = fromSpinner.getSelectedItemPosition();
        int toname = toSpinner.getSelectedItemPosition();
        if (fromname != 0) {
            if (toname != 0) {
                final ProgressDialog progressdialog = ProgressDialog.show(
                        HomeActivity.this, "Please wait",
                        "Loading please wait..", true);
                progressdialog.show();
                progressdialog.setCancelable(true);

                Log.i("calling :" + fromname, "Call :" + toname);
                if (fromname != toname) {
//                        Toast.makeText(this, "" + fromname + "     " + toname, Toast.LENGTH_LONG).show();
                    mYdb = new MYdb(this);
                    if (cd.isConnectingToInternet()) {
                        loadData();
//                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//                        Call<FareResponse> call = apiInterface.getFare(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN, HomeActivity.this), fareRequestApi);
//                        call.enqueue(new Callback<FareResponse>() {
//                            @Override
//                            public void onResponse(Call<FareResponse> call, Response<FareResponse> response) {
//                                if (response.code() == 200) {
//                                    FareResponse fareResponse = response.body();
//                                    assert fareResponse != null;
//                                    if (fareResponse.getStatus() == 200) {
//
//                                        fare = Integer.parseInt(fareResponse.getPayload().get(0).getfareAmount());
//                                        disc = Float.parseFloat(fareResponse.getPayload().get(0).getDiscount());
//                                        UpdateFare();
//                                        progressdialog.dismiss();
//
//                                    } else {
//                                        progressdialog.dismiss();
//                                        Toast.makeText(HomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } else {
//                                    progressdialog.dismiss();
//                                    MoveToLogin.moveToLogin(HomeActivity.this);
//                                    Toast.makeText(HomeActivity.this, "Token Expired.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<FareResponse> call, Throwable t) {
//                                progressdialog.dismiss();
//                                Toast.makeText(HomeActivity.this, "Failed : " + t.getCause(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                }
            }
        }
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

    }


    private void loadData() {
        int channelId = 1;
        String tokenId = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);
        String ticketType = jType;
        int srcStpId = 1;//mYdb.getStationId(fromSpinner.getSelectedItem().toString());
        int destStnId = 2; //mYdb.getStationId(toSpinner.getSelectedItem().toString());
        int paxType = 3;
        int tktJrnyType = typeT;
        int noOfPax = 1;
//        FareRequestData payload = new FareRequestData(ticketType, srcStpId, destStnId, paxType, tktJrnyType, noOfPax);
//        fareRequestApi = new FareRequestApi(channelId, tokenId, payload);
    }

    public void loadData1() {

        pssType = "Adult";
        noOfTkt = 1;
        tktAmount = fare;
        discount = disc;
        amtPaid = Actual;
        totalAmt = Total;
        type = jType;

//        PassengerInfoSJTRequestData passengerInfoSJTRequestData = new PassengerInfoSJTRequestData(pssType, noOfTkt, tktAmount, discount, amtPaid, totalAmt);
//        List<PassengerInfoSJTRequestData> llst = new ArrayList<>();
//        llst.add(passengerInfoSJTRequestData);

        String userId = AppPreferences.getAppPrefrences(VariablesConstant.EMAIL, this);
        srcStpId = "1";//String.valueOf(mYdb.getStationId(fromSpinner.getSelectedItem().toString()));
        destStpId = "2";//String.valueOf(mYdb.getStationId(toSpinner.getSelectedItem().toString()));
        String cust_IPaddress = Constants.ipAddress;
        String cust_IMIE_No = Constants.imei;
        String tktType = type;

        String token = AppPreferences.getAppPrefrences(VariablesConstant.TOKEN, this);

//        SJTTicketGenerateRequestData sjtTicketGenerateRequestData = new SJTTicketGenerateRequestData(userId, srcStpId, destStpId, cust_IPaddress, cust_IMIE_No, tktType, llst);
//
//        sjtTicketGenerateRequest = new SJTTicketGenerateRequest(1, token, sjtTicketGenerateRequestData);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        refreshData();
        dataUpdate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
