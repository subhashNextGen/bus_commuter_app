package com.example.buscustomerapplicationv2.models;

public class BaseFareResponsePayload {

    String backendKey_dest_stop;
           String backendKey_src_stop;
            String src_stop_txt;
            String fare_value;
           String backendKey_fare;
            String dest_stop_txt;
            boolean isActive;

    public String getBackendKey_dest_stop() {
        return backendKey_dest_stop;
    }

    public String getBackendKey_src_stop() {
        return backendKey_src_stop;
    }

    public String getSrc_stop_txt() {
        return src_stop_txt;
    }

    public String getFare_value() {
        return fare_value;
    }

    public String getBackendKey_fare() {
        return backendKey_fare;
    }

    public String getDest_stop_txt() {
        return dest_stop_txt;
    }

    public boolean isActive() {
        return isActive;
    }
}
