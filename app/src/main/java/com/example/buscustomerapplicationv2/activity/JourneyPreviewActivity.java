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

        Objects.requireNonNull(getSupportActionBar()).setTitle("Preview");

        cd = new ConnectionDetector(this);
        mYdb = new MYdb(this);

        pssType = "Adult";

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
                }
            }
        });
        backBtn.setOnClickListener(v -> finish());
    }

}
