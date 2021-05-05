package com.example.buscustomerapplicationv2.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.utils.CustomTypeface;
import com.example.buscustomerapplicationv2.utils.QRGenerator;
import com.example.buscustomerapplicationv2.utils.TicketType;
import com.example.buscustomerapplicationv2.utils.types;
import com.subhasha.mylibraryencdec.AESUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyTicketQR extends AppCompatActivity {

    String encryptedQRSjt, encryptedQRRjt;

    QRGenerator qr;
    ImageView imageViewtSjt, imageViewtRjt;
    ProgressBar pb, pb1;
    TextView scanQRSjt, scanQRRjt;
    LinearLayout rjtLayout;
    RelativeLayout qrRjtlayout;
    TextView tkttypetv, tktSrctv, tktDesttv, tktfaretv, tktValidtv;
    TextView sjtText, rjtText;
    TextView status;
    SpotsDialog spotsDialog;
    MYdb db;

    Model_ListSjt_ResponsePayload ticket;

//    @Override       // for font style
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/caviar_dreams.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        setContentView(R.layout.activity_my_ticket_q_r);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Ticket");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        spotsDialog = new SpotsDialog(this, R.style.CustomDialogue);
        spotsDialog.show();
        db = new MYdb(this);
        ticket = (Model_ListSjt_ResponsePayload) getIntent().getSerializableExtra("data");

        imageViewtSjt = findViewById(R.id.imageview_ticket_qr_sjt);
        imageViewtRjt = findViewById(R.id.imageview_ticket_qr_rjt);
        pb = findViewById(R.id.progress_my_ticket);
        pb1 = findViewById(R.id.progress_my_ticket_rjt);
        scanQRSjt = findViewById(R.id.scanQr);
        scanQRRjt = findViewById(R.id.scanQr1);
        tkttypetv = findViewById(R.id.ticket_type);
        tktfaretv = findViewById(R.id.fare_rev);
        tktSrctv = findViewById(R.id.source_rev);
        tktDesttv = findViewById(R.id.destination_rev);
        tktValidtv = findViewById(R.id.valid_till);
        rjtLayout = findViewById(R.id.rjttextlayout);
        qrRjtlayout = findViewById(R.id.rjt_qr_layout);
        sjtText = findViewById(R.id.sjt_scan);
        rjtText = findViewById(R.id.rjt_scan);
        status=findViewById(R.id.status);

        if (ticket.isRjt_booked()) {
            rjtLayout.setVisibility(View.VISIBLE);
            qrRjtlayout.setVisibility(View.VISIBLE);
        }


status.setText(getIntent().getStringExtra("status"));
        sjtText.setTypeface(new CustomTypeface(this).getTypeface());
        rjtText.setTypeface(new CustomTypeface(this).getTypeface());
        scanQRSjt.setTypeface(new CustomTypeface(this).getTypeface());
        scanQRRjt.setTypeface(new CustomTypeface(this).getTypeface());

        String qrText = ticket.getSjt_qrcode() + TicketType.tTypes(types.SJT);
        String qrTextrjt = ticket.getRjt_qrcode() + TicketType.tTypes(types.RJT);
        try {
            encryptedQRSjt = AESUtil.encryptValue(qrText);
            encryptedQRRjt = AESUtil.encryptValue(qrTextrjt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        qr = new QRGenerator();

        if (ticket.isRjt_booked()) {
            tkttypetv.setText("RJT");
        } else tkttypetv.setText("SJT");

        tktSrctv.setText(ticket.getSrc_stop_textual_Identifier());
        tktDesttv.setText(ticket.getDest_stop_textual_Identifier());
        tktValidtv.setText(ticket.getSjt_expiring_on());
        tktfaretv.setText(ticket.getFare());
        sjtText.setText(ticket.getSrc_stop_textual_Identifier()+" to "+ticket.getDest_stop_textual_Identifier());
        rjtText.setText(ticket.getDest_stop_textual_Identifier()+" to "+ticket.getSrc_stop_textual_Identifier());


        qr.createQR(encryptedQRSjt, imageViewtSjt);
        qr.createQR(encryptedQRRjt, imageViewtRjt);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                pb.setVisibility(View.GONE);
                pb1.setVisibility(View.GONE);

                imageViewtSjt.setVisibility(View.VISIBLE);
                imageViewtRjt.setVisibility(View.VISIBLE);
            }
        }, 1000);

        spotsDialog.dismiss();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
