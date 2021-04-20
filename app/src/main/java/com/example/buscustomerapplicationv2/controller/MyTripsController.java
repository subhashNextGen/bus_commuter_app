package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.MyTripsActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.Model_ListSjt;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_Response;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_ResponsePayload;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTripsController {

    MyTripsActivity view;
    ApiInterface apiInterface;
    List<Model_ListSjt_ResponsePayload> arrayLists;

    public MyTripsController(MyTripsActivity view) {
        this.view = view;
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
    }

    public void getLists(int offset){

        Model_ListSjt model_listSjt=new Model_ListSjt();
        model_listSjt.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user,view));
        model_listSjt.setClientID(view.getResources().getString(R.string.clientID));
        model_listSjt.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_listSjt.setOffset(offset);
        model_listSjt.setLimit(8);
        arrayLists=new ArrayList<>();

        Call<Model_ListSjt_Response> call=apiInterface.listSjt(model_listSjt);
        call.enqueue(new Callback<Model_ListSjt_Response>() {
            @Override
            public void onResponse(Call<Model_ListSjt_Response> call, Response<Model_ListSjt_Response> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus()==0){
                        Model_ListSjt_Response model_listSjt_response=response.body();
                        if (model_listSjt_response.getPayload()!=null) {
                            for (Model_ListSjt_ResponsePayload model : model_listSjt_response.getPayload()) {
                                if (model.getExit_stop_textual_Identifier() != null)
                                    arrayLists.add(model);
                            }
                            if (arrayLists.isEmpty()) {
                                if (offset==1)
                                view.onNorecord();else
                                    view.setLoading();
                            } else {
                                view.onSuccess(arrayLists);
                            }
                        }
                        else
                            view.setLoading();
                    }
                    else {
                        view.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_ListSjt_Response> call, Throwable t) {
getLists(offset);
            }
        });

    }

}
