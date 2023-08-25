package com.linhthuy.mobileapp.database;

import android.provider.BaseColumns;

public class BookContract {

    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PUBLISHER = "publisher";
        public static final String COLUMN_SUBJECT = "subject";
    }

    public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + BookEntry.TABLE_NAME + " (\n" +
            BookEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            BookEntry.COLUMN_TITLE + " TEXT NOT NULL, \n" +
            BookEntry.COLUMN_AUTHOR + " TEXT default NULL, \n" +
            BookEntry.COLUMN_PUBLISHER + " TEXT default NULL, \n" +
            BookEntry.COLUMN_SUBJECT + " TEXT default NULL \n" +
            ")";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;
}
