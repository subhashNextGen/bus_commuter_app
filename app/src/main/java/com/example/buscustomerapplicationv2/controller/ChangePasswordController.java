package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.ChangePassword;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_ChangeEntryCode;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordController {
    ChangePassword view;
    ApiInterface apiInterface;

    public ChangePasswordController(ChangePassword view) {
        this.view = view;
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
    }

    public void changePassword(String entrycode){

        Model_ChangeEntryCode model_changeEntryCode=new Model_ChangeEntryCode();
        model_changeEntryCode.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user,view));
        model_changeEntryCode.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_changeEntryCode.setClientID(view.getResources().getString(R.string.clientID));
        model_changeEntryCode.setNew_entrycode(entrycode);


        Call<JsonObject> call=apiInterface.changePassword(model_changeEntryCode);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonElement status= response.body().get("status");
                    if (status.getAsInt()==0){
                        view.onSuccess();
                    }
                    else {
                        view.onError(response.body().get("message").getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                view.onError(t.getLocalizedMessage());
            }
        });

    }

}
