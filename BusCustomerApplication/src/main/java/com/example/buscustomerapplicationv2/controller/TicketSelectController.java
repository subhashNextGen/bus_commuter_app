package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.TicketSelActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.Model_ListSjt;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_Response;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketSelectController {
    TicketSelActivity view;
    ApiInterface apiInterface;

    public TicketSelectController(TicketSelActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getLists(int offset) {
        int limit = 10;
        Model_ListSjt model_listSjt = new Model_ListSjt();
        model_listSjt.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user, view));
        model_listSjt.setClientID(view.getResources().getString(R.string.clientID));
        model_listSjt.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_listSjt.setOffset(offset * limit);
        model_listSjt.setLimit(10);

        Call<Model_ListSjt_Response> call = apiInterface.listSjt(model_listSjt);
        call.enqueue(new Callback<Model_ListSjt_Response>() {
            @Override
            public void onResponse(Call<Model_ListSjt_Response> call, Response<Model_ListSjt_Response> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 0) {
                        Model_ListSjt_Response model_listSjt_response = response.body();
                        if (model_listSjt_response.getPayload() != null)
                            view.onSuccess(model_listSjt_response.getPayload());
                        else
                            view.setLoading();
                    } else {
                        if (offset > 0)
                            view.setLoading();
                        else
                            view.onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_ListSjt_Response> call, Throwable t) {
//                view.onError(t.getLocalizedMessage());
                getLists(offset);
            }
        });

    }

    public void onRefresh(){
        getLists(0);
    }

}
