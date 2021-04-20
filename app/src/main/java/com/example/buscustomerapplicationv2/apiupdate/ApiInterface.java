package com.example.buscustomerapplicationv2.apiupdate;

import com.example.buscustomerapplicationv2.models.ModelVerificationResponse;
import com.example.buscustomerapplicationv2.models.Model_Base_Fare;
import com.example.buscustomerapplicationv2.models.Model_BookSjt_Response;
import com.example.buscustomerapplicationv2.models.Model_ChangeEntryCode;
import com.example.buscustomerapplicationv2.models.Model_CommuterRegistration;
import com.example.buscustomerapplicationv2.models.Model_CreateSessio_Response;
import com.example.buscustomerapplicationv2.models.Model_CreateSession;
import com.example.buscustomerapplicationv2.models.Model_ListBusStop;
import com.example.buscustomerapplicationv2.models.Model_ListCommuterCategory_response;
import com.example.buscustomerapplicationv2.models.Model_ListSjt;
import com.example.buscustomerapplicationv2.models.Model_ListSjt_Response;
import com.example.buscustomerapplicationv2.models.Model_SendOTP;
import com.example.buscustomerapplicationv2.models.Model_SendOtp_Response;
import com.example.buscustomerapplicationv2.models.Model_VerifyOTP;
import com.example.buscustomerapplicationv2.models.Model_WalletBalance;
import com.example.buscustomerapplicationv2.models.Model_WalletBalanceResponse;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge;
import com.example.buscustomerapplicationv2.models.Model_WalletRecharge_Response;
import com.example.buscustomerapplicationv2.models.Model_bookSJT;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface ApiInterface {

    @POST(EndApi.REGISTER_USER)
    Call<JsonObject> registerUser(@Body Model_CommuterRegistration registrationModule);

    @POST(EndApi.STOPS_MASTER)
    Call<Model_ListBusStop> getStops(@Body JsonObject clientId);

    @POST(EndApi.LOGIN)
    Call<Model_CreateSessio_Response> loginSession(@Body Model_CreateSession model_createSession);

    @POST(EndApi.VERIFICATION)
    Call<ModelVerificationResponse> verify(@Body Model_VerifyOTP model_createSession);

    @POST(EndApi.GET_FARE)
    Call<Model_Base_Fare> getFare(@Body JsonObject clientId);

    @POST(EndApi.SEND_OTP)
    Call<Model_SendOtp_Response> sendOtpRequest(@Body Model_SendOTP sendOTP);
//
//
    @POST(EndApi.VALIDATE_OTP)
    Call<Model_CreateSessio_Response> verifyMobile(@Body Model_VerifyOTP verifyOTP);
//
    @POST(EndApi.CHANGE_PASSWORD)
    Call<JsonObject> changePassword( @Body Model_ChangeEntryCode changeEntryCode);
//
    @POST(EndApi.WALLET_BAL)
    Call<Model_WalletBalanceResponse> walletBalance(@Body Model_WalletBalance model_walletBalance);

    @POST(EndApi.WALLET_RECHARGE)
    Call<Model_WalletRecharge_Response> walletRecharge(@Body Model_WalletRecharge model_walletRecharge);
//
//
    @POST(EndApi.BOOK_TICKET)
    Call<Model_BookSjt_Response> bookTicket(@Body Model_bookSJT model_bookSJT);
//
    @POST(EndApi.LIST_COMMUTER_CATEGORY)
    Call<Model_ListCommuterCategory_response> getCommuterCategory(@Body JsonObject clientId);
//
    @POST(EndApi.LIST_SJT)
    Call<Model_ListSjt_Response> listSjt( @Body Model_ListSjt model_listSjt);
//
//    @POST(EndApi.GET_TICKET_ID)
//    Call<SJTTicketGenerateResponse> generateTicketID(@Header("Authorization") String auth,
//                                                     @Body SJTTicketGenerateRequest sjtTicketGenerateRequest);
//
//    @POST(EndApi.GET_PASS_FARE)
//    Call<PassFareResponse> getPassFare(@Header("Authorization") String auth,
//                                       @Body PassFareRequest passFareRequest);
//
//    @POST(EndApi.GET_PASS_SERIAL)
//    Call<PassSerialNumberResponse> getPassSerial(@Header("Authorization") String auth,
//                                                 @Body PassSerialNumberRequest passSerialNumberRequest);
//
//    @POST(EndApi.BOOK_PASS_QR)
//    Call<PassBookQrResponse> getPassQR(@Header("Authorization") String auth,
//                                       @Body PassBookQrRequest passBookQrRequest);
//
//    @POST(EndApi.FORGET_PASSWORD_1)
//    Call<ForgetPasswordResponse> forgetPassword(@Body ForgetPasswordRequest forgetPasswordRequest);
//    @POST(EndApi.FORGET_PASSWORD_2)
//    Call<ForgetPasswordStep2Response> forgetPasswordOtpVerify(@Body ForgetPasswordStep2Req forgetPasswordStep2Req);
//    @POST(EndApi.FORGET_PASSWORD_3)
//    Call<ForgetPasswordStep2Response> forgetPasswordChPass(@Body ForgetPasswordStep3Req loginModule);
//
//    @POST(EndApi.MY_TICKET)
//    Call<MyTicketResponse> myTicket(@Header("Authorization") String auth,
//                                    @Body MyTicketReq loginModule);


}