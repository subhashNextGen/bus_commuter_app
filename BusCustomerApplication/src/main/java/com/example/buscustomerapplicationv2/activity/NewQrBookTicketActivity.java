package com.example.buscustomerapplicationv2.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.FontRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.NewQrBookTicketController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.models.Model_BookSjt_Response;
import com.example.buscustomerapplicationv2.utils.CustomTypeface;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class NewQrBookTicketActivity extends AppCompatActivity {

    ImageView change;
    TextView srcTv, destTv, singleTv, returnTv;
    Button proceedBtn;
    String from, to;
    float value;
    CustomTypeface ct;
    RadioButton singleRadio, returnRadio;
    LinearLayout returnLayout;

    String singleValue, returnValue;
    boolean rjtBooked;
    CardView cv1, cv2;
    SpotsDialog progressDialog;
    RadioGroupPlus radioGroupPlus;

    NewQrBookTicketController newQrBookTicketController;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_qr_book_ticket);
        singleRadio=findViewById(R.id.radio_single);
        returnRadio=findViewById(R.id.radio_return);
        returnLayout=findViewById(R.id.retur_radio_ll);
        ct=new CustomTypeface(this);
        progressDialog = new SpotsDialog(this, R.style.CustomDialogue);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("QR Ticket");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.title);
        mTitle.setText(toolbar.getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
       // mTitle.setTypeface(ct.getTypeface());
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog.show();
        change = findViewById(R.id.change_btn);
        radioGroupPlus = findViewById(R.id.radioGroup);
        srcTv = findViewById(R.id.src);
        destTv = findViewById(R.id.dest);
        singleTv = findViewById(R.id.single_price);
        returnTv = findViewById(R.id.return_price);
        proceedBtn = findViewById(R.id.proceed_pay);
        cv1 = findViewById(R.id.top_cardview);
        cv2 = findViewById(R.id.bottom_card);

        findViewById(R.id.radio_single);
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        srcTv.setText(from);
        destTv.setText(to);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = srcTv.getText().toString().trim();
                srcTv.setText(destTv.getText().toString().trim());
                destTv.setText(temp);
            }
        });
        newQrBookTicketController = new NewQrBookTicketController(this);
        newQrBookTicketController.getFare(srcTv.getText().toString(), destTv.getText().toString());
        radioGroupPlus.check(R.id.radio_single);
        singleRadio.setTypeface(ct.getTypeface());

        radioGroupPlus.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @SuppressLint({"WrongConstant", "NonConstantResourceId"})
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

                switch (checkedId) {

                    case R.id.radio_single:
                        returnRadio.setTypeface(Typeface.createFromAsset(getAssets(), "font/caviar_dreams.ttf"));
                       singleRadio.setTypeface(ct.getTypeface());
                        rjtBooked = false;
                        updatePayBtn(singleValue);
                        break;
                    case R.id.radio_return:
                        singleRadio.setTypeface(Typeface.createFromAsset(getAssets(), "font/caviar_dreams.ttf"));
                        returnRadio.setTypeface(ct.getTypeface());
                        rjtBooked = true;
                        updatePayBtn(returnValue);
                        break;
                }

            }
        });

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewQrBookTicketActivity.this, SelectStopsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewQrBookTicketActivity.this, SelectStopsActivity.class);
                intent.putExtra("from", srcTv.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                newQrBookTicketController.bookTicket(srcTv.getText().toString(), destTv.getText().toString(), rjtBooked, value);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    public void updateFare(String singlefare, String returnFare) {
        progressDialog.dismiss();
        String text = getResources().getString(R.string.Rs) + " ";
        String perPerson = " price/Person";
        singleValue = singlefare;
        returnValue = returnFare;
        singleTv.setText(text + singlefare + perPerson);
        returnTv.setText(text + returnFare + perPerson);
        updatePayBtn(singlefare);
        if (Float.parseFloat(returnFare) == 0){
            returnLayout.setVisibility(View.INVISIBLE);
    }

    }

    @SuppressLint("SetTextI18n")
    public void updatePayBtn(String fare) {
        proceedBtn.setText("Proceed to Pay " + getResources().getString(R.string.Rs) + " " + fare);
        value = Float.parseFloat(fare);

    }

    public void onSuccessBook(Model_BookSjt_Response response) {
        progressDialog.dismiss();
        Intent intent = new Intent(this, PostPaymentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", response);
        intent.putExtra("flag", 1);
        startActivity(intent);
        finish();
    }

//    public void noDirectBus(){
//
//    }

    public void onError(String s) {
        if (progressDialog.isShowing())
        progressDialog.dismiss();
        CustomDailogue.showDailogue(this, s);
        radioGroupPlus.setEnabled(false);
        singleRadio.setEnabled(false);
        returnRadio.setEnabled(false);
        proceedBtn.setEnabled(false);

    }

}