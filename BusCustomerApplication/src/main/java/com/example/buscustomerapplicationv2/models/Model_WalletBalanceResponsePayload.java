package com.example.buscustomerapplicationv2.models;

import java.io.Serializable;

public class Model_WalletBalanceResponsePayload implements Serializable {

    String wallet_balance;
            String last_updated_on;
            String wallet_qr_code;
            String last_emei_used;
           String backendKey_wallet;

    public String getWallet_balance() {
        return wallet_balance;
    }

    public String getLast_updated_on() {
        return last_updated_on;
    }

    public String getWallet_qr_code() {
        return wallet_qr_code;
    }

    public String getLast_emei_used() {
        return last_emei_used;
    }

    public String getBackendKey_wallet() {
        return backendKey_wallet;
    }
}
