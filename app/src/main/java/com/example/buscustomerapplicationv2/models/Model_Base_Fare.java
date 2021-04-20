package com.example.buscustomerapplicationv2.models;

import java.util.List;

public class Model_Base_Fare {
     int status;
             String message;
             List<BaseFareResponsePayload> payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<BaseFareResponsePayload> getPayload() {
        return payload;
    }
}
