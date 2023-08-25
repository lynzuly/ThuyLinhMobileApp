package com.linhthuy.mobileapp.database;

import android.provider.BaseColumns;

public class CallCardContract {

    private CallCardContract() {
    }

    public static class CallCardEntry implements BaseColumns {
        public static final String TABLE_NAME = "call_card";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_BOOK_ID = "book_id";
        public static final String COLUMN_EFF_DATE = "eff_date";
        public static final String COLUMN_EXP_DATE = "exp_date";
        // 0-Borrowing, 1-Returned, 2-Expired
        public static final String COLUMN_STATE = "state";
    }

    public final String SQL_CREATE = "CREATE TABLE " + CallCardEntry.TABLE_NAME + " (\n" +
            CallCardEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            CallCardEntry.COLUMN_USER_ID + " INTEGER NOT NULL, \n" +
            CallCardEntry.COLUMN_BOOK_ID + " INTEGER NOT NULL, \n" +
            CallCardEntry.COLUMN_EFF_DATE + " TEXT NOT NULL, \n" +
            CallCardEntry.COLUMN_EXP_DATE + " TEXT NOT NULL, \n" +
            CallCardEntry.COLUMN_STATE + " INTEGER default 0 \n" +
            ")";
}
