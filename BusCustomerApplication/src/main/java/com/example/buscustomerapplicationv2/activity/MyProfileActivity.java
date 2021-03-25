package com.example.buscustomerapplicationv2.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyProfileActivity extends AppCompatActivity {

    Button SaveBtn;
    EditText etFname, etLname, etEmai, etMobile, etAddress1, etCity, etState, etPincode;

    TextView tvUpdateStatus;

    SpotsDialog spotsDialog;

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
        setContentView(R.layout.activity_my_profile);
        spotsDialog = new SpotsDialog(this, R.style.CustomDialogue);
        spotsDialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("My Profile");
        } else {
            getSupportActionBar().setTitle("My Profile");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etFname = findViewById(R.id.fname_my_profile);
        etLname = findViewById(R.id.lname_my_profile);
        etMobile = findViewById(R.id.mobile_my_profile);
        etEmai = findViewById(R.id.email_my_profile);
        etAddress1 = findViewById(R.id.address1_my_profile);
        etCity = findViewById(R.id.city_my_profile);
        etState = findViewById(R.id.state_my_profile);
        etPincode = findViewById(R.id.pin_my_profile);
        tvUpdateStatus = findViewById(R.id.textViewStatus_My_prifile);


        SaveBtn = findViewById(R.id.save_btn_my_profile);

        etMobile.setEnabled(false);

        etFname.setText(AppPreferences.getAppPrefrences(VariablesConstant.F_NAME, this));
        etLname.setText(AppPreferences.getAppPrefrences(VariablesConstant.L_NAME, this));
        etMobile.setText(AppPreferences.getAppPrefrences(VariablesConstant.MOBILE, this));
        etEmai.setText(AppPreferences.getAppPrefrences(VariablesConstant.EMAIL, this));
        etAddress1.setText(AppPreferences.getAppPrefrences(VariablesConstant.ADDRESS, this));
        etCity.setText(AppPreferences.getAppPrefrences(VariablesConstant.CITY, this));
        etState.setText(AppPreferences.getAppPrefrences(VariablesConstant.STATE, this));
        etPincode.setText(AppPreferences.getAppPrefrences(VariablesConstant.PINCODE, this));


        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });


        spotsDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//    public boolean isvalidation() {
//
//        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
//        Matcher fname = ps.matcher(etFname.getText().toString());
//        Matcher lname = ps.matcher(etLname.getText().toString());
//        boolean isfnameValid = fname.matches();
//        boolean isLnameValid = lname.matches();
//
//        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        if (etFname.getText().toString().trim().length() == 0) {
//            etFname.setError("Please Enter First Name");
//            return false;
//        } else if (!isfnameValid) {
//            etFname.setError("Please Enter Valid First Name");
//            return false;
//        } else if (etLname.getText().toString().trim().length() == 0) {
//            etLname.setError("Please Enter Last Name");
//            return false;
//        } else if (!isLnameValid) {
//            etLname.setError("Please Enter Valid Last Name");
//            return false;
//        } else if (etMobile.length() == 0) {
//            etMobile.setError("Please Enter Mobile Number");
//            return false;
//        } else if (etMobile.length() < 10) {
//            etMobile.setError("Please Enter valid Mobile Number");
//            return false;
//        } else if (etEmai.getText().toString().trim().length() == 0) {
//            etEmai.setError("Please Enter Email");
//            return false;
//        } else if (!etEmai.getText().toString().matches(emailPattern)) {
//            etEmai.setError("Please Enter Valid Email");
//            return false;
//        }
//
//        return true;
//    }
}
