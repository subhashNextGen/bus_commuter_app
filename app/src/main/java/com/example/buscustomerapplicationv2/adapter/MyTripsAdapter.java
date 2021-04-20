package com.example.buscustomerapplicationv2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.ViewHolder> {
    private final Context context;
    private final List<Model_ListSjt_ResponsePayload> list;
    MYdb mYdb;

    private static ViewHolder.ClickListener clickListener;

    public MyTripsAdapter(Context context, List<Model_ListSjt_ResponsePayload> list) {
        this.context = context;
        this.list = list;
        mYdb = new MYdb(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_my_trips, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_ListSjt_ResponsePayload mytrip = list.get(position);

        if (mytrip != null) {
            String dateTime = mytrip.getEntry_datetime();

            Log.w("Date Available ", "" + dateTime);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            Date date = null;
            try {
                date = dt.parse(dateTime);
                Log.w("Date", "" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt2 = new SimpleDateFormat("dd-MMM-yyyy");
            // String date1=dt2.format(date);
            String time = dt1.format(date);
            String da = dt2.format(date);
            holder.date.setText(da);
            holder.time.setText(time);
            holder.amount.setText(String.format("Rs %.2f", Float.valueOf(mytrip.getFare())));
            holder.fromStnTV.setText(mytrip.getSrc_stop_textual_Identifier());
            holder.toStnTV.setText(mytrip.getDest_stop_textual_Identifier());
//            if (mytrip.isRjt_booked())
//                holder.mytrip.setText("RJT");
//            else
//                holder.mytrip.setText("SJT");
//
        }
//                String dateTime = mytrip.getEntry_datetime();
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
//                Date date = null;
//                try {
//                    date = dt.parse(dateTime);
//                    Log.w("Date", "" + date);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("hh:mm");
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat dt2 = new SimpleDateFormat("dd-MMM-yyyy");
//                // String date1=dt2.format(date);
//                String time = dt1.format(date);
//                String date1=dt2.format(date);
//                holder.date.setText(date1);
//                holder.time.setText(time);
//              holder.amount.setText(mytrip.getFare());
//                holder.fromStnTV.setText(mytrip.getEntry_stop_textual_Identifier());
//                holder.toStnTV.setText(mytrip.getExit_stop_textual_Identifier());


    }

    @Override
    public int getItemCount() {
            return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView amount ;
        public TextView fromStnTV ;
        public TextView toStnTV ;
        public TextView date;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_mytrip);
            time = itemView.findViewById(R.id.time_mytrip);
            fromStnTV=itemView.findViewById(R.id.from_mytrip);
            toStnTV=itemView.findViewById(R.id.to_mytrip);
            amount = itemView.findViewById(R.id.amount_mytrips);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        public interface ClickListener {
            void onItemClick(int position, View v);
        }

        public void setOnMyClickListener(ClickListener clickListener) {
            MyTripsAdapter.clickListener = (ClickListener) clickListener;
        }
    }
}
