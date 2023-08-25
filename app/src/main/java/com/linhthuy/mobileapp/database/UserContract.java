package com.linhthuy.mobileapp.database;

import android.provider.BaseColumns;

public class UserContract {

    private UserContract() {
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "user_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        // 1-reader, 2-clerk
        public static final String COLUMN_ROLE = "role";
    }

    public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + UserEntry.TABLE_NAME + " (\n" +
            UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            UserEntry.COLUMN_USERNAME + " TEXT NOT NULL UNIQUE,\n" +
            UserEntry.COLUMN_PASSWORD + " TEXT DEFAULT NULL,\n" +
            UserEntry.COLUMN_EMAIL + " TEXT DEFAULT NULL,\n" +
            UserEntry.COLUMN_PHONE_NUMBER + " TEXT DEFAULT NULL, \n" +
            UserEntry.COLUMN_ROLE + " INTEGER NOT NULL DEFAULT " + Role.USER + "\n" +
            ")";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public static class Role {
        public static final String USER = "USER";
        public static final String LIBRARY_CLERK = "LIBRARY_CLERK";
        public static final String MANAGER = "MANAGER";
        public static final String ADMIN = "ADMIN";
    }
}
