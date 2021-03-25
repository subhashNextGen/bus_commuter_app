package com.example.buscustomerapplicationv2.controller;

import android.util.Log;

import com.example.buscustomerapplicationv2.R;
import com.example.buscustomerapplicationv2.activity.NewQrBookTicketActivity;
import com.example.buscustomerapplicationv2.apiupdate.ApiClient;
import com.example.buscustomerapplicationv2.apiupdate.ApiInterface;
import com.example.buscustomerapplicationv2.models.Model_BookSjt_Response;
import com.example.buscustomerapplicationv2.models.Model_bookSJT;
import com.example.buscustomerapplicationv2.myDataBase.MYdb;
import com.example.buscustomerapplicationv2.sharedPref.AppPreferences;
import com.example.buscustomerapplicationv2.sharedPref.VariablesConstant;
import com.example.buscustomerapplicationv2.utils.PermissionManagerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class NewQrBookTicketController {
    NewQrBookTicketActivity view;
    ApiInterface apiInterface;
    MYdb mYdb;

    public NewQrBookTicketController(NewQrBookTicketActivity view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mYdb = new MYdb(view);
    }


    public void bookTicket(String from, String to, boolean rjtBooked, float fare) {
        Model_bookSJT model_bookSJT = new Model_bookSJT();
        model_bookSJT.setClientID(view.getResources().getString(R.string.clientID));
        model_bookSJT.setBackendKey_commuter_cat(AppPreferences.getAppPrefrences(VariablesConstant.BACKENDKEY_COMMUTER_CAT, view));
        model_bookSJT.setBackendKey_user(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_user, view));
        String srcBackend = mYdb.getBackendKeyStop(from);
        String destBackend = mYdb.getBackendKeyStop(to);
        model_bookSJT.setRjt_booked(rjtBooked);
        model_bookSJT.setImei(new PermissionManagerUtil(view).getDeviceIMEI());
        model_bookSJT.setBackendKey_src_stop(srcBackend);
        model_bookSJT.setBackendKey_dest_stop(destBackend);
        model_bookSJT.setFare_value(String.valueOf(fare));

        Call<Model_BookSjt_Response> call = apiInterface.bookTicket(model_bookSJT);
        call.enqueue(new Callback<Model_BookSjt_Response>() {
            @Override
            public void onResponse(Call<Model_BookSjt_Response> call, Response<Model_BookSjt_Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Model_BookSjt_Response model_bookSjt_response = response.body();
                        view.onSuccessBook(model_bookSjt_response);
                    } else view.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Model_BookSjt_Response> call, Throwable t) {
                bookTicket(from, to, rjtBooked, fare);
            }
        });
    }

    public void getFare(String from, String to) {

        mYdb = new MYdb(view);
        float returnFare = 0;
        Log.w(TAG, "getFare: " + from + " : " + to);
        float singlefare = Float.parseFloat(mYdb.getFare(from, to));
        if (singlefare == 0) {
            view.onError("No Direct Busses");
            return;
        }
        float singlefare2 = Float.parseFloat(mYdb.getFare(to, from));
        if (singlefare2 != 0)
            returnFare = singlefare + singlefare2;

        view.updateFare(String.valueOf(singlefare), String.valueOf(returnFare));

    }

}
