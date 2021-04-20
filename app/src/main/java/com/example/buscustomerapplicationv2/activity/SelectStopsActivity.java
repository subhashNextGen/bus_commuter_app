package com.example.buscustomerapplicationv2.activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.controller.SelectStopsController;
import com.example.buscustomerapplicationv2.fragment.FromFragment;
import com.example.buscustomerapplicationv2.fragment.ToFragment;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;

import java.util.ArrayList;
import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class SelectStopsActivity extends AppCompatActivity {

    SpotsDialog progressDialog;

    SelectStopsController selectStopsController;
String fromSrc;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stops);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    } else
                        finish();
                }
            });
        }
        fromSrc=getIntent().getStringExtra("from");
        progressDialog = new SpotsDialog(this,R.style.CustomDialogue);

        progressDialog.show();



        selectStopsController = new SelectStopsController(this);
        selectStopsController.getStops();

    }

    public void init(ArrayList<String> arrayList) {

        Fragment fragment;
        Bundle bundle = new Bundle();

        if (getIntent().getStringExtra("from")==null) {
            fragment = new FromFragment();
        }else
        {
            fragment=new ToFragment();
            int position=arrayList.indexOf(fromSrc);
            bundle.putString("value",getIntent().getStringExtra("from"));
        }
        bundle.putStringArrayList("list",arrayList);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container_view_tag, fragment);
        ft.commit();
        progressDialog.dismiss();
    }

    public void showError(String s)
    {
        progressDialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }
}