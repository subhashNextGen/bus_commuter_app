package com.example.buscustomerapplicationv2.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.ChangePassword;
import com.example.buscustomerapplicationv2.activity.MainActivity;
import com.example.buscustomerapplicationv2.activity.MyProfileActivity;
import com.example.buscustomerapplicationv2.activity.MyTripsActivity;
import com.example.buscustomerapplicationv2.activity.TicketSelActivity;
import com.example.buscustomerapplicationv2.activity.ValueTicketActivity;

@SuppressLint("Registered")
public class MenuActivity extends AppCompatActivity {

    Menu mMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mMenu = menu;


        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), 0);
            Log.e("app", "Activity name:" + info.name);
            if (info.name.equals("com.example.metroapplication.Activity.ValueTicketActivity")) {
//                menu.findItem(R.id.action_vt_ticket).setVisible(false);
            } else if (info.name.equals("com.example.metroapplication.Activity.ChangePassword")) {
                menu.findItem(R.id.action_ch_pass).setVisible(false);
            } else if (info.name.equals("com.example.metroapplication.Activity.MainActivity")) {
//                menu.findItem(R.id.action_home).setVisible(false);
            } else if (info.name.equals("com.example.metroapplication.Activity.MyTicketQR")) {
//                menu.findItem(R.id.action_myticket).setVisible(false);
            } else if (info.name.equals("com.example.metroapplication.Activity.MyProfileActivity")) {
                menu.findItem(R.id.action_myprofile).setVisible(false);
            } else if (info.name.equals("com.example.metroapplication.Activity.MyTripsActivity")) {
                menu.findItem(R.id.action_mytrip).setVisible(false);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent = null;
        switch (item.getItemId()) {
//            case R.id.action_home:
//                intent = new Intent(this, MainActivity.class);
//                this.startActivity(intent);
//
//
//                return true;
            case R.id.action_mytrip:

                intent = new Intent(this, MyTripsActivity.class);
                this.startActivity(intent);

                return true;
            case R.id.action_myprofile:

                intent = new Intent(this, MyProfileActivity.class);
                this.startActivity(intent);

                return true;
//            case R.id.action_myticket:
//
//                intent = new Intent(this, TicketSelActivity.class);
//                this.startActivity(intent);
//
//                return true;
            case R.id.action_ch_pass:

                intent = new Intent(this, ChangePassword.class);
                this.startActivity(intent);

                return true;
//            case R.id.action_vt_ticket:
//
//                intent = new Intent(this, ValueTicketActivity.class);
//                this.startActivity(intent);
//
//                return true;
//            case R.id.action_mypass:
//                intent=new Intent(this, MyPassQR.class);
//                this.startActivity(intent);
//                this.finish();
//                return true;

        }
        update();
        return true;
    }

    private void update() {
        mMenu.findItem(R.id.action_ch_pass).setVisible(true);
//        mMenu.findItem(R.id.action_home).setVisible(true);
//        mMenu.findItem(R.id.action_myticket).setVisible(true);
        mMenu.findItem(R.id.action_myprofile).setVisible(true);
        mMenu.findItem(R.id.action_mytrip).setVisible(true);
    }


}