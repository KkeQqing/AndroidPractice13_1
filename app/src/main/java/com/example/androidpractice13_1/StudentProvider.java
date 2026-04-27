package com.example.androidpractice13_1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StudentProvider extends ContentProvider {

    // 唯一标识（必须与应用2一致）
    public static final String AUTHORITY = "com.example.androidpractice13_1.studentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + StudentDBHelper.TABLE_NAME);

    private StudentDBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new StudentDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    // 查询
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        return db.query(StudentDBHelper.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    // 插入
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = db.insert(StudentDBHelper.TABLE_NAME, null, values);
        if (id > 0) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        return null;
    }

    // 删除
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.delete(StudentDBHelper.TABLE_NAME, selection, selectionArgs);
    }

    // 更新
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.update(StudentDBHelper.TABLE_NAME, values, selection, selectionArgs);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}