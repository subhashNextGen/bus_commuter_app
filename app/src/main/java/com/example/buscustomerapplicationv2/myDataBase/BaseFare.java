package com.example.buscustomerapplicationv2.myDataBase;

public class BaseFare {

    public static final String fareTableName = "baseFare";
    public static final String Id = "uid";
    public static final String backendKey_dest_stop = "backendKey_dest_stop";
    public static final String backendKey_src_stop = "backendKey_src_stop";
    public static final String src_stop_txt = "src_stop_txt";
    public static final String fare_value = "fare_value";
    public static final String backendKey_fare = "backendKey_fare";
    public static final String dest_stop_txt = "dest_stop_txt";
    public static final String isActive = "isActive";



    public static final String CREATE_TABLE = "CREATE TABLE " + fareTableName + "(" + Id
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "+backendKey_dest_stop + " TEXT, " + backendKey_src_stop + " TEXT, " + src_stop_txt + " TEXT, "
            + fare_value + " TEXT, " + backendKey_fare+" TEXT, "+dest_stop_txt+" TEXT, "+isActive+" BOOLEAN)";



}

