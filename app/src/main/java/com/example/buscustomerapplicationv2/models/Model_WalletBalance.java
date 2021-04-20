package com.example.buscustomerapplicationv2.models;

public class Model_WalletBalance {
    String clientID;
    String backendKey_user;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
