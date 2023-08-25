package com.linhthuy.mobileapp.lesson;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linhthuy.mobileapp.database.UserContract;
import com.linhthuy.mobileapp.database.manager.DbManager;

public class lesson10_UserProvider extends ContentProvider {
    private final String AUTHORITY = "com.huy.Practice9_UserProvider";
    private final String CONTENT_PATH = "user";
    private final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    static final int URI_ALL_ITEMS_CODE = 1;
    static final int URI_ONE_ITEM_CODE = 2;
    UriMatcher uriMatcher;

    private DbManager dbManager;

    @Override
    public boolean onCreate() {
        dbManager = DbManager.getInstance();
        dbManager.open(getContext());

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH, URI_ALL_ITEMS_CODE);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH + "/#", URI_ONE_ITEM_CODE);

        return dbManager.isConnected();
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selections, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = dbManager.getDb().query(UserContract.UserEntry.TABLE_NAME,
                projection,
                selections, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = dbManager.getDb().insert(UserContract.UserEntry.TABLE_NAME, null, contentValues);
        Uri _uri = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(_uri, null);
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
