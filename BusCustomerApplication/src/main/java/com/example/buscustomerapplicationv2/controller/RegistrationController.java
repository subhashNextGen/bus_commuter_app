package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.RegistrationActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_CommuterRegistration;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationController {
    RegistrationActivity view;
    ApiInterface apiInterface;
    Model_CommuterRegistration model_commuterRegistration;

    public RegistrationController(RegistrationActivity view) {
        this.view = view;
    }

    public void registerUser(String firstName, String lastName, String mobileNo, String email, String password, String address, String city, String state, String pincode) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        model_commuterRegistration = new Model_CommuterRegistration(firstName, lastName, mobileNo, email, password, address, city, state, pincode);
        model_commuterRegistration.setClientID(view.getString(R.string.clientID));
        model_commuterRegistration.setImei(new PermissionManagerUtil(view).getDeviceIMEI());

        Call<JsonObject> call = apiInterface.registerUser(model_commuterRegistration);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    JsonElement statusCode = response.body().get("status");
                    if (statusCode.getAsInt() == 0) {
                        JsonObject payload = (JsonObject) response.body().get("payload");
                        JsonElement otp=payload.get("verificationOTP");
                        AppPreferences.setAppPrefrences(VariablesConstant.MOBILE,mobileNo,view);
                        view.onSuccess(otp.getAsString());
                    } else {
                        view.showError(String.valueOf(response.body().get("message")));
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
registerUser(firstName, lastName, mobileNo, email, password, address, city, state, pincode);
            }
        });


//        Toast.makeText(view, ""+model_commuterRegistration.logValue(), Toast.LENGTH_LONG).show();


    }


}
