package com.example.buscustomerapplicationv2.models;

import java.io.Serializable;

public class Model_ListSjt_ResponsePayload implements Serializable {

    String fare;
    String entry_datetime;
    boolean exit_available;
    String commuter_backendKey;
    String sjt_qrcode;
    String rjt_expiring_on;
    String exit_datetime;
    boolean rjt_booked;
    String exit_stop_backendKey;
    boolean entry_available;
    String src_stop_textual_Identifier;
    String dest_stop_backendKey;
    String entry_stop_textual_Identifier;
    boolean is_expired;
    String exit_stop_textual_Identifier;
    String created_on;
    String sjt_expiring_on;
    String src_stop_backendKey;
    String dest_stop_textual_Identifier;
    String last_updated_on;
    String rjt_qrcode;
    String entry_stop_backendKey;
    String sjt_backendKey;

    public String getFare() {
        return fare;
    }

    public String getEntry_datetime() {
        return entry_datetime;
    }

    public boolean isExit_available() {
        return exit_available;
    }

    public String getCommuter_backendKey() {
        return commuter_backendKey;
    }

    public String getSjt_qrcode() {
        return sjt_qrcode;
    }

    public String getRjt_expiring_on() {
        return rjt_expiring_on;
    }

    public String getExit_datetime() {
        return exit_datetime;
    }

    public boolean isRjt_booked() {
        return rjt_booked;
    }

    public String getExit_stop_backendKey() {
        return exit_stop_backendKey;
    }

    public boolean isEntry_available() {
        return entry_available;
    }

    public String getSrc_stop_textual_Identifier() {
        return src_stop_textual_Identifier;
    }

    public String getDest_stop_backendKey() {
        return dest_stop_backendKey;
    }

    public String getEntry_stop_textual_Identifier() {
        return entry_stop_textual_Identifier;
    }

    public boolean isIs_expired() {
        return is_expired;
    }

    public String getExit_stop_textual_Identifier() {
        return exit_stop_textual_Identifier;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getSjt_expiring_on() {
        return sjt_expiring_on;
    }

    public String getSrc_stop_backendKey() {
        return src_stop_backendKey;
    }

    public String getDest_stop_textual_Identifier() {
        return dest_stop_textual_Identifier;
    }

    public String getLast_updated_on() {
        return last_updated_on;
    }

    public String getRjt_qrcode() {
        return rjt_qrcode;
    }

    public String getEntry_stop_backendKey() {
        return entry_stop_backendKey;
    }

    public String getSjt_backendKey() {
        return sjt_backendKey;
    }
}
