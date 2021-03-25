package com.example.buscustomerapplicationv2.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.LoginController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import dmax.dialog.SpotsDialog;

import static com.example.buscustomerapplicationv2.utils.PermissionManagerUtil.REQUEST_PERMISSION_PHONE_STATE;

public class LoginActivity extends AppCompatActivity {

    Button mloginBtn;
    TextView forgotPwd, signUpLink;
    EditText mobileInput, passwordInput;
    CheckBox showHide;
    PermissionManagerUtil pm;
    ConnectionDetector cd;
    LoginController loginController;

    SpotsDialog progressDialog;

//    @Override       // for font style
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/caviar_dreams.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        setContentView(R.layout.activity_login);

        mloginBtn = (Button) findViewById(R.id.loginbtn);
        forgotPwd = (TextView) findViewById(R.id.forgot_pswd);
        signUpLink = (TextView) findViewById(R.id.new_user_signup);
        mobileInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        showHide = (CheckBox) findViewById(R.id.show_hide_checkbox);
        progressDialog = new SpotsDialog(this, R.style.CustomDialogue);

        pm = new PermissionManagerUtil(this);

        pm.showPhoneStatePermission();


        cd = new ConnectionDetector(LoginActivity.this);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isvalidation()) {
                    if (cd.isConnectingToInternet()) {
                        if (new PermissionManagerUtil(LoginActivity.this).checkPhoneStatePermission()) {
                        progressDialog.show();
                        loginController = new LoginController(LoginActivity.this);
                        loginController.onLogin(mobileInput.getText().toString().trim(), passwordInput.getText().toString().trim());

                    }}
                }
            }
        });
        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        showHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHide.setText("Show Password");
                } else {
                    passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHide.setText("Hide Password");
                }
            }
        });
    }

    public void onError(String s) {
        progressDialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }

    public void onSuccess() {
        progressDialog.dismiss();
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    public boolean isvalidation() {
        if (mobileInput.length() == 0) {
            mobileInput.setError("Please Enter the Mobile");
            return false;
        } else if (mobileInput.length() != 10) {
            mobileInput.setError("Please Enter the valid Mobile");
            return false;
        } else if (passwordInput.length() == 0) {
            passwordInput.setError("Please Enter the Password");
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_PHONE_STATE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(LoginActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

}
