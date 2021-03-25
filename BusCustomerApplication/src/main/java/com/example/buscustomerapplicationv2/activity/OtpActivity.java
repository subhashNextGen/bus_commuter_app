package com.example.buscustomerapplicationv2.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;

import com.example.buscustomerapplicationv2.controller.OtpController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;

import dmax.dialog.SpotsDialog;

public class OtpActivity extends AppCompatActivity {

    Button back, verify;
    EditText editText;
   SpotsDialog progressBar;

    String oTP;

    OtpController otpController;

//    @Override       // for font style
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/caviar_dreams.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        setContentView(R.layout.activity_otp);

        back = findViewById(R.id.backBtn);
        verify = findViewById(R.id.verifyBtn);

        oTP = getIntent().getStringExtra("otp");

        editText = findViewById(R.id.otp_ev);
//        Toast.makeText(this, "OTP : "+oTP, Toast.LENGTH_LONG).show();

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("OTP : "+oTP);
        dialog.setTitle("OTP Box");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"OTP : "+oTP,Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
progressBar=new SpotsDialog(OtpActivity.this, R.style.CustomDialogue);
progressBar.show();
                otpController=new OtpController(OtpActivity.this);
                otpController.onVerifyOtp(AppPreferences.getAppPrefrences(VariablesConstant.MOBILE,OtpActivity.this),editText.getText().toString().trim());

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onSuccess(){
        progressBar.dismiss();
        startActivity(new Intent(this,LoginActivity.class));
        Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onError(String s){
        CustomDailogue.showDailogue(this,s);
    }

}
