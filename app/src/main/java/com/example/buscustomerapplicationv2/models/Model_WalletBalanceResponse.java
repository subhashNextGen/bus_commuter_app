package com.example.buscustomerapplicationv2.models;

import java.io.Serializable;

public class Model_WalletBalanceResponse implements Serializable {
    int status;
            String message;
    Model_WalletBalanceResponsePayload payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Model_WalletBalanceResponsePayload getPayload() {
        return payload;
    }
}
