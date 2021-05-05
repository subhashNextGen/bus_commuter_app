package com.example.buscustomerapplicationv2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.adapter.MyTicketAdapter;
import com.example.buscustomerapplicationv2.controller.TicketSelectController;
import com.example.buscustomerapplicationv2.helper.CustomDailogue;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketSelActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ConnectionDetector cd;

    SpotsDialog progressDialog;

    ProgressBar progressBar;

    MyTicketAdapter adapter;
    List<Model_ListSjt_ResponsePayload> list;
    LinearLayout retryLayout;

    TicketSelectController controller;

    int visibleItemCount;
    int totalItemCount;
    int pastVisibleItems;
    boolean loading = true;
    int pageNumber = 0;



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
        setContentView(R.layout.activity_ticket_sel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Ticket List");
        } else {
            getSupportActionBar().setTitle("Ticket List");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_sel_ticket);
        progressBar = findViewById(R.id.bottom_progress_bar);
        retryLayout=findViewById(R.id.retry_ll);
        cd = new ConnectionDetector(this);

        list = new ArrayList<>();

        adapter = new MyTicketAdapter(getApplicationContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TicketSelActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        adapter.setOnMyClickListener(new MyTicketAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Model_ListSjt_ResponsePayload ticket = list.get(position);
                Intent intent = new Intent(TicketSelActivity.this, MyTicketQR.class);
                intent.putExtra("data", ticket);
               TextView statusTV= v.findViewById(R.id.status_mytrips);
                intent.putExtra("status", statusTV.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        controller = new TicketSelectController(this);
        controller.getLists(pageNumber);

        progressDialog = new SpotsDialog(this, R.style.CustomDialogue);
        progressDialog.show();

        recyclerView.addOnScrollListener(
                new OnScrollListener() {
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
                                    }
                                    else loading=true;
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
        recyclerView.setVisibility(View.GONE);
        retryLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onRetry(View view) {
        controller.onRefresh();
        recyclerView.setVisibility(View.VISIBLE);
        retryLayout.setVisibility(View.GONE);
    }
}