package com.example.buscustomerapplicationv2.models;

import java.util.List;

public class Model_ListCommuterCategory_response {

        int status;
            String message;
           List<responsePaylod> payload;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<responsePaylod> getPayload() {
        return payload;
    }

    public class responsePaylod{
             String textual_identifier;
                    int display_order;
                     String backendKey;

             public String getTextual_identifier() {
                 return textual_identifier;
             }

             public int getDisplay_order() {
                 return display_order;
             }

             public String getBackendKey() {
                 return backendKey;
             }
         }

}
