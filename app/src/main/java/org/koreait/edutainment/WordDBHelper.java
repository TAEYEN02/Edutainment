package org.koreait.edutainment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class WordDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Word.db";
    private static final String TABLE_NAME = "Sentences";
    private static final String COLUMN_NAME = "Sentence";

    public WordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY)";
        db.execSQL(CREATE_TABLE);

        insertSentence(db, "원숭이는 바나나를 먹어요");
        insertSentence(db, "철수가 가방을 챙긴다");
        insertSentence(db, "영희가 엄마를 부른다");
    }

    private void insertSentence(SQLiteDatabase db, String sentence) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, sentence);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전이 업그레이드될 때 수행할 작업을 여기에 작성합니다.
    }

    public ArrayList<String> getSentences() {
        ArrayList<String> sentences = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String sentence = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            sentences.add(sentence);
        }
        cursor.close();
        return sentences;
    }
}
