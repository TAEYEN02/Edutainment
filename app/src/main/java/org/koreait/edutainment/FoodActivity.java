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

public class FoodActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "FoodActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    private int currentFoodIndex = 0;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

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
        currentFoodIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);

        sqLiteHelper = new DBHelper(this);
        imageDBHelper = new ImageDBHelper(this);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("sweet_potato") == null) {
            Bitmap sweet_potato = BitmapFactory.decodeResource(getResources(), R.drawable.sweet_potato);
            imageDBHelper.addImage("sweet_potato", sweet_potato);
        }
        if (imageDBHelper.getImage("strawberry") == null) {
            Bitmap strawberry = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry);
            imageDBHelper.addImage("strawberry", strawberry);
        }
        if (imageDBHelper.getImage("pumkin") == null) {
            Bitmap pumkin = BitmapFactory.decodeResource(getResources(), R.drawable.pumkin);
            imageDBHelper.addImage("pumkin", pumkin);
        }
        if (imageDBHelper.getImage("bell_pepper") == null) {
            Bitmap bell_pepper = BitmapFactory.decodeResource(getResources(), R.drawable.bell_pepper);
            imageDBHelper.addImage("bell_pepper", bell_pepper);
        }
        if (imageDBHelper.getImage("carrot") == null) {
            Bitmap carrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
            imageDBHelper.addImage("carrot", carrot);
        }
        if (imageDBHelper.getImage("corn") == null) {
            Bitmap corn = BitmapFactory.decodeResource(getResources(), R.drawable.corn);
            imageDBHelper.addImage("corn", corn);
        }
        if (imageDBHelper.getImage("mushroom") == null) {
            Bitmap mushroom = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);
            imageDBHelper.addImage("mushroom", mushroom);
        }
        if (imageDBHelper.getImage("rice") == null) {
            Bitmap rice = BitmapFactory.decodeResource(getResources(), R.drawable.rice);
            imageDBHelper.addImage("block", rice);
        }
        if (imageDBHelper.getImage("tangerine") == null) {
            Bitmap tangerine = BitmapFactory.decodeResource(getResources(), R.drawable.tangerine);
            imageDBHelper.addImage("tangerine", tangerine);
        }
        if (imageDBHelper.getImage("tomato") == null) {
            Bitmap tomato = BitmapFactory.decodeResource(getResources(), R.drawable.tomato);
            imageDBHelper.addImage("tomato", tomato);
        }
        if (imageDBHelper.getImage("spinach") == null) {
            Bitmap spinach = BitmapFactory.decodeResource(getResources(), R.drawable.spinach);
            imageDBHelper.addImage("spinach", spinach);
        }
        if (imageDBHelper.getImage("banana") == null) {
            Bitmap banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
            imageDBHelper.addImage("banana", banana);
        }
        if (imageDBHelper.getImage("watermelon") == null) {
            Bitmap watermelon = BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
            imageDBHelper.addImage("watermelon", watermelon);
        }
        if (imageDBHelper.getImage("onion") == null) {
            Bitmap onion = BitmapFactory.decodeResource(getResources(), R.drawable.onion);
            imageDBHelper.addImage("onion", onion);
        }
        if (imageDBHelper.getImage("cucumber") == null) {
            Bitmap cucumber = BitmapFactory.decodeResource(getResources(), R.drawable.cucumber);
            imageDBHelper.addImage("cucumber", cucumber);
        }
        if (imageDBHelper.getImage("apple") == null) {
            Bitmap apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            imageDBHelper.addImage("apple", apple);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> foodNames = sqLiteHelper.getFoodNames();

            if (currentFoodIndex < foodNames.size()) {
                String nextName = foodNames.get(currentFoodIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(nextName, "Foods");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentFoodIndex++;
            } else {
                currentFoodIndex = 0;
                String firstName = foodNames.get(currentFoodIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = dbHelper.getEnglishName(firstName, "Foods");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentFoodIndex + 1) + "/" + foodNames.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentFoodIndex);
            editor.apply();

            // 음악 서비스 중지
            if (MainActivity.SoundCheck) {
                Intent intent = new Intent(FoodActivity.this, MusicService.class);
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

