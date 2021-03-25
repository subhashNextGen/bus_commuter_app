package com.example.buscustomerapplicationv2.models;

import java.util.List;

public class Model_ListSjt_Response {

     int status;
             String message;
    List<Model_ListSjt_ResponsePayload> payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Model_ListSjt_ResponsePayload> getPayload() {
        return payload;
    }
}
