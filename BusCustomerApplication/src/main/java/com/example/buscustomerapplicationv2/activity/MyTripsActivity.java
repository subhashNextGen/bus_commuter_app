package com.example.buscustomerapplicationv2.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.adapter.MyTripsAdapter;
import com.example.buscustomerapplicationv2.controller.MyTripsController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;



public class MyTripsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ConnectionDetector cd;

    SpotsDialog progressDialog;

    ProgressBar progressBar;

    MyTripsAdapter adapter;
    List<Model_ListSjt_ResponsePayload> list;

    MyTripsController controller;

    int visibleItemCount;
    int totalItemCount;
    int pastVisibleItems;
    boolean loading = true;
    int pageNumber = 1;

    TextView noRecords;


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
        setContentView(R.layout.activity_my_trips);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Trip List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_sel_ticket);
        progressBar = findViewById(R.id.bottom_progress_bar);
        noRecords = findViewById(R.id.no_record);
        cd = new ConnectionDetector(this);

        list = new ArrayList<>();

        adapter = new MyTripsAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyTripsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        controller = new MyTripsController(this);
        controller.getLists(pageNumber);

        progressDialog = new SpotsDialog(this, R.style.CustomDialogue);
        progressDialog.show();

        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0) //check for scroll down
                        {
                            visibleItemCount = linearLayoutManager.getChildCount();
                            totalItemCount = linearLayoutManager.getItemCount();
                            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    loading = false;
                                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                                        Log.e("...", "Last Item Wow !");
                                        ++pageNumber;
//                                        Toast.makeText(TicketSelActivity.this, "Reached Last", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        controller.getLists(pageNumber);
                                    } else loading = true;

                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    }
                }
        );

    }

    public void onSuccess(List<Model_ListSjt_ResponsePayload> list) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        else
            progressBar.setVisibility(View.GONE);
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
        loading = true;
    }

    public void setLoading() {
        progressBar.setVisibility(View.GONE);
        loading = false;
    }

    public void onError(String s) {
        progressDialog.dismiss();
        CustomDailogue.showDailogue(this, s);
    }

    public void onNorecord() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        recyclerView.setVisibility(View.GONE);
        noRecords.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
