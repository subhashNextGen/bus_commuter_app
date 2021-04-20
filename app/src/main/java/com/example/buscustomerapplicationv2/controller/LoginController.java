package com.example.buscustomerapplicationv2.controller;

import android.util.Log;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.LoginActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.BaseFareResponsePayload;
import com.example.buscustomerapplicationv2.models.Model_Base_Fare;
import com.example.buscustomerapplicationv2.models.Model_CreateSessio_Response;
import com.example.buscustomerapplicationv2.models.Model_CreateSession;
import com.example.buscustomerapplicationv2.models.Model_ListCommuterCategory_response;
import com.example.buscustomerapplicationv2.models.Model_SendOTP;
import com.example.buscustomerapplicationv2.models.Model_SendOtp_Response;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginController {

    LoginActivity view;
    ApiInterface apiInterface;
    MYdb mYdb;
    public LoginController(LoginActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mYdb = new MYdb(view);
    }
    public void onLogin(String mobile, String password) {

        Model_CreateSession model_createSession = new Model_CreateSession();
        model_createSession.setClientID(view.getResources().getString(R.string.clientID));
        model_createSession.setEntrycode(password);
        model_createSession.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_createSession.setMobile(mobile);

        Call<Model_CreateSessio_Response> call = apiInterface.loginSession(model_createSession);

            call.enqueue(new Callback<Model_CreateSessio_Response>() {
                @Override
                public void onResponse(Call<Model_CreateSessio_Response> call, Response<Model_CreateSessio_Response> response) {
                    if (response.code() == 200) {
                        if (response.body().getStatus() == 0) {
                            Model_CreateSessio_Response model_createSessio_response = response.body();
                            AppPreferences.setAppPrefrences(VariablesConstant.backendKey_user, model_createSessio_response.getPayload().getBackendKey(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.MOBILE, model_createSessio_response.getPayload().getMobile(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.ADDRESS, model_createSessio_response.getPayload().getAddress(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.CITY, model_createSessio_response.getPayload().getCity(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.STATE, model_createSessio_response.getPayload().getState(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.PINCODE, model_createSessio_response.getPayload().getPincode(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.F_NAME, model_createSessio_response.getPayload().getFirst_name(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.L_NAME, model_createSessio_response.getPayload().getLast_name(), view);
                            AppPreferences.setAppPrefrences(VariablesConstant.EMAIL, model_createSessio_response.getPayload().getEmailId(), view);

                            fetchCommuter();
                            view.onSuccess();

                        }else if (response.body().getStatus() == 1009){
                            Log.e(TAG," onMobileNotVerifieed");
                            verifyMobile(model_createSession);
                        }
                        else {
                            view.onError(response.body().getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<Model_CreateSessio_Response> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        view.onError(t.getMessage());
                    }
//
                    else
                        onLogin(mobile, password);
                }
            });
    }

    private void verifyMobile(Model_CreateSession model_createSession) {
        Model_SendOTP model_sendOTP=new Model_SendOTP();
        model_sendOTP.setMobile(model_createSession.getMobile());
        model_sendOTP.setClientID(model_createSession.getClientID());
        model_sendOTP.setSmsText("1");
        model_sendOTP.setImei(model_createSession.getImei());

        Call<Model_SendOtp_Response> call=apiInterface.sendOtpRequest(model_sendOTP);
        call.enqueue(new Callback<Model_SendOtp_Response>() {
            @Override
            public void onResponse(Call<Model_SendOtp_Response> call, Response<Model_SendOtp_Response> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()==0){
                        AppPreferences.setAppPrefrences(VariablesConstant.MOBILE, model_createSession.getMobile(), view);

                        view.onMobileVerify(response.body().getPayload().getOTP());
                    }else
                        view.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Model_SendOtp_Response> call, Throwable t) {
verifyMobile(model_createSession);
            }
        });
    }

    public void fetchCommuter() {
        String client = view.getResources().getString(R.string.clientID);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("clientId", client);
        Call<Model_ListCommuterCategory_response> call = apiInterface.getCommuterCategory(jsonObject);
        call.enqueue(new Callback<Model_ListCommuterCategory_response>() {
            @Override
            public void onResponse(Call<Model_ListCommuterCategory_response> call, Response<Model_ListCommuterCategory_response> response) {
                if (response.isSuccessful()) {
                    Model_ListCommuterCategory_response model_listCommuterCategory_response = response.body();
                    if (model_listCommuterCategory_response.getStatus() == 0) {
                        AppPreferences.setAppPrefrences(VariablesConstant.BACKENDKEY_COMMUTER_CAT, model_listCommuterCategory_response.getPayload().get(0).getBackendKey(), view);
                        fetchupdatedFare();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_ListCommuterCategory_response> call, Throwable t) {
                fetchCommuter();
            }
        });
    }

    public void fetchupdatedFare() {
        JsonObject jsonObject = new JsonObject();
        String clientId = view.getResources().getString(R.string.clientID);
        jsonObject.addProperty("ClientId", clientId);

mYdb.dropBaseFareTable();
        Call<Model_Base_Fare> call = apiInterface.getFare(jsonObject);

        call.enqueue(new Callback<Model_Base_Fare>() {
            @Override
            public void onResponse(Call<Model_Base_Fare> call, Response<Model_Base_Fare> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 0) {
                        Model_Base_Fare model_base_fare = response.body();
                        for (BaseFareResponsePayload payload : model_base_fare.getPayload()) {
                            mYdb.addFare(payload);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_Base_Fare> call, Throwable t) {
                fetchupdatedFare();
            }
        });
    }
    
    


}
