package com.example.buscustomerapplicationv2.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<String> {


    public MyArrayAdapter(@NonNull Context context, int resource,  @NonNull List<String> objects) {
        super(context, resource,  objects);
    }

    public boolean isEnabled(int position) {
        // return false if position == positionYouWantToDisable
        return false;
    }
}
