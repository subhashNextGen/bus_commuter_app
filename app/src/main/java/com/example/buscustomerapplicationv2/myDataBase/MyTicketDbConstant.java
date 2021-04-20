package com.example.buscustomerapplicationv2.myDataBase;


public class MyTicketDbConstant {

    public static final String myTicketTableName = "MyTicket";
    public static final String myTicketid = "id";
    public static final String myTicketNo = "ticketNo";
    public static final String myTicketType = "ticketType";
    public static final String myTicketSource = "ticketSource";
    public static final String myTicketDestination = "ticketDestination";
    public static final String myTicketValidity = "ticketValidity";
    public static final String myTicketFare = "ticketFare";
    public static final String myTicketQR = "ticketQR";


    public static final String CREATE_TABLE = "CREATE TABLE " + myTicketTableName + "(" + myTicketid
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + myTicketNo + " INTEGER," + myTicketType + " TEXT,"
            + myTicketSource + " TEXT," + myTicketDestination + " TEXT," + myTicketValidity + " TEXT,"
            + myTicketFare + " INTEGER," + myTicketQR + " TEXT, " + "UNIQUE("+myTicketNo+"))";


}

