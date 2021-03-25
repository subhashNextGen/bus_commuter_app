package com.example.buscustomerapplicationv2.models;

public class Payload_ListBusStop {


    String numeric_identifier;
    String stop_category;
    String backendKey;
    String textual_Identifier;

    public Payload_ListBusStop(String numeric_identifier, String stop_category, String backendKey, String textual_Identifier) {
        this.numeric_identifier = numeric_identifier;
        this.stop_category = stop_category;
        this.backendKey = backendKey;
        this.textual_Identifier = textual_Identifier;
    }

    public String getNumeric_identifier() {
        return numeric_identifier;
    }

    public String getStop_category() {
        return stop_category;
    }

    public String getBackendKey() {
        return backendKey;
    }

    public String getTextual_Identifier() {
        return textual_Identifier;
    }
}
