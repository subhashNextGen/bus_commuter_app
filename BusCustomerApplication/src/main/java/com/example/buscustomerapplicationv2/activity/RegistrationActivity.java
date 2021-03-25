package com.example.buscustomerapplicationv2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.RegistrationController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.CustomTypeface;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.buscustomerapplicationv2.utils.PermissionManagerUtil.REQUEST_PERMISSION_PHONE_STATE;

public class RegistrationActivity extends Activity {
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText edtfirstName, edtlastName, edtmob, edtEmail, edtPassword, edtConfirmPassword, edtAddress, edtCity, edtState, edtPincode;
    Button btnRegistration;
    TextView txtAlreadyUser, cmpleteProfile, errorText;
    ConnectionDetector cd;
    PermissionManagerUtil p;
    String firstName, lastName, email, password;
    String mobileNo, address, city, state, pincode;

    SpotsDialog progressdialog;
    RegistrationController registrationController;

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

        setContentView(R.layout.activity_registration);
        p = new PermissionManagerUtil(this);


        btnRegistration = findViewById(R.id.registrationBtn);
        edtfirstName = findViewById(R.id.first_name);
        edtlastName = findViewById(R.id.last_name);
        edtmob = findViewById(R.id.mobile);
        edtConfirmPassword = findViewById(R.id.register_confirm_password);
        edtEmail = findViewById(R.id.register_email);
        edtPassword = findViewById(R.id.register_password);
        edtAddress = findViewById(R.id.register_address);
        edtCity = findViewById(R.id.register_city);
        edtState = findViewById(R.id.register_state);
        edtPincode = findViewById(R.id.register_pincode);
        txtAlreadyUser = findViewById(R.id.new_user_signup);
        cmpleteProfile = findViewById(R.id.complete_profile);
        errorText = findViewById(R.id.register_errors);
        cd = new ConnectionDetector(RegistrationActivity.this);

        CustomTypeface customTypeface = new CustomTypeface(this);
        cmpleteProfile.setTypeface(customTypeface.getTypeface());
        registrationController = new RegistrationController(this);

        txtAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvalidation()) {
                    progressdialog =new SpotsDialog(RegistrationActivity.this,R.style.CustomDialogue);
                    progressdialog.show();
                    p.showPhoneStatePermission();
                    loadData();
                    registrationController.registerUser(firstName, lastName, mobileNo, email, password, address, city, state, pincode);
                }
            }
        });
    }

    private void loadData() {
        firstName = edtfirstName.getText().toString();
        lastName = edtlastName.getText().toString();
        email = edtEmail.getText().toString();
        mobileNo = edtmob.getText().toString();
        password = edtPassword.getText().toString();
        address = edtAddress.getText().toString();
        city = edtCity.getText().toString();
        state = edtState.getText().toString();
        pincode = edtPincode.getText().toString();

    }

    public boolean isvalidation() {

        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher fname = ps.matcher(edtfirstName.getText().toString());
        Matcher lname = ps.matcher(edtlastName.getText().toString());
        boolean isfnameValid = fname.matches();
        boolean isLnameValid = lname.matches();

        if (edtfirstName.getText().toString().trim().length() == 0) {
            edtfirstName.setError("Please Enter First Name");
            edtfirstName.setFocusable(true);
            return false;
        } else if (!isfnameValid) {
            edtfirstName.setError("Please Enter Valid First Name");
            edtfirstName.setFocusable(true);
            return false;
        } else if (edtlastName.getText().toString().trim().length() == 0) {
            edtlastName.setError("Please Enter Last Name");
            edtlastName.setFocusable(true);
            return false;
        } else if (!isLnameValid) {
            edtlastName.setError("Please Enter Valid Last Name");
            edtlastName.setFocusable(true);
            return false;
        } else if (edtmob.length() == 0) {
            edtmob.setError("Please Enter Mobile Number");
            edtmob.setFocusable(true);
            return false;
        } else if (edtmob.length() < 10) {
            edtmob.setError("Please Enter valid Mobile Number");
            edtmob.setFocusable(true);
            return false;
        } else if (edtEmail.getText().toString().trim().length() == 0) {
            edtEmail.setError("Please Enter Email");
            edtEmail.setFocusable(true);
            return false;
        } else if (!edtEmail.getText().toString().matches(emailPattern)) {
            edtEmail.setError("Please Enter Valid Email");
            edtEmail.setFocusable(true);
            return false;
        } else if (edtPassword.getText().toString().trim().length() == 0) {
            edtPassword.setError("Please Enter Password");
            edtPassword.setFocusable(true);
            return false;
        } else if (edtConfirmPassword.length() == 0) {
            edtConfirmPassword.setError("Please Enter Confirm Password");
            edtConfirmPassword.setFocusable(true);
            return false;
        } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError("Confirm Password doesn't Match");
            edtConfirmPassword.setFocusable(true);
            return false;
        } else if (edtPincode.getText().toString().trim().length() < 6) {
            edtPincode.setError("Please enter correct pincode");
            edtPincode.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    public void onSuccess(String otp) {
        progressdialog.dismiss();
        Intent intent = new Intent(this, OtpActivity.class);
        intent.putExtra("otp",otp);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }

    public void showError(String s) {
        progressdialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_PHONE_STATE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistrationActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }


}
