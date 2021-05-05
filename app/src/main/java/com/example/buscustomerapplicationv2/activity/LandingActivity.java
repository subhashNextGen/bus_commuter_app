package com.example.buscustomerapplicationv2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView cv1, cv2, cv3, cv4;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    PermissionManagerUtil permissionManagerUtil;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user, this) == null) {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_landing);

        cd = new ConnectionDetector(this);
        cv1 = findViewById(R.id.card_view_1);
        cv2 = findViewById(R.id.card_view_2);
        cv3 = findViewById(R.id.card_view_3);
        cv4=findViewById(R.id.card_view_4);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_landing_view);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.nav_drawer_open,
                R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    startActivity(new Intent(LandingActivity.this, SelectStopsActivity.class));
                } else
                    onError();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    startActivity(new Intent(LandingActivity.this, ValueTicketActivity.class));
                } else
                    onError();

            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    startActivity(new Intent(LandingActivity.this, TicketSelActivity.class));
                } else
                    onError();
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet()) {
                    startActivity(new Intent(LandingActivity.this, SelectPassActivity.class));
                } else
                    onError();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (cd.isConnectingToInternet()) {
            switch (item.getItemId()) {
                case R.id.action_mytrip:
                    //Need to update
//                Toast.makeText(this, "Need to update", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(LandingActivity.this, MyTripsActivity.class));
                    break;
                case R.id.action_value_pass_history:
                    //Need to Update
                    Toast.makeText(this, "Need to update", Toast.LENGTH_LONG).show();
                    break;
                case R.id.action_myprofile:
                    startActivity(new Intent(LandingActivity.this, MyProfileActivity.class));
                    break;
                case R.id.action_ch_pass:
                    startActivity(new Intent(LandingActivity.this, ChangePassword.class));
                    break;
                case R.id.action_logout:
                    logoutScreen();
                    break;
            }
        } else
            onError();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    private void logoutScreen() {
        AppPreferences.clearAllPreferences();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onError() {
        CustomDailogue.showDailogue(this, "No Internet Connection");
    }
}