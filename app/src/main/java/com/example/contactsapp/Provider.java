package com.example.contactsapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class Provider {

    private Provider(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.contactsapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CONTACTS = "contact";

    public static final class Entry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CONTACTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;

        public final static String TABLE_NAME = "contacts";
        public final static String _ID =BaseColumns._ID;
        public final static String COLUMN_CONTACT_ID ="ID" ;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_PHONE = "phone";
        public final static String COLUMN_MAIL = "mail";
        public final static String COLUMN_PHOTO="photo";


    }
}
