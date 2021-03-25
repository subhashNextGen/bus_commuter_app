package com.example.buscustomerapplicationv2.models;

public class Model_WalletRecharge {

       String clientID;
                String backendKey_user;
                String backendKey_wallet;
                String recharge_value;
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

    public String getBackendKey_wallet() {
        return backendKey_wallet;
    }

    public void setBackendKey_wallet(String backendKey_wallet) {
        this.backendKey_wallet = backendKey_wallet;
    }

    public String getRecharge_value() {
        return recharge_value;
    }

    public void setRecharge_value(String recharge_value) {
        this.recharge_value = recharge_value;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
