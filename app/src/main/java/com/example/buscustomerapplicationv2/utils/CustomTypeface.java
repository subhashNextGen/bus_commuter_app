package com.example.buscustomerapplicationv2.utils;

import android.content.Context;
import android.graphics.Typeface;

public class CustomTypeface {

   final Context context;
     Typeface typeface;

    public CustomTypeface(Context context) {
        this.context = context;
    }

    public Typeface getTypeface(){
        typeface=Typeface.createFromAsset(context.getAssets(), "font/CaviarDreams_Bold.ttf");
        return typeface;
    }
}
