package com.example.buscustomerapplicationv2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.adapter.MyTicketAdapter;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;

import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class SelectPassActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    ConnectionDetector cd;

    SpotsDialog progressDialog;

    ProgressBar progressBar;

    MyTicketAdapter adapter;
    List<Model_ListSjt_ResponsePayload> list;
    LinearLayout retryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pass);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Passes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}