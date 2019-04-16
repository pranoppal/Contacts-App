package com.example.contactsapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity{

    private DBhelper DbHelper;
    CustomCursorAdapter mCursorAdapter;
    SQLiteDatabase db,db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper = new DBhelper(this);
        ListView petListView = findViewById(R.id.list);

        //Get all the contacts in the database file
        db = DbHelper.getWritableDatabase();
        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=?)",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                ContactsContract.Data.CONTACT_ID);
        c.moveToFirst();
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(ContactsContract.Data.CONTACT_ID));
            String name = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String data1 = c.getString(c.getColumnIndex(ContactsContract.Data.DATA1));
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID",id);
            contentValues.put("name",name);
            contentValues.put("phone",data1);
            db.insert(Provider.Entry.TABLE_NAME, null, contentValues);
        }
        c.close();

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
        db1=DbHelper.getReadableDatabase();
        Cursor todoCursor = db1.rawQuery("SELECT  * FROM contacts ORDER BY name ASC", null);
        mCursorAdapter =new CustomCursorAdapter(this, todoCursor);
        petListView.setAdapter(mCursorAdapter);
    }

}