package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.ValueTicketPreviewActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge_Response;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValueTicketPreviewController {
    ValueTicketPreviewActivity view;
    ApiInterface apiInterface;

    public ValueTicketPreviewController(ValueTicketPreviewActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void rechargeValue(String value, String backendKey_wallet) {
        Model_WalletRecharge model_walletRecharge = new Model_WalletRecharge();

        model_walletRecharge.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user, view));
        model_walletRecharge.setClientID(view.getResources().getString(R.string.clientID));
        model_walletRecharge.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_walletRecharge.setBackendKey_wallet(backendKey_wallet);
        model_walletRecharge.setRecharge_value(value);

        Call<Model_WalletRecharge_Response> call = apiInterface.walletRecharge(model_walletRecharge);

        call.enqueue(new Callback<Model_WalletRecharge_Response>() {
            @Override
            public void onResponse(Call<Model_WalletRecharge_Response> call, Response<Model_WalletRecharge_Response> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus() == 0) {
                        Model_WalletRecharge_Response model_walletRecharge_response = response.body();
                        view.onSuccess(model_walletRecharge_response);
                    } else {
                        view.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_WalletRecharge_Response> call, Throwable t) {
//                view.onError(t.getLocalizedMessage());
                rechargeValue(value, backendKey_wallet);
            }
        });


    }

}
