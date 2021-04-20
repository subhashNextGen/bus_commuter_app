package com.example.buscustomerapplicationv2.myDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.buscustomerapplicationv2.models.BaseFareResponsePayload;
import com.example.buscustomerapplicationv2.models.Payload_ListBusStop;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MYdb extends SQLiteOpenHelper {


    private static final String dbName = "MyDB.db";
    private static final int versionCode = 1;


    public MYdb(@Nullable Context context) {
        super(context, dbName, null, versionCode);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyTicketDbConstant.CREATE_TABLE);
        db.execSQL(Stops.CREATE_TABLE);
        db.execSQL(BaseFare.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MyTicketDbConstant.myTicketTableName));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Stops.stopTableName));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", BaseFare.fareTableName));

        //create the table again
        onCreate(db);

    }


//    public void addTicket(MyTicketModel myTicketModel) {
//        // getting db instance for writing the user
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        // cv.put(User_id,usr.getId());
//        cv.put(MyTicketDbConstant.myTicketNo, myTicketModel.getMyTicketNo());
//        cv.put(MyTicketDbConstant.myTicketType, myTicketModel.getMyTicketType());
//        cv.put(MyTicketDbConstant.myTicketSource, myTicketModel.getMyTicketSource());
//        cv.put(MyTicketDbConstant.myTicketDestination, myTicketModel.getMyTicketDestination());
//        cv.put(MyTicketDbConstant.myTicketValidity, myTicketModel.getMyTicketValidity());
//        cv.put(MyTicketDbConstant.myTicketFare, myTicketModel.getMyTicketFare());
//        cv.put(MyTicketDbConstant.myTicketQR, myTicketModel.getMyTicketQR());
//        db.insert(MyTicketDbConstant.myTicketTableName, null, cv);
//        db.close();
//    }


    public void addStop(Payload_ListBusStop stopModel) {
        // getting db instance for writing the user
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(Stops.numeric_identifier, stopModel.getNumeric_identifier());
        cv.put(Stops.stop_category, stopModel.getStop_category());
        cv.put(Stops.backendKey, stopModel.getBackendKey());
        cv.put(Stops.textual_Identifier, stopModel.getTextual_Identifier());
        //inserting row
        db.insert(Stops.stopTableName, null, cv);
        //close the database to avoid any leak
        db.close();
    }

    public void addFare(BaseFareResponsePayload faremodel) {
        // getting db instance for writing the user
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // cv.put(User_id,usr.getId());
        cv.put(BaseFare.backendKey_dest_stop, faremodel.getBackendKey_dest_stop());
        cv.put(BaseFare.backendKey_src_stop, faremodel.getBackendKey_src_stop());
        cv.put(BaseFare.src_stop_txt, faremodel.getSrc_stop_txt());
        cv.put(BaseFare.fare_value, faremodel.getFare_value());
        cv.put(BaseFare.backendKey_fare, faremodel.getBackendKey_fare());
        cv.put(BaseFare.dest_stop_txt,faremodel.getDest_stop_txt());
        cv.put(BaseFare.isActive, faremodel.isActive());
        //inserting row
        db.insert(BaseFare.fareTableName, null, cv);
        //close the database to avoid any leak
        db.close();
    }

    public List<String> getAllLabels(){
        List<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  "+Stops.textual_Identifier+" FROM " + Stops.stopTableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding 1st column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    public String getBackendKeyStop(String stopName){
        String backendKey = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query="SELECT * FROM "+ Stops.stopTableName+" WHERE "+ Stops.textual_Identifier+" = ?";
        Cursor cursor = db.rawQuery(query,
                new String[]{stopName});

        if (cursor != null)
        {
            cursor.moveToFirst();
            backendKey = cursor.getString(cursor.getColumnIndex(Stops.backendKey));
            cursor.close();
        }

        db.close();
        return backendKey;
    }

    public String getFare(String from, String to){
       Log.w(TAG, "getFare: "+from + " : " + to );
        String fare = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query="SELECT fare_value FROM "+ BaseFare.fareTableName+" WHERE "+ BaseFare.src_stop_txt+" = ? AND "+BaseFare.dest_stop_txt+" = ? ";
        Cursor cursor = db.rawQuery(query,
                new String[]{from,to});

        if (cursor != null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            fare = cursor.getString(cursor.getColumnIndex(BaseFare.fare_value));
            Log.w(TAG, "getFare: "+from + " : " + to );
            cursor.close();
        }

        db.close();
        if (fare.equals(""))
        return "0";
        else return fare;

    }

    public void dropStopTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(Stops.stopTableName,
                null, null);
        db.close();
    }

    public void dropBaseFareTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(BaseFare.fareTableName,
                null, null);
        db.close();
    }

//    public int getStationId(String stationName){
//
//        int id = 0;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String query="SELECT * FROM "+ Stops.stopTableName+" WHERE "+ Stops.stopName+" = ?";
//       Cursor cursor = db.rawQuery(query,
//                new String[]{stationName});
//
//        if (cursor != null)
//        {
//
//                cursor.moveToFirst();
//
//                id = cursor.getInt(cursor.getColumnIndex(Stops.stopId));
//                cursor.close();
//            }
//
//        db.close();
//        return id;
//    }

//    public String getStationName(int stationId){
//
//        String name = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String query="SELECT * FROM "+ Stops.stopTableName+" WHERE "+ Stops.stopId+" = ?";
//        Cursor cursor = db.rawQuery(query,
//                new String[]{String.valueOf(stationId)});
//
//        if (cursor != null)
//        {
//            cursor.moveToFirst();
//            name = cursor.getString(cursor.getColumnIndex(Stops.stopName));
//            cursor.close();
//        }
//
//        db.close();
//        return name;
//    }

}
