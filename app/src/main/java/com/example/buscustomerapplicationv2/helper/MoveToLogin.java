package com.example.buscustomerapplicationv2.helper;

import android.content.Context;
import android.content.Intent;

import com.example.buscustomerapplicationv2.activity.LoginActivity;

public class MoveToLogin {

    public static void moveToLogin(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}
