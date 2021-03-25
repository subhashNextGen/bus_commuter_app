package com.example.buscustomerapplicationv2.activity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.models.Model_BookSjt_Response;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge_Response;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.utils.QRGenerator;
import com.subhasha.mylibraryencdec.AESUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class PostPaymentActivity extends AppCompatActivity {

    String encryptedQR, qrText;

    ImageView imageView;
    ProgressBar pb;

    SpotsDialog spotsDialog;
    LinearLayout validityLL;

    MYdb mYdb;

    TextView tvFrom, tvTo, tvAmountPaid, tvDate, tvTime, tvValid, tvPmtId, tvTcktId, tvTrnscId;

    LinearLayout llFrom, llTo;

    TextView yourPay, yourText;

    int flag;

    Model_BookSjt_Response model_bookSjt_response;

    QRGenerator qr;

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
        setContentView(R.layout.activity_post_payment);
        spotsDialog = new SpotsDialog(this, R.style.CustomDialogue);
        spotsDialog.show();

        mYdb = new MYdb(this);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvFrom = findViewById(R.id.from_post_payment);
        tvTo = findViewById(R.id.to_post_payment);
        tvAmountPaid = findViewById(R.id.amount_paid_post_payment);
        tvDate = findViewById(R.id.date_post_payment);
        tvTime = findViewById(R.id.time_post_payment);
        tvValid = findViewById(R.id.valid_post_payment);
        tvPmtId = findViewById(R.id.payment_id_post_payment);
        tvTcktId = findViewById(R.id.ticket_id_post_payment);
//        tvTrnscId=findViewById(R.id.transaction_id_post_payment);
        llFrom = findViewById(R.id.fromlinear_post_payment);
        llTo = findViewById(R.id.tolinear_post_payment);

        validityLL = findViewById(R.id.validity_layout);


        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {
            model_bookSjt_response = (Model_BookSjt_Response) getIntent().getSerializableExtra("data");
            qrText = model_bookSjt_response.getPayload().getSjt_qrcode() + "-T-SJT";
            tvFrom.setText(model_bookSjt_response.getPayload().getSrc_stop_textual_Identifier());
            tvTo.setText(model_bookSjt_response.getPayload().getDest_stop_textual_Identifier());
            tvAmountPaid.setText(model_bookSjt_response.getPayload().getFare());
            String dateTime = model_bookSjt_response.getPayload().getCreated_on();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            Date date = null;
            try {
                date = dt.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt2 = new SimpleDateFormat("dd-MMM-yyyy");
            String date1 = dt2.format(date);
            String time = dt1.format(date);
            tvDate.setText(date1);
            tvTime.setText(time);
            tvValid.setText(model_bookSjt_response.getPayload().getSjt_expiring_on());

        } else if (flag == 2) {
            Model_WalletRecharge_Response model_walletRecharge_response = (Model_WalletRecharge_Response) getIntent().getSerializableExtra("data");
            // issueValueQRResponse= (IssueValueQRResponse) getIntent().getSerializableExtra("data");
            llFrom.setVisibility(View.GONE);
            llTo.setVisibility(View.GONE);
            qrText = model_walletRecharge_response.getPayload().getWallet_qr_code() + "-T-ValueTicket";
            tvAmountPaid.setText(model_walletRecharge_response.getPayload().getRecharge_value());
            String dateTime = String.valueOf(model_walletRecharge_response.getPayload().getLast_updated_on());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            Date date = null;
            try {
                date = dt.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = dt2.format(date);
            String time = dt1.format(date);
            tvDate.setText(date1);
            tvTime.setText(time);
            validityLL.setVisibility(View.GONE);
            tvPmtId.setText(model_walletRecharge_response.getPayload().getBackendKey_rechargeTxn());
            tvTcktId.setText(model_walletRecharge_response.getPayload().getBackendKey_rechargeTxn());
        }
//
        imageView = findViewById(R.id.imageview_ticket_qr);
        pb = findViewById(R.id.progress_post_payment);
        qr = new QRGenerator();
        try {
            encryptedQR = AESUtil.encryptValue(qrText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        yourPay = findViewById(R.id.yourPayment);
        yourText = findViewById(R.id.yourText);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/CaviarDreams_Bold.ttf");
        yourPay.setTypeface(typeface);
        yourText.setTypeface(typeface);

        qr.createQR(encryptedQR, imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                pb.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                spotsDialog.dismiss();
            }
        }, 1000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
