package com.example.buscustomerapplicationv2.models;

public class Model_SendOtp_Response {


    int status;
    String message;
    SendOtpResponsePay payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public SendOtpResponsePay getPayload() {
        return payload;
    }
}


