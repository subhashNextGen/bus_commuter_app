package com.example.buscustomerapplicationv2.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.ValueTicketActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_WalletBalance;
import com.example.buscustomerapplicationv2.models.Model_WalletBalanceResponse;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValueTicketController {
    ValueTicketActivity view;
    ApiInterface apiInterface;
    PermissionManagerUtil pm;

    public ValueTicketController(ValueTicketActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pm=new PermissionManagerUtil(view);
    }

    @SuppressLint("HardwareIds")
    public void getBal() {

        Model_WalletBalance model_walletBalance = new Model_WalletBalance();
        model_walletBalance.setClientID(view.getResources().getString(R.string.clientID));
        model_walletBalance.setImei(pm.getDeviceIMEI());
        model_walletBalance.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user,view));

        Call<Model_WalletBalanceResponse> call=apiInterface.walletBalance(model_walletBalance);

        call.enqueue(new Callback<Model_WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<Model_WalletBalanceResponse> call, Response<Model_WalletBalanceResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()==0){
                        Model_WalletBalanceResponse model_walletBalanceResponse=response.body();
                        view.onSucces(model_walletBalanceResponse);
                    }else
                    {
                        view.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_WalletBalanceResponse> call, Throwable t) {

//                view.onError(t.getLocalizedMessage());

                getBal();
            }
        });
}

    
}
