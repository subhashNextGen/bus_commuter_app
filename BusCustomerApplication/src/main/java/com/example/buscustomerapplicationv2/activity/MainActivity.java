package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;

import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {


    Button Singlejourney, returnJourney, MPass, Vticket, myTicket, myTrip, myProfile, lgOut;
    ProgressBar pr;
    LinearLayout img;
    TextView selectTick;

    RelativeLayout relativeLayout;

    ConnectionDetector cd;
    MYdb mYdb;

//    ApiInterface apiInterface;
//    MasterRequest masterRequest;

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
        setContentView(R.layout.activity_main);

        cd=new ConnectionDetector(this);
        relativeLayout=findViewById(R.id.relativeLayout);

        final ProgressDialog progressdialog = ProgressDialog.show(
                MainActivity.this, "Please wait",
                "Loading please wait..", true);
        progressdialog.show();
        progressdialog.setCancelable(true);
        loadData();

//        apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        Call<MasterData> call=apiInterface.getMasterData(AppPreferences.getAppPrefrences(VariablesConstant.AUTH_TOKEN,MainActivity.this),masterRequest);

        if (cd.isConnectingToInternet()){

      }
        else
        {
            progressdialog.dismiss();
            Snackbar.make(relativeLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        } else {
            getSupportActionBar().setTitle("Home");
        }

        Singlejourney = findViewById(R.id.singleBtn);
        returnJourney = findViewById(R.id.returnBtn);
        MPass = findViewById(R.id.passBtn);
        Vticket = findViewById(R.id.vtBtn);
        myTicket = findViewById(R.id.myTicketBtn);
        myTrip = findViewById(R.id.mTripsBtn);
        myProfile = findViewById(R.id.mProfileBtn);
        lgOut = findViewById(R.id.lgBtn);
        pr = findViewById(R.id.progress_main);
        img = findViewById(R.id.imageview_main);
        selectTick = findViewById(R.id.selecttick);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/CaviarDreams_Bold.ttf");
        selectTick.setTypeface(typeface);

        img.setVisibility(View.INVISIBLE);
        pr.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                pr.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
            }
        }, 1000);

        Singlejourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        returnJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);

            }
        });

        MPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PassActivity.class);
                startActivity(intent);

            }
        });

        Vticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ValueTicketActivity.class);
                startActivity(intent);


            }
        });

        myTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TicketSelActivity.class);
                startActivity(intent);


            }
        });
        myTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MyTripsActivity.class);
                startActivity(intent);


            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);


            }
        });

        lgOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressdialog = ProgressDialog.show(
                        MainActivity.this, "Please wait",
                        "Loading please wait..", true);
                progressdialog.show();
                progressdialog.setCancelable(true);


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    private void loadData() {
        int channelId=1;
        String token=AppPreferences.getAppPrefrences(VariablesConstant.TOKEN,this);

        String mobile=AppPreferences.getAppPrefrences(VariablesConstant.MOBILE,this);
//        MasterRequestPayload masterRequestPayload;
//        masterRequestPayload=new MasterRequestPayload("allStopMaster");
//
//        masterRequest =new MasterRequest(channelId,token,masterRequestPayload);

    }


}
