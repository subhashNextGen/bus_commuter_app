package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.OtpActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.ModelVerificationResponse;
import com.example.buscustomerapplicationv2.models.Model_CreateSessio_Response;
import com.example.buscustomerapplicationv2.models.Model_VerifyOTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpController {
    OtpActivity view;
    ApiInterface apiInterface;
    int flag;

    public OtpController(OtpActivity view) {
        this.view = view;
        this.flag = flag;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void onVerifyOtp(String mobile, String otp) {
        Model_VerifyOTP model_verifyOTP = new Model_VerifyOTP();
        model_verifyOTP.setClientID(view.getResources().getString(R.string.clientID));
        model_verifyOTP.setmOTP(otp);
        model_verifyOTP.setMobile(mobile);
//        if (flag == 1)
//            forLogin(model_verifyOTP);
//        else
            forVerify(model_verifyOTP);

    }

    void forVerify(Model_VerifyOTP model_verifyOTP) {
        Call<ModelVerificationResponse> call = apiInterface.verify(model_verifyOTP);
        call.enqueue(new Callback<ModelVerificationResponse>() {
            @Override
            public void onResponse(Call<ModelVerificationResponse> call, Response<ModelVerificationResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 0) {
                        view.onSuccess();
                    } else {
                        view.onError((String) response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVerificationResponse> call, Throwable t) {
                forVerify(model_verifyOTP);
            }
        });
    }

    void forLogin(Model_VerifyOTP model_verifyOTP) {
        Call<Model_CreateSessio_Response> call = apiInterface.verifyMobile(model_verifyOTP);
        call.enqueue(new Callback<Model_CreateSessio_Response>() {
            @Override
            public void onResponse(Call<Model_CreateSessio_Response> call, Response<Model_CreateSessio_Response> response) {
                if (response.isSuccessful())
                    if (response.body().getStatus() == 0) {
//                        view.onLoginSuccess();
                    } else
                        view.onError(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<Model_CreateSessio_Response> call, Throwable t) {

            }
        });
    }


}
