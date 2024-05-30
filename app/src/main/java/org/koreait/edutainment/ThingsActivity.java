package org.koreait.edutainment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class ThingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "ThingsActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    DBHelper dbHelper;
    private TextToSpeech tts;
    private int currentThingsIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);

        button = findViewById(R.id.nextbutton);
        textView = findViewById(R.id.meaningTextView);
        progressTextView = findViewById(R.id.progressTextView);
        imageView = findViewById(R.id.imageView);

        // TextToSpeech 초기화
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        // SharedPreferences에서 진행 상황 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentThingsIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);
        sqLiteHelper = new DBHelper(this);
        imageDBHelper = new ImageDBHelper(this);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("ball") == null) {
            Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            imageDBHelper.addImage("ball", ball);
        }
        if (imageDBHelper.getImage("doll") == null) {
            Bitmap doll = BitmapFactory.decodeResource(getResources(), R.drawable.doll);
            imageDBHelper.addImage("doll", doll);
        }
        if (imageDBHelper.getImage("socks") == null) {
            Bitmap socks = BitmapFactory.decodeResource(getResources(), R.drawable.socks);
            imageDBHelper.addImage("socks", socks);
        }
        if (imageDBHelper.getImage("gloves") == null) {
            Bitmap gloves = BitmapFactory.decodeResource(getResources(), R.drawable.gloves);
            imageDBHelper.addImage("gloves", gloves);
        }
        if (imageDBHelper.getImage("bag") == null) {
            Bitmap bag = BitmapFactory.decodeResource(getResources(), R.drawable.bag);
            imageDBHelper.addImage("bag", bag);
        }
        if (imageDBHelper.getImage("shoes") == null) {
            Bitmap shoes = BitmapFactory.decodeResource(getResources(), R.drawable.shoes);
            imageDBHelper.addImage("shoes", shoes);
        }
        if (imageDBHelper.getImage("book") == null) {
            Bitmap book = BitmapFactory.decodeResource(getResources(), R.drawable.book);
            imageDBHelper.addImage("book", book);
        }
        if (imageDBHelper.getImage("block") == null) {
            Bitmap block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
            imageDBHelper.addImage("block", block);
        }
        if (imageDBHelper.getImage("robot") == null) {
            Bitmap robot = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
            imageDBHelper.addImage("robot", robot);
        }
        if (imageDBHelper.getImage("roly_poly") == null) {
            Bitmap roly_poly = BitmapFactory.decodeResource(getResources(), R.drawable.roly_poly);
            imageDBHelper.addImage("roly_poly", roly_poly);
        }
        if (imageDBHelper.getImage("scarf") == null) {
            Bitmap scarf = BitmapFactory.decodeResource(getResources(), R.drawable.scarf);
            imageDBHelper.addImage("scarf", scarf);
        }
        if (imageDBHelper.getImage("skirt") == null) {
            Bitmap skirt = BitmapFactory.decodeResource(getResources(), R.drawable.skirt);
            imageDBHelper.addImage("skirt", skirt);
        }
        if (imageDBHelper.getImage("shirt") == null) {
            Bitmap shirt = BitmapFactory.decodeResource(getResources(), R.drawable.shirt);
            imageDBHelper.addImage("shirt", shirt);
        }
        if (imageDBHelper.getImage("pants") == null) {
            Bitmap pants = BitmapFactory.decodeResource(getResources(), R.drawable.pants);
            imageDBHelper.addImage("pants", pants);
        }
        if (imageDBHelper.getImage("necklace") == null) {
            Bitmap necklace = BitmapFactory.decodeResource(getResources(), R.drawable.necklace);
            imageDBHelper.addImage("necklace", necklace);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> thingsNames = sqLiteHelper.getThingsNames();

            if (currentThingsIndex < thingsNames.size()) {
                String nextName = thingsNames.get(currentThingsIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(nextName, "Things");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentThingsIndex++;
            } else {
                currentThingsIndex = 0;
                String firstName = thingsNames.get(currentThingsIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(firstName, "Things");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentThingsIndex + 1) + "/" + thingsNames.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentThingsIndex);
            editor.apply();

            // 음악 서비스 중지
            if (MainActivity.SoundCheck) {
                Intent intent = new Intent(ThingsActivity.this, MusicService.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
