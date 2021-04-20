package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;

import com.example.buscustomerapplicationv2.controller.ChangePasswordController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;


import java.util.Objects;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePassword extends AppCompatActivity {

    Button submit;
    EditText edtPassword, edtConfirmPassword;

    SpotsDialog spotsDialog;
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
        setContentView(R.layout.activity_change_password);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Change Password");
        } else {
            getSupportActionBar().setTitle("Change Password");

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        submit = findViewById(R.id.submit_chnPasswordBtn);
        edtPassword = findViewById(R.id.new_pass_chp);
        edtConfirmPassword = findViewById(R.id.confirm_pass_chp);
        spotsDialog=new SpotsDialog(this,R.style.CustomDialogue);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
                spotsDialog.show();

                ChangePasswordController changePasswordController=new ChangePasswordController(ChangePassword.this);
                changePasswordController.changePassword(edtPassword.getText().toString());}
                else{
                    Toast.makeText(ChangePassword.this, "Password not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //onCreate Close

    public void onSuccess(){
        spotsDialog.dismiss();
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Succes");
        dialog.setMessage("Password changed successfully");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.getString(android.R.string.ok).toUpperCase(),
                (dialog1, buttonId) -> dialog1.dismiss());
        dialog.show();

    }

    public void onError(String s){
        CustomDailogue.showDailogue(this,s);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



//    public boolean isvalidation() {
//
//        if (edtPassword.getText().toString().trim().length() == 0) {
//            edtPassword.setError("Please Enter Password");
//            return false;
//        } else if (edtConfirmPassword.length() == 0) {
//            edtConfirmPassword.setError("Please Enter Confirm Password");
//            return false;
//        } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
//            edtConfirmPassword.setError("Confirm Password doesn't Match");
////            edtConfirmPassword.setText("");
//            return false;
//        } else if (edtCurrectpasswrd.length() == 0) {
//            edtCurrectpasswrd.setError("Please Enter Current Password");
//
//            return false;
//        }
//        return true;
//    }



}//class close
