package com.example.buscustomerapplicationv2.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.utils.MenuActivity;
import com.example.buscustomerapplicationv2.utils.QRGenerator;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends MenuActivity {

    ProgressBar pb;
    Toast toast;
    ImageView image;
    QRGenerator qr;
    String qrText;

    @Override       // for font style
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/caviar_dreams.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Trip Details");
        } else {
            getSupportActionBar().setTitle("Trip Details");
        }
        pb = findViewById(R.id.progress_detail);
        image = findViewById(R.id.imageview_detail);

        qrText = "";
        qr = new QRGenerator();

        if (qrText == null || "".equals(qrText) || qrText.length() < 1) {
            toast.setText("Input in Empty");
            toast.show();
            return;
        } else {
            qr.createQR(qrText, image);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                pb.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
