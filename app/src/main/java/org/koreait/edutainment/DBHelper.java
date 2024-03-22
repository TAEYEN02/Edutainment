package org.koreait.edutainment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "familyList.db"; //데이터 베이스 생성

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Members"); //테이블 생성
        db.execSQL("create table Members (mID integer primary key autoincrement, Name text);");


        db.execSQL("INSERT INTO Members (Name) VALUES('부모님')");
        db.execSQL("INSERT INTO Members (Name) VALUES('아빠')");
        db.execSQL("INSERT INTO Members (Name) VALUES('엄마')");
        db.execSQL("INSERT INTO Members (Name) VALUES('할머니')");
        db.execSQL("INSERT INTO Members (Name) VALUES('할아버지')");
        db.execSQL("INSERT INTO Members (Name) VALUES('아들')");
        db.execSQL("INSERT INTO Members (Name) VALUES('딸')");
        db.execSQL("INSERT INTO Members (Name) VALUES('고모, 이모')");
        db.execSQL("INSERT INTO Members (Name) VALUES('여동생')");
        db.execSQL("INSERT INTO Members (Name) VALUES('누나')");
        db.execSQL("INSERT INTO Members (Name) VALUES('남동생')");
        db.execSQL("INSERT INTO Members (Name) VALUES('형')");
        db.execSQL("INSERT INTO Members (Name) VALUES('고모부, 삼촌')");
        db.execSQL("INSERT INTO Members (Name) VALUES('사촌')");


    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<String> getMemberNames() {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name FROM Members", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                names.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return names;
    }


}