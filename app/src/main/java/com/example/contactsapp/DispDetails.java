package com.example.contactsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DispDetails extends AppCompatActivity{

    private TextView view_name,view_mail,view_id,view_phone;
    private ImageView view_profile;
    SQLiteDatabase db;
    private DBhelper DbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        view_id=(TextView)findViewById(R.id.disp_id);
        view_name=(TextView)findViewById(R.id.disp_name);
        view_mail=(TextView)findViewById(R.id.disp_mail);
        view_phone=(TextView)findViewById(R.id.disp_phone);
        view_profile=(ImageView)findViewById(R.id.dips_image);

        DbHelper = new DBhelper(this);
        db = DbHelper.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        long id= extras.getLong("id");

        Cursor cursor = db.rawQuery("SELECT * FROM contacts WHERE _id= " + id, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String mail = cursor.getString(cursor.getColumnIndexOrThrow("mail"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String c_id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));

            view_name.setText(name);
            view_mail.setText(mail);
            view_phone.setText(phone);
            //view_profile.setText(name);
            view_id.setText(c_id);
        }
    }
}
