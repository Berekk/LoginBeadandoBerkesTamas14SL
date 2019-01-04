package com.example.tomi.loginbeadandoberkestamas14sl;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
        HASZNÁLHATÓ LINKEK:
        http://sqlitebrowser.org/
        https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
        https://developer.android.com/training/data-storage/sqlite.html
        https://developer.android.com/training/data-storage/room/index.html
 */

public class AdatbazisSegito extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "User_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NICKNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "FULLNAME";
    public static final String COL_5 = "PHONENUMBER";

    public AdatbazisSegito(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NICKNAME TEXT, PASSWORD TEXT, FULLNAME TEXT, PHONENUMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean adatRogzites(String nickname, String password, String fullname, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nickname);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, fullname);
        contentValues.put(COL_5, phoneNumber);

        long eredmeny = db.insert(TABLE_NAME, null, contentValues);

        if (eredmeny == -1)
        {
            return false;
        }else
            return true;
    }

    public Cursor adatLekerdezes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor eredmeny = db.rawQuery("Select * from " + TABLE_NAME,null);
        return eredmeny;
    }

}

