package com.example.buscustomerapplicationv2.models;

public class Model_ListSjt {
   String clientID;
            String backendKey_user;
            int offset;
            int limit;
            String imei;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBackendKey_user() {
        return backendKey_user;
    }

    public void setBackendKey_user(String backendKey_user) {
        this.backendKey_user = backendKey_user;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
