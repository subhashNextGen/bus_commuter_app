package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.ForgetPassword;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_ChangeEntryCode;
import com.example.buscustomerapplicationv2.models.Model_CreateSessio_Response;
import com.example.buscustomerapplicationv2.models.Model_SendOTP;
import com.example.buscustomerapplicationv2.models.Model_SendOtp_Response;
import com.example.buscustomerapplicationv2.models.Model_VerifyOTP;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordController {
    ForgetPassword view;
    ApiInterface apiInterface;

    public ForgetPasswordController(ForgetPassword view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    public void confirmMobile(String mobile) {
        Model_SendOTP sendOTP = new Model_SendOTP();
        sendOTP.setClientID(view.getResources().getString(R.string.clientID));
        sendOTP.setMobile(mobile);
        sendOTP.setSmsText("2");

        Call<Model_SendOtp_Response> call = apiInterface.sendOtpRequest(sendOTP);
        call.enqueue(new Callback<Model_SendOtp_Response>() {
            @Override
            public void onResponse(Call<Model_SendOtp_Response> call, Response<Model_SendOtp_Response> response) {
                if (response.code() == 200) {
                    Model_SendOtp_Response model_sendOtp_response = response.body();
                    if (model_sendOtp_response.getStatus() == 0) {
                        view.showOtpScreen(model_sendOtp_response.getPayload().getOTP());
                    } else {
                        view.onError(model_sendOtp_response.getMessage());
                    }
                }else
                view.onError(response.message());
            }

            @Override
            public void onFailure(Call<Model_SendOtp_Response> call, Throwable t) {
                view.onError(t.getLocalizedMessage());
            }
        });


    }


    public void confirmOtp(String mobile, String otp) {

        Model_VerifyOTP model_verifyOTP = new Model_VerifyOTP();
        model_verifyOTP.setClientID(view.getResources().getString(R.string.clientID));
        model_verifyOTP.setMobile(mobile);
        model_verifyOTP.setmOTP(otp);

        Call<Model_CreateSessio_Response> call = apiInterface.verifyMobile(model_verifyOTP);
        call.enqueue(new Callback<Model_CreateSessio_Response>() {
            @Override
            public void onResponse(Call<Model_CreateSessio_Response> call, Response<Model_CreateSessio_Response> response) {

                if (response.code() == 200) {
                   Model_CreateSessio_Response model_createSessio_response=response.body();
                    if (model_createSessio_response.getStatus() == 0) {
                        AppPreferences.setAppPrefrences(VariablesConstant.backendKey_user,model_createSessio_response.getPayload().getBackendKey(),view);
                        view.showPassLayout();
                    } else {
                        view.onError(model_createSessio_response.getMessage());
                    }
                } else
                    view.onError(response.message());
            }

            @Override
            public void onFailure(Call<Model_CreateSessio_Response> call, Throwable t) {

                view.onError(t.getLocalizedMessage());
            }
        });

    }

    public void changePassword(String mobile, String password) {
        Model_ChangeEntryCode model_changeEntryCode = new Model_ChangeEntryCode();

        model_changeEntryCode.setClientID(view.getResources().getString(R.string.clientID));
        model_changeEntryCode.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_changeEntryCode.setNew_entrycode(password);
        model_changeEntryCode.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user, view));

        Call<JsonObject> call = apiInterface.changePassword(model_changeEntryCode);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code() == 200) {
                    JsonElement status = response.body().get("status");
                    JsonElement message = response.body().get("message");
                    int StatusCode = status.getAsInt();
                    if (StatusCode == 0) {
                        view.onSuccess();

                    } else {
                        view.onError(message.getAsString());
                    }

                } else
                    view.onError(response.message());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                view.onError(t.getLocalizedMessage());
            }
        });

    }

}
