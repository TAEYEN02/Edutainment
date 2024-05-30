package org.koreait.edutainment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SentenceDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sentences.db";
    private static final int DATABASE_VERSION = 10;

    public SentenceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE sentences (sentence TEXT, image TEXT)");
            db.execSQL("INSERT INTO sentences VALUES ('내가 공을 차다.','image1')");
            db.execSQL("INSERT INTO sentences VALUES ('형이 책을 읽다.','image2')");
            db.execSQL("INSERT INTO sentences VALUES ('친구가 말을 타다.','image3')");
            db.execSQL("INSERT INTO sentences VALUES ('동생이 손을 씻다.','image4')");
            db.execSQL("INSERT INTO sentences VALUES ('언니가 잠을 자다.','image5')");
            db.execSQL("INSERT INTO sentences VALUES ('엄마가 꽃을 심다.','image6')");
            db.execSQL("INSERT INTO sentences VALUES ('형이 그림을 그리다.','image7')");
            db.execSQL("INSERT INTO sentences VALUES ('친구가 손을 흔들다.','image8')");
            db.execSQL("INSERT INTO sentences VALUES ('내가 노래를 부르다.','image9')");
            db.execSQL("INSERT INTO sentences VALUES ('투수가 공을 던지다.','image10')");
            db.execSQL("INSERT INTO sentences VALUES ('아빠가 마당을 쓸다.','image11')");
            db.execSQL("INSERT INTO sentences VALUES ('엄마가 빨래를 널다.','image12')");
            db.execSQL("INSERT INTO sentences VALUES ('원숭이가 바나나를 먹어요.','image13')");
            db.execSQL("INSERT INTO sentences VALUES ('농부가 밭을 갈아요.','image14')");
            db.execSQL("INSERT INTO sentences VALUES ('친구가 자전거를 타요.','image15')");
            db.execSQL("INSERT INTO sentences VALUES ('친구들이 색종이를 접어요.','image16')");
            db.execSQL("INSERT INTO sentences VALUES ('다람쥐가 도토리를 옮겨요.','image17')");
            db.execSQL("INSERT INTO sentences VALUES ('나는 전화를 걸어요.','image18')");
            db.execSQL("INSERT INTO sentences VALUES ('준우가 친구를 도와줘요.','image19')");
            db.execSQL("INSERT INTO sentences VALUES ('가수가 노래를 불러요.','image20')");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sentences");
        onCreate(db);
    }

    public List<String> getSentences() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM sentences", null);
            List<String> sentences = new ArrayList<>();
            while (cursor.moveToNext()) {
                sentences.add(cursor.getString(0));
            }
            // 문장 리스트를 랜덤하게 섞습니다.
            Collections.shuffle(sentences);
            return sentences;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getNumberOfSentences() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sentences", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public List<String> getSentences(int numSentences) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM sentences LIMIT ?", new String[]{String.valueOf(numSentences)});
        List<String> sentences = new ArrayList<>();
        while (cursor.moveToNext()) {
            sentences.add(cursor.getString(0));
        }
        cursor.close();
        return sentences;
    }

    public String getImageNameForSentence(String sentence) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("sentences", new String[]{"image"}, "sentence = ?", new String[]{sentence}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int imageColumnIndex = cursor.getColumnIndex("image");
            if (imageColumnIndex != -1) {
                String imageName = cursor.getString(imageColumnIndex);
                cursor.close();
                return imageName;
            }
        }
        return "default_image";
    }

}