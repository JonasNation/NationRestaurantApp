package com.example.android.nationrestaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Nation Restaurant";
    public static final String TABLE_NAME = "Orders";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "totalPurchase";
    public static final String TABLE_NAME_SECOND = "Reservation";
    public static final String NAME_FIRST = "First_Name";
    public static final String NAME_LAST = "Last_Name";
    public static final String EMAIL = "Email_Address";
    public static final String PHONE = "Phone_Number";
    public static final String GUEST = "Number_Of_Guest";
    public static final String DATE = "Date_of_Reservation";
    public static final String TIME = "Time_of_Reservation";


    Integer nextOrder;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TOTALPURCHASE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_SECOND + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, EMAIL_ADDRESS TEXT, PHONE_NUMBER INT, NUMBER_OF_GUEST INT, DATE_OF_RESERVATION DATE, TIME_OF_RESERVATION TIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME + TABLE_NAME_SECOND);
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(String totalPurchase) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, totalPurchase);

        long result = sqLiteDatabase.insert(TABLE_NAME, null,contentValues);
        return result != -1;
    }

    public boolean insertReservation(String First_Name, String Last_Name, String Email_Address, String Phone_Number, String Number_Of_Guest, String Date_Of_Reservation, String Time_Of_Reservation) {
        SQLiteDatabase reserve = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_FIRST, First_Name);
        contentValues.put(NAME_LAST, Last_Name);
        contentValues.put(EMAIL, Email_Address);
        contentValues.put(PHONE, Phone_Number);
        contentValues.put(GUEST, Number_Of_Guest);
        contentValues.put(DATE, Date_Of_Reservation);
        contentValues.put(TIME, Time_Of_Reservation);



        long result = reserve.insert(TABLE_NAME_SECOND, null, contentValues);
        return result != -1;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("select * from Orders", null);
    }

    public Cursor getAllReservations() {
        SQLiteDatabase reservations = this.getReadableDatabase();
        return reservations.rawQuery("select * from Reservation", null);
    }

    public boolean deleteOrder(String totalPurchase) {
        //handle updating records in database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        long result = sqLiteDatabase.delete(TABLE_NAME, "totalPurchase = ?", new String[]{totalPurchase});
        return result != -1;
    }

    public boolean deleteReservation(String Date_Of_Reservation) {
        //handle updating records in database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        long result = sqLiteDatabase.delete(TABLE_NAME_SECOND, "Date_Of_Reservation = ?", new String[]{Date_Of_Reservation});
        return result != -1;
    }
}
