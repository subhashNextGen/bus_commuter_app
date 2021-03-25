package com.example.buscustomerapplicationv2.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.buscustomerapplicationv2.constants.Constants;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.ContentValues.TAG;

public class PermissionManagerUtil {

    public static final int REQUEST_PERMISSION_PHONE_STATE = 1;

    final Context context;

    public PermissionManagerUtil(Context context) {
        this.context = context;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                showPhoneStatePermission();
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }


    @SuppressLint("HardwareIds")
    public void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                context, READ_PHONE_STATE);
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE)) {
            Constants.imei = getDeviceId(context);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    READ_PHONE_STATE)) {
                showExplanation();
                Constants.imei = getDeviceId(context);
            } else {
                requestPermission(READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {
            Constants.imei = getDeviceId(context);
            // Toast.makeText(RegistrationActivity.this, "Permission already Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permission Needed")
                .setMessage("Rationale")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(android.Manifest.permission.READ_PHONE_STATE, PermissionManagerUtil.REQUEST_PERMISSION_PHONE_STATE);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{permissionName}, permissionRequestCode);
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.w(TAG," deviceId : "+deviceId);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
                Log.w(TAG," deviceId1 : "+deviceId);
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                Log.w(TAG," deviceId2 : "+deviceId);
            }
        }

        return deviceId;
    }
}
