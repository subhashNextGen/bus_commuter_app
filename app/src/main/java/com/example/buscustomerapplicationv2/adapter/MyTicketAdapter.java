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


public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.ViewHolder> {
    private static final String TAG = MyTicketAdapter.class.getSimpleName() ;
    private final Context context;
    private final List<Model_ListSjt_ResponsePayload> list;
    final MYdb mYdb;
    private static ClickListener clickListener;


    public MyTicketAdapter(Context context, List<Model_ListSjt_ResponsePayload> list) {
        this.context = context;
        this.list = list;
        mYdb = new MYdb(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ticket_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.setIsRecyclable(false);
        Log.w(TAG," onCreateViewHolder : "+ viewType);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_ListSjt_ResponsePayload myTick = list.get(position);
        holder.itemView.setTag(myTick);

        if (myTick != null) {
            String dateTime = myTick.getCreated_on();

//            Log.w("Date Available ", "" + dateTime);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            Date date = null;
            try {
                date = dt.parse(dateTime);
//                Log.w("Date", "" + date);
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
            holder.amount.setText(String.format("Rs %.2f", Float.valueOf(myTick.getFare())));
            holder.fromStnTV.setText(myTick.getSrc_stop_textual_Identifier());
            holder.toStnTV.setText(myTick.getDest_stop_textual_Identifier());
            if (myTick.isRjt_booked())
                holder.type.setText("RJT");
            else
                holder.type.setText("SJT");

            if (myTick.isEntry_available() && myTick.isExit_available())
            {
                if (myTick.isIs_expired())
                {
                    holder.status.setText("Expired");
                }else
                {
                    holder.status.setText("Valid");
                }

            }else
            {
                if (!myTick.isEntry_available() && !myTick.isExit_available()){
                    holder.status.setText("Consumed");
                }

            }

        }
        Log.w(TAG, " onBindView : "+ position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView amount;
        public final TextView fromStnTV;
        public final TextView toStnTV;
        public final TextView date;
        public final TextView time;
        public final TextView type;
        public final TextView status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.date_mytrip);
            time = itemView.findViewById(R.id.time_mytrip);
            fromStnTV = itemView.findViewById(R.id.from_mytrip);
            toStnTV = itemView.findViewById(R.id.to_mytrip);
            amount = itemView.findViewById(R.id.amount_mytrips);
            type = itemView.findViewById(R.id.type_mytrips);
            status=itemView.findViewById(R.id.status_mytrips);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnMyClickListener(ClickListener clickListener) {
        MyTicketAdapter.clickListener = clickListener;
    }

}
