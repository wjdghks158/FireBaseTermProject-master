package org.techtown.firebasetermproject.calender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBCalendar extends SQLiteOpenHelper {
    private static final String DB_NAME="calendar.db";
    private static final int DATABASE_VERSION = 2;

    public MyDBCalendar(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    //"_id INTEGER  NOT NULL PRIMARY KEY," +
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE calendar (" + "day VARCHAR(45) NULL, color INTEGER NULL" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS calendar");
        onCreate(db);
    }
}
