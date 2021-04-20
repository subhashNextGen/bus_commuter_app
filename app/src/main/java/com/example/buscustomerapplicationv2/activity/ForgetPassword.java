package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.ForgetPasswordController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPassword extends AppCompatActivity {

    Button submit, verify, confirm;
    LinearLayout mobileLayout, otpLayout, passLayout;
    EditText etMobile, otpTV, newPasET, confirmPassET;
    TextView errorTV;
    String mobile;

    ConnectionDetector cd;


    ForgetPasswordController forgetPasswordController;


    SpotsDialog progressdialog;


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
        setContentView(R.layout.activity_forget_password);
        progressdialog = new SpotsDialog(this,R.style.CustomDialogue);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Forget Password");
        } else {
            getSupportActionBar().setTitle("Forget Password");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        submit = findViewById(R.id.submit_forget_password);
        verify = findViewById(R.id.verify_forget_password);
        confirm = findViewById(R.id.confirm_forget_password_btn);
        mobileLayout = findViewById(R.id.mobile_layout);
        otpLayout = findViewById(R.id.otp_layout);
        passLayout = findViewById(R.id.password_layout);
        etMobile = findViewById(R.id.mobile_forget_password);
        errorTV = findViewById(R.id.textView_forget_pasword);
        otpTV = findViewById(R.id.otp_forget_password);
        newPasET = findViewById(R.id.new_pass_forget_password);
        confirmPassET = findViewById(R.id.confirm_pass_forget_password);

        errorTV.setVisibility(View.INVISIBLE);
        cd = new ConnectionDetector(this);

        forgetPasswordController = new ForgetPasswordController(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    if (!etMobile.getText().toString().equals("") && etMobile.getText().toString().length() == 10) {
                        mobile = etMobile.getText().toString().trim();
                        progressdialog.show();
                        forgetPasswordController.confirmMobile(mobile);
                    } else {
                        CustomDailogue.showDailogue(ForgetPassword.this, "Please provide correct mobile number");
                    }
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    if (otpTV.getText().toString().trim().length() == 6) {
                        progressdialog.show();
                        forgetPasswordController.confirmOtp(mobile, otpTV.getText().toString().trim());
                    } else {
                        CustomDailogue.showDailogue(ForgetPassword.this, "Please provide correct OTP");
                    }
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!newPasET.getText().toString().equals("") && !confirmPassET.getText().toString().equals("")) {
                    if (newPasET.getText().toString().trim().equals(confirmPassET.getText().toString().trim())) {
                        if (cd.isConnectingToInternet()) {
                            progressdialog.show();
                            forgetPasswordController.changePassword(mobile, newPasET.getText().toString().trim());
                        }
                    }
                }
            }
        });

    }

    public void onError(String s) {
        progressdialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }

    public void showOtpScreen(String s) {
        progressdialog.dismiss();
        mobileLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("OTP");
        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.getString(android.R.string.ok).toUpperCase(),
                (dialog1, buttonId) -> dialog1.dismiss());
        dialog.show();

    }

    public void showPassLayout() {
        progressdialog.dismiss();
        mobileLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.GONE);
        passLayout.setVisibility(View.VISIBLE);
    }

    public void onSuccess() {
        progressdialog.dismiss();
        Toast.makeText(this, "Password changed", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}