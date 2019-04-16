package com.example.contactsapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private DBhelper DbHelper;
    CustomCursorAdapter mCursorAdapter;
    SQLiteDatabase db,db1;
    private int PermissionCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Contact Permission Granted",Toast.LENGTH_SHORT);
        }
        else
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},PermissionCode);

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
            String mail=c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS));
            String photo=c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.Photo.PHOTO));
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID",id);
            contentValues.put("name",name);
            contentValues.put("phone",data1);
            contentValues.put("mail",mail);
            contentValues.put("photo",photo);
            db.insert(Provider.Entry.TABLE_NAME, null, contentValues);
        }
        c.close();


        //On click listener on the listview


        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DispDetails.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        //Set all contacts to adapter


        db1=DbHelper.getReadableDatabase();
        Cursor todoCursor = db1.rawQuery("SELECT  * FROM contacts ORDER BY name ASC", null);
        mCursorAdapter =new CustomCursorAdapter(this, todoCursor);
        petListView.setAdapter(mCursorAdapter);
    }

}