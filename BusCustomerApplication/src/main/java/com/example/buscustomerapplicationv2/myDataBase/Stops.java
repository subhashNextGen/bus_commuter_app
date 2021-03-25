package com.example.buscustomerapplicationv2.myDataBase;


public class Stops {

    public static final String stopTableName = "stops";
    public static final String Id = "uid";
    public static final String numeric_identifier = "numeric_identifier";
    public static final String stop_category = "stop_category";
    public static final String backendKey = "backendKey";
    public static final String textual_Identifier = "textual_Identifier";


    public static final String CREATE_TABLE = "CREATE TABLE " + stopTableName + "(" + Id
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "+numeric_identifier + " Text, " + stop_category + " TEXT, " + backendKey + " TEXT, "
            + textual_Identifier + " TEXT, " + "UNIQUE("+backendKey+"))";


}

