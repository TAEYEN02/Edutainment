package org.koreait.edutainment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 17;
    private static final String DATABASE_NAME = "my_database.db"; //데이터 베이스 생성



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Members" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('아빠','dad')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('엄마','mom')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('할머니','grandmother')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('할아버지','grandfather')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('오빠','older_brother')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('언니','older_sister')");
        db.execSQL("INSERT INTO Members (KoreanName, EnglishName) VALUES('동생','daughter')");


        db.execSQL("CREATE TABLE IF NOT EXISTS Animals" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('곰', 'bear')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('펭귄', 'penguin')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('오리','duck')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('나비','butterfly')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('캥거루','kangaroo')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('박쥐','bat')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('코끼리','elephant')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('토끼','rabbit')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('판다','panda')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('양','sheep')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('다람쥐','squirrel')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('호랑이','tiger')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('수달','otter')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('타조','ostrich')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('사자','lion')");
        db.execSQL("INSERT INTO Animals (KoreanName, EnglishName) VALUES('여우','fox')");


        db.execSQL("CREATE TABLE IF NOT EXISTS Rides" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('비행기','airplane')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('오토바이','motorcycle')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('자동차','car')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('기차','train')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('배','boat')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('헬리콥터','helicopter')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('자전거','bicycle')");
        db.execSQL("INSERT INTO Rides (KoreanName, EnglishName) VALUES('버스','bus')");


        db.execSQL("CREATE TABLE IF NOT EXISTS Things" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('공','ball')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('인형','doll')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('양말','socks')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('장갑','gloves')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('가방','bag')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('구두','shoes')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('책','book')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('블록','block')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('로봇','robot')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('오뚝이','roly_poly')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('목도리','scarf')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('치마','skirt')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('티셔츠','shirt')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('바지','pants')");
        db.execSQL("INSERT INTO Things (KoreanName, EnglishName) VALUES('목걸이','necklace')");


        db.execSQL("CREATE TABLE IF NOT EXISTS Foods" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('고구마','sweet_potato')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('딸기','strawberry')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('호박','pumkin')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('파프리카','bell_pepper')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('당근','carrot')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('옥수수','corn')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('버섯','mushroom')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('쌀','rice')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('귤','tangerine')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('토마토','tomato')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('시금치','spinach')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('바나나','banana')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('수박','watermelon')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('양파','onion')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('오이','cucumber')");
        db.execSQL("INSERT INTO Foods (KoreanName, EnglishName) VALUES('사과','apple')");


        db.execSQL("CREATE TABLE IF NOT EXISTS Nature" + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, KoreanName TEXT, EnglishName TEXT);");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('선인장','cactus')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('해바라기','sunflower')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('연꽃','lotus')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('민들레','dandelion')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('봉선화','garden_balsam')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('무궁화','hibiscus')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('벚꽃','cherry_blossoms')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('매꽃','bellbind')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('소나무','pine')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('은행나무','ginkgo')");
        db.execSQL("INSERT INTO Nature (KoreanName, EnglishName) VALUES('단풍나무','maple')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Members");
        db.execSQL("DROP TABLE IF EXISTS Animals");
        db.execSQL("DROP TABLE IF EXISTS Rides");
        db.execSQL("DROP TABLE IF EXISTS Things");
        db.execSQL("DROP TABLE IF EXISTS Foods");
        db.execSQL("DROP TABLE IF EXISTS Nature");

        onCreate(db);


    }


    public ArrayList<String> getMemberNames() {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Members", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            names.add(name);
        }
        cursor.close();
        return names;
    }

    public ArrayList<String> getAnimalNames() {
        ArrayList<String> AnimalNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Animals ", null);
        while (cursor.moveToNext()) {
            String AnimalName = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            AnimalNames.add(AnimalName);
        }
        cursor.close();
        return AnimalNames;
    }

    public ArrayList<String> getRideNames() {
        ArrayList<String> RideNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Rides ", null);
        while (cursor.moveToNext()) {
            String RideName = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            RideNames.add(RideName);
        }
        cursor.close();
        return RideNames;
    }

    public ArrayList<String> getThingsNames() {
        ArrayList<String> ThingsNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Things ", null);
        while (cursor.moveToNext()) {
            String ThingsName = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            ThingsNames.add(ThingsName);
        }
        cursor.close();
        return ThingsNames;
    }

    public ArrayList<String> getFoodNames() {
        ArrayList<String> FoodNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Foods ", null);
        while (cursor.moveToNext()) {
            String FoodName = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            FoodNames.add(FoodName);
        }
        cursor.close();
        return FoodNames;
    }

    public ArrayList<String> getNatureNames() {
        ArrayList<String> NatureNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KoreanName FROM Nature ", null);
        while (cursor.moveToNext()) {
            String NatureName = cursor.getString(cursor.getColumnIndexOrThrow("KoreanName"));
            NatureNames.add(NatureName);
        }
        cursor.close();
        return NatureNames;
    }

    public String getEnglishName(String koreanName, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT EnglishName FROM " + tableName + " WHERE KoreanName = ?", new String[]{koreanName});
        if (cursor.moveToFirst()) {
            String englishName = cursor.getString(0);
            cursor.close();
            return englishName;
        }
        return null;
    }
}