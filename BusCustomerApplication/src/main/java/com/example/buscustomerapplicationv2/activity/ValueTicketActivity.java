package com.example.buscustomerapplicationv2.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.ValueTicketController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.models.Model_WalletBalanceResponse;
import com.example.buscustomerapplicationv2.utils.MenuActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ValueTicketActivity extends AppCompatActivity {

    Button PaynowBtn, btn1, btn2, btn3, btn4;
    TextView availableValueTV;
    SpotsDialog progressDialog;
    TextView textView;
    float number = 0.00f;
    CardView cardView;
    Model_WalletBalanceResponse model_walletBalanceResponse;
ValueTicketController valueTicketController;


//    @Override       // for font style
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
    
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/caviar_dreams.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        setContentView(R.layout.activity_value_ticket);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Value Ticket");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PaynowBtn = findViewById(R.id.pay_now_btn_vt);
        btn1 = findViewById(R.id.btn_1_vt);
        btn2 = findViewById(R.id.btn_2_vt);
        btn3 = findViewById(R.id.btn_3_vt);
        btn4 = findViewById(R.id.btn_4_vt);
        availableValueTV = findViewById(R.id.available_bal);
        textView = findViewById(R.id.total_amount_vt);
        cardView=findViewById(R.id.card_view_value);

        valueTicketController=new ValueTicketController(this);
        progressDialog=new SpotsDialog(this,R.style.CustomDialogue);
        progressDialog.show();
        valueTicketController.getBal();

        btn1.setOnClickListener(v -> {

            number = number + Float.parseFloat(btn1.getText().toString());
            textView.setText(String.format("Rs %.2f", number));
        });
        btn2.setOnClickListener(v -> {
            number = number + Float.parseFloat(btn2.getText().toString());
            textView.setText(String.format("Rs %.2f", number));
        });

        btn3.setOnClickListener(v -> {
            number = number + Float.parseFloat(btn3.getText().toString());
            textView.setText(String.format("Rs %.2f", number));
        });
        btn4.setOnClickListener(v -> {
            number = number + Float.parseFloat(btn4.getText().toString());
            textView.setText(String.format("Rs %.2f", number));
        });


        PaynowBtn.setOnClickListener(v -> {
            if (Float.parseFloat(textView.getText().toString().substring(3)) > 0) {
                Intent intent = new Intent(ValueTicketActivity.this, ValueTicketPreviewActivity.class);
                intent.putExtra("data",model_walletBalanceResponse);
                intent.putExtra("amount", Float.parseFloat(textView.getText().toString().substring(3)));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ValueTicketActivity.this, "Amount cant be 0 ", Toast.LENGTH_SHORT).show();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ValueTicketActivity.this,ValueTicketQr.class);
                intent.putExtra("data",model_walletBalanceResponse);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void onSucces(Model_WalletBalanceResponse model_walletBalanceResponse){
        progressDialog.dismiss();
        this.model_walletBalanceResponse=model_walletBalanceResponse;
        availableValueTV.setText(getResources().getString(R.string.Rs)+" "+model_walletBalanceResponse.getPayload().getWallet_balance());
    }

    public void onError(String s){

        progressDialog.dismiss();
        CustomDailogue.showDailogue(this,s);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    }
