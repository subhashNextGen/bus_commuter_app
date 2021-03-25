package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.OtpActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.constants.Constants;
import com.example.buscustomerapplicationv2.models.Model_VerifyOTP;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpController {
    OtpActivity view;
    ApiInterface apiInterface;

    public OtpController(OtpActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void onVerifyOtp(String mobile, String otp){
        Model_VerifyOTP model_verifyOTP=new Model_VerifyOTP();
        model_verifyOTP.setClientID(view.getResources().getString(R.string.clientID));
        model_verifyOTP.setmOTP(otp);
        model_verifyOTP.setMobile(mobile);


        Call<JSONObject> call=apiInterface.verify(model_verifyOTP);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()){

                    try {
                        assert response.body() != null;
                        int status= (int) response.body().getInt("status");
                        if (status==0)
                        {
                            view.onSuccess();
                        }
                        else {
                            view.onError((String) response.body().getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
onVerifyOtp(mobile, otp);
            }
        });



    }


}
