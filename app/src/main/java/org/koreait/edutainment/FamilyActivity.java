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

public class FamilyActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "FamilyActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    DBHelper dbHelper;
    private TextToSpeech tts;
    private int currentFamilyIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

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
        currentFamilyIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);

        sqLiteHelper = new DBHelper(this);
        imageDBHelper = new ImageDBHelper(this);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("mom") == null) {
            Bitmap mom = BitmapFactory.decodeResource(getResources(), R.drawable.mom);
            imageDBHelper.addImage("mom", mom);
        }
        if (imageDBHelper.getImage("dad") == null) {
            Bitmap dad = BitmapFactory.decodeResource(getResources(), R.drawable.dad);
            imageDBHelper.addImage("dad", dad);
        }
        if (imageDBHelper.getImage("daughter") == null) {
            Bitmap daughter = BitmapFactory.decodeResource(getResources(), R.drawable.daughter);
            imageDBHelper.addImage("daughter", daughter);
        }
        if (imageDBHelper.getImage("grandfather") == null) {
            Bitmap grandfather = BitmapFactory.decodeResource(getResources(), R.drawable.grandfather);
            imageDBHelper.addImage("grandfather", grandfather);
        }
        if (imageDBHelper.getImage("grandmother") == null) {
            Bitmap grandmother = BitmapFactory.decodeResource(getResources(), R.drawable.grandmother);
            imageDBHelper.addImage("grandmother", grandmother);
        }
        if (imageDBHelper.getImage("older_brother") == null) {
            Bitmap older_brother = BitmapFactory.decodeResource(getResources(), R.drawable.older_brother);
            imageDBHelper.addImage("older_brother", older_brother);
        }
        if (imageDBHelper.getImage("older_sister") == null) {
            Bitmap older_sister = BitmapFactory.decodeResource(getResources(), R.drawable.older_sister);
            imageDBHelper.addImage("older_sister", older_sister);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> memberNames = sqLiteHelper.getMemberNames();

            if (currentFamilyIndex < memberNames.size()) {
                String nextName = memberNames.get(currentFamilyIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(nextName, "Members");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentFamilyIndex++;
            } else {
                currentFamilyIndex = 0;
                String firstName = memberNames.get(currentFamilyIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(firstName, "Members");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentFamilyIndex + 1) + "/" + memberNames.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentFamilyIndex);
            editor.apply();

            // 음악 서비스 중지
            if (MainActivity.SoundCheck) {
                Intent intent = new Intent(FamilyActivity.this, MusicService.class);
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
