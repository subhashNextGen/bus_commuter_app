package com.example.buscustomerapplicationv2.models;

public class Model_CreateSessio_Response {

   int status;
            String message;
            CreateSessionPayload payload;


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CreateSessionPayload getPayload() {
        return payload;
    }
}
