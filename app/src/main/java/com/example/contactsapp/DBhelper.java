package com.example.contactsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts1.db";
    private static final int DATABASE_VERSION = 1;

    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Provider.Entry.TABLE_NAME + "("
                + Provider.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Provider.Entry.COLUMN_CONTACT_ID + " INTEGER," + Provider.Entry.COLUMN_NAME + " TEXT,"
                + Provider.Entry.COLUMN_PHONE + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
