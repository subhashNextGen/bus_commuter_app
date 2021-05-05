package com.example.buscustomerapplicationv2.utils;


public class TicketType {
    private static final String suffixString = "-T-";

    private static final String sjt = "SJT";
    private static final String rjt = "RJT";
    private static final String value = "ValueTicket";


    public static String tTypes(types type) {
        String typesStr = "";
        switch (type) {
            case SJT:
                typesStr = suffixString + sjt;
                break;
            case RJT:
                typesStr = suffixString + rjt;
                break;
            case ValueTicket:
                typesStr = suffixString + value;
                break;
        }
        return typesStr;
    }


}
