package com.linhthuy.mobileapp.lesson.Practice13;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MediaUtils {
    public static List<String> getAllFiles(Context context, String folder, String type) {

        List<String> fileList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection = {
                MediaStore.Files.FileColumns.DATA
        };

        String selection = MediaStore.Files.FileColumns.DATA + " LIKE ? AND " +
                MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{"%" + folder + "%", type};

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(dataIndex);
                fileList.add(filePath);
            }
            cursor.close();
        }
        return fileList;
    }
}