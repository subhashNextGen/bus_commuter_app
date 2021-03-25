package com.example.buscustomerapplicationv2.controller;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.SelectStopsActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.Model_ListBusStop;
import com.example.buscustomerapplicationv2.models.Payload_ListBusStop;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStopsController {

    SelectStopsActivity view;
    ApiInterface apiInterface;
    Model_ListBusStop model_listBusStop;
    MYdb mYdb;

    public SelectStopsController(SelectStopsActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mYdb = new MYdb(view);
    }

    public void getStops() {

        String clientId = view.getResources().getString(R.string.clientID);

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("clientId", clientId);

        Call<Model_ListBusStop> call = apiInterface.getStops(jsonObject1);

        call.enqueue(new Callback<Model_ListBusStop>() {
            @Override
            public void onResponse(Call<Model_ListBusStop> call, Response<Model_ListBusStop> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 0) {
                        model_listBusStop = response.body();
                        MYdb mYdb = new MYdb(view);
                        mYdb.dropStopTable();
                        Payload_ListBusStop stops;
                        for (int i = 0; model_listBusStop.getPayload().size() > i; i++) {
                            stops = model_listBusStop.getPayload().get(i);
                            Payload_ListBusStop stopsModel = new Payload_ListBusStop(stops.getNumeric_identifier(), stops.getStop_category(), stops.getBackendKey(), stops.getTextual_Identifier());
                            mYdb.addStop(stopsModel);
                        }
                        ArrayList<String> arrayList = new ArrayList<String>(mYdb.getAllLabels());
                        view.init(arrayList);
                    } else {
                        view.showError(String.valueOf(response.body().getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_ListBusStop> call, Throwable t) {
                if (t instanceof SocketTimeoutException){
                    view.showError(t.getMessage());
                }else
                getStops();
            }

        });

    }


}
