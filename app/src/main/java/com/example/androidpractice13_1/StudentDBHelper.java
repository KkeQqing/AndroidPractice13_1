package com.example.androidpractice13_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDBHelper extends SQLiteOpenHelper {

    // 数据库信息
    public static final String DB_NAME = "student_provider.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "student";

    // 字段：学号、姓名、年龄、专业
    public static final String _ID = "_id";         // 主键
    public static final String STU_ID = "stu_id";   // 学号
    public static final String NAME = "name";       // 姓名
    public static final String AGE = "age";         // 年龄
    public static final String MAJOR = "major";     // 专业

    public StudentDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STU_ID + " TEXT UNIQUE NOT NULL,"
                + NAME + " TEXT,"
                + AGE + " INTEGER,"
                + MAJOR + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}