package com.example.buscustomerapplicationv2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.models.Model_WalletBalanceResponse;
import com.example.buscustomerapplicationv2.utils.QRGenerator;
import com.example.buscustomerapplicationv2.utils.TicketType;
import com.example.buscustomerapplicationv2.utils.types;

public class ValueTicketQr extends AppCompatActivity {
    ImageView imageViewQr;
    TextView balanceText, lastUpdateText;
    ProgressBar progressBar;
    Model_WalletBalanceResponse model_walletBalanceResponse;
    QRGenerator qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_ticket_qr);

        imageViewQr = findViewById(R.id.imageview_value_qr);
        progressBar = findViewById(R.id.progress_my_ticket);
        balanceText = findViewById(R.id.balance);
        lastUpdateText = findViewById(R.id.last_update);
        model_walletBalanceResponse = (Model_WalletBalanceResponse) getIntent().getSerializableExtra("data");
        qr = new QRGenerator();

        String qrString=model_walletBalanceResponse.getPayload().getWallet_qr_code() +  TicketType.tTypes(types.ValueTicket);;


        qr.createQR(qrString,imageViewQr);

        balanceText.setText(model_walletBalanceResponse.getPayload().getWallet_balance());
        lastUpdateText.setText(model_walletBalanceResponse.getPayload().getLast_updated_on());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                progressBar.setVisibility(View.GONE);
                imageViewQr.setVisibility(View.VISIBLE);
            }
        }, 1000);


    }
}