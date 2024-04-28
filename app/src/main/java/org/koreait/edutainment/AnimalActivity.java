package org.koreait.edutainment;

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

// AnimalActivity 클래스
public class AnimalActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "AnimalActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    private int currentAnimalIndex = 0;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        button = findViewById(R.id.nextbutton);
        sqLiteHelper = new DBHelper(this);
        imageDBHelper = new ImageDBHelper(this);
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
        currentAnimalIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("bear") == null) {
            Bitmap bear = BitmapFactory.decodeResource(getResources(), R.drawable.bear);
            imageDBHelper.addImage("bear", bear);
        }
        if (imageDBHelper.getImage("penguin") == null) {
            Bitmap penguin = BitmapFactory.decodeResource(getResources(), R.drawable.penguin);
            imageDBHelper.addImage("penguin", penguin);
        }
        if (imageDBHelper.getImage("duck") == null) {
            Bitmap duck = BitmapFactory.decodeResource(getResources(), R.drawable.duck);
            imageDBHelper.addImage("duck", duck);
        }
        if (imageDBHelper.getImage("butterfly") == null) {
            Bitmap butterfly = BitmapFactory.decodeResource(getResources(), R.drawable.butterfly);
            imageDBHelper.addImage("butterfly", butterfly);
        }
        if (imageDBHelper.getImage("kangaroo") == null) {
            Bitmap kangaroo = BitmapFactory.decodeResource(getResources(), R.drawable.kangaroo);
            imageDBHelper.addImage("kangaroo", kangaroo);
        }
        if (imageDBHelper.getImage("bat") == null) {
            Bitmap bat = BitmapFactory.decodeResource(getResources(), R.drawable.bat);
            imageDBHelper.addImage("bat", bat);
        }
        if (imageDBHelper.getImage("elephant") == null) {
            Bitmap elephant = BitmapFactory.decodeResource(getResources(), R.drawable.elephant);
            imageDBHelper.addImage("elephant", elephant);
        }
        if (imageDBHelper.getImage("rabbit") == null) {
            Bitmap rabbit = BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);
            imageDBHelper.addImage("rabbit", rabbit);
        }
        if (imageDBHelper.getImage("panda") == null) {
            Bitmap panda = BitmapFactory.decodeResource(getResources(), R.drawable.panda);
            imageDBHelper.addImage("panda", panda);
        }
        if (imageDBHelper.getImage("sheep") == null) {
            Bitmap sheep = BitmapFactory.decodeResource(getResources(), R.drawable.sheep);
            imageDBHelper.addImage("sheep", sheep);
        }
        if (imageDBHelper.getImage("squirrel") == null) {
            Bitmap squirrel = BitmapFactory.decodeResource(getResources(), R.drawable.squirrel);
            imageDBHelper.addImage("squirrel", squirrel);
        }
        if (imageDBHelper.getImage("tiger") == null) {
            Bitmap tiger = BitmapFactory.decodeResource(getResources(), R.drawable.tiger);
            imageDBHelper.addImage("tiger", tiger);
        }
        if (imageDBHelper.getImage("otter") == null) {
            Bitmap otter = BitmapFactory.decodeResource(getResources(), R.drawable.otter);
            imageDBHelper.addImage("otter", otter);
        }
        if (imageDBHelper.getImage("ostrich") == null) {
            Bitmap ostrich = BitmapFactory.decodeResource(getResources(), R.drawable.ostrich);
            imageDBHelper.addImage("ostrich", ostrich);
        }
        if (imageDBHelper.getImage("lion") == null) {
            Bitmap lion = BitmapFactory.decodeResource(getResources(), R.drawable.lion);
            imageDBHelper.addImage("lion", lion);
        }
        if (imageDBHelper.getImage("fox") == null) {
            Bitmap fox = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
            imageDBHelper.addImage("fox", fox);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> animalNames = dbHelper.getAnimalNames();

            if (currentAnimalIndex < animalNames.size()) {
                String nextName = animalNames.get(currentAnimalIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null);

                String englishName = dbHelper.getEnglishName(nextName, "Animals");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentAnimalIndex++;
            } else {
                currentAnimalIndex = 0;
                String firstName = animalNames.get(currentAnimalIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null);

                String englishName = dbHelper.getEnglishName(firstName, "Animals");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentAnimalIndex + 1) + "/" + animalNames.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentAnimalIndex);
            editor.apply();
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
