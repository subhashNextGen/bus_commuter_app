package com.example.buscustomerapplicationv2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.ValueTicketPreviewController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.models.Model_WalletBalanceResponse;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge_Response;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ValueTicketPreviewActivity extends AppCompatActivity {

    Button  PaynowBtn;

    ProgressDialog progressdialog;


    ValueTicketPreviewController valueTicketPreviewController;
    Model_WalletBalanceResponse model_walletBalanceResponse;
    float value;
    ConnectionDetector cd;

    TextView preview, previewAmount, preiveTicktType;
    private final String TAG = ValueTicketPreviewActivity.class.getName();

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
        setContentView(R.layout.activity_value_ticket_preview);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Preview");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        PaynowBtn = findViewById(R.id.pay_now_btn_vt_preview);

        preview = findViewById(R.id.preive_preview);
        preiveTicktType = findViewById(R.id.value_ticket_vt_preview);
        previewAmount = findViewById(R.id.total_amount_vt_preview);
        cd = new ConnectionDetector(this);
        valueTicketPreviewController = new ValueTicketPreviewController(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/CaviarDreams_Bold.ttf");
        preview.setTypeface(typeface);

        value = getIntent().getFloatExtra("amount", 0.00f);
        model_walletBalanceResponse = (Model_WalletBalanceResponse) getIntent().getSerializableExtra("data");

        previewAmount.setText(String.format("Rs %s", value));
        preiveTicktType.setText("Value Ticket");


        PaynowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value > 0) {
                    if (cd.isConnectingToInternet()) {
                        progressdialog = ProgressDialog.show(
                                ValueTicketPreviewActivity.this, "Please wait",
                                "Loading please wait..", true);
                        progressdialog.show();
                        progressdialog.setCancelable(true);
                        valueTicketPreviewController.rechargeValue(String.valueOf(value), model_walletBalanceResponse.getPayload().getBackendKey_wallet());

                    } else {
                        Toast.makeText(ValueTicketPreviewActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void onSuccess(Model_WalletRecharge_Response model_walletRecharge_response) {
        progressdialog.dismiss();
        Intent intent = new Intent(this, PostPaymentActivity.class);
        intent.putExtra("data", model_walletRecharge_response);
        intent.putExtra("flag", 2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onError(String s) {
        progressdialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
