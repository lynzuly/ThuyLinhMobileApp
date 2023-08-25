package com.linhthuy.mobileapp.database.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.linhthuy.mobileapp.database.BookContract;
import com.linhthuy.mobileapp.database.UserContract;

public class DbManager {
    public static final String DATABASE_NAME = "test_practice";
    public static final int DATABASE_VERSION = 4;

    private Context context;
    private boolean isConnected;
    private SQLiteOpenHelper dbHelper;
    private static SQLiteDatabase db;
    private static DbManager instance;

    public DbManager() {
    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    };

    public void open(Context context) {
        dbHelper = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        this.context = context;

        try {
            Cursor cursor = db.rawQuery("SELECT 1", null);
            isConnected = true;
            Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            isConnected = false;
            Toast.makeText(context, "Connect failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getAllTable() {
        StringBuilder sb = null;
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            sb = new StringBuilder();
            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(c.getString(0));
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Query failed!", Toast.LENGTH_LONG).show();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return sb != null ? sb.toString() : null;
    }

    public void close() {
        db.close();
        dbHelper.close();
        Toast.makeText(context, "Closed database!", Toast.LENGTH_SHORT).show();
    }

    public void createAllTable() {
        db.execSQL(UserContract.SQL_CREATE);
        db.execSQL(BookContract.SQL_CREATE);
    }

    public void dropAllTable() {
        db.execSQL(UserContract.SQL_DROP);
        db.execSQL(BookContract.SQL_DROP);
    }

    public static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqliteDb) {
            db.execSQL(UserContract.SQL_CREATE);
            db.execSQL(BookContract.SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
