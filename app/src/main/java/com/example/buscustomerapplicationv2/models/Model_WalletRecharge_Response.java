package com.example.buscustomerapplicationv2.models;

import java.io.Serializable;

public class Model_WalletRecharge_Response implements Serializable {

    int status;
            String message;
    Model_WalletRecharge_ResponsePayload payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Model_WalletRecharge_ResponsePayload getPayload() {
        return payload;
    }
}
