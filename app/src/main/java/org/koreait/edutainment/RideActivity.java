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

public class RideActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "RideActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    private int currentRideIndex = 0;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

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
        currentRideIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("airplane") == null) {
            Bitmap airplane = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
            imageDBHelper.addImage("airplane", airplane);
        }
        if (imageDBHelper.getImage("motorcycle") == null) {
            Bitmap motorcycle = BitmapFactory.decodeResource(getResources(), R.drawable.motorcycle);
            imageDBHelper.addImage("motorcycle", motorcycle);
        }
        if (imageDBHelper.getImage("car") == null) {
            Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.car);
            imageDBHelper.addImage("car", car);
        }
        if (imageDBHelper.getImage("train") == null) {
            Bitmap train = BitmapFactory.decodeResource(getResources(), R.drawable.train);
            imageDBHelper.addImage("train", train);
        }
        if (imageDBHelper.getImage("boat") == null) {
            Bitmap boat = BitmapFactory.decodeResource(getResources(), R.drawable.boat);
            imageDBHelper.addImage("boat", boat);
        }
        if (imageDBHelper.getImage("helicopter") == null) {
            Bitmap helicopter = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
            imageDBHelper.addImage("helicopter", helicopter);
        }
        if (imageDBHelper.getImage("bicycle") == null) {
            Bitmap bicycle = BitmapFactory.decodeResource(getResources(), R.drawable.bicycle);
            imageDBHelper.addImage("bicycle", bicycle);
        }
        if (imageDBHelper.getImage("bus") == null) {
            Bitmap bus = BitmapFactory.decodeResource(getResources(), R.drawable.bus);
            imageDBHelper.addImage("bus", bus);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> ridesName = dbHelper.getRideNames();

            if (currentRideIndex < ridesName.size()) {
                String nextName = ridesName.get(currentRideIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null);

                String englishName = dbHelper.getEnglishName(nextName, "Rides");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentRideIndex++;
            } else {
                currentRideIndex = 0;
                String firstName = ridesName.get(currentRideIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null);

                String englishName = dbHelper.getEnglishName(firstName, "Rides");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentRideIndex + 1) + "/" + ridesName.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentRideIndex);
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
