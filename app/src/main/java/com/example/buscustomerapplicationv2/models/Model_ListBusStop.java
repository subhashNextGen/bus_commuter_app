package com.example.buscustomerapplicationv2.models;

import java.util.List;

public class Model_ListBusStop {


        int status;
            String message;
        List<Payload_ListBusStop> payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Payload_ListBusStop> getPayload() {
        return payload;
    }


}
