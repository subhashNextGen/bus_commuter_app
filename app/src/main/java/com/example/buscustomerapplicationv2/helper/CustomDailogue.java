package com.example.buscustomerapplicationv2.helper;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class CustomDailogue {

    public static void showDailogue(Context context, String s){

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Unable to Proceed");
        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok).toUpperCase(),
                (dialog1, buttonId) -> dialog1.dismiss());
        dialog.show();
    }

    public static void showOTPDailogue(Context context, String s){

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("OTP");
        dialog.setMessage(s);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok).toUpperCase(),
                (dialog1, buttonId) -> dialog1.dismiss());
        dialog.show();
    }


}
