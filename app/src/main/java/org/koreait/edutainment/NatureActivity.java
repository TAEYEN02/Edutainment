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

public class NatureActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "Progress";
    private static final String PREFS_KEY_PROGRESS = "NatureActivityProgress";
    DBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView textView;
    TextView progressTextView;
    Button button;
    ImageView imageView;
    DBHelper dbHelper;
    private TextToSpeech tts;
    private int currentNatureIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

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
        currentNatureIndex = sharedPreferences.getInt(PREFS_KEY_PROGRESS, 0);

        sqLiteHelper = new DBHelper(this);
        imageDBHelper = new ImageDBHelper(this);

        // res/drawable에 있는 이미지를 로드하고 ImageDBHelper에 저장
        if (imageDBHelper.getImage("cactus") == null) {
            Bitmap cactus = BitmapFactory.decodeResource(getResources(), R.drawable.cactus);
            imageDBHelper.addImage("cactus", cactus);
        }
        if (imageDBHelper.getImage("sunflower") == null) {
            Bitmap sunflower = BitmapFactory.decodeResource(getResources(), R.drawable.sunflower);
            imageDBHelper.addImage("sunflower", sunflower);
        }
        if (imageDBHelper.getImage("lotus") == null) {
            Bitmap lotus = BitmapFactory.decodeResource(getResources(), R.drawable.lotus);
            imageDBHelper.addImage("lotus", lotus);
        }
        if (imageDBHelper.getImage("dandelion") == null) {
            Bitmap dandelion = BitmapFactory.decodeResource(getResources(), R.drawable.dandelion);
            imageDBHelper.addImage("dandelion", dandelion);
        }
        if (imageDBHelper.getImage("garden_balsam") == null) {
            Bitmap garden_balsam = BitmapFactory.decodeResource(getResources(), R.drawable.garden_balsam);
            imageDBHelper.addImage("garden_balsam", garden_balsam);
        }
        if (imageDBHelper.getImage("hibiscus") == null) {
            Bitmap hibiscus = BitmapFactory.decodeResource(getResources(), R.drawable.hibiscus);
            imageDBHelper.addImage("hibiscus", hibiscus);
        }
        if (imageDBHelper.getImage("cherry_blossoms") == null) {
            Bitmap cherry_blossoms = BitmapFactory.decodeResource(getResources(), R.drawable.cherry_blossoms);
            imageDBHelper.addImage("cherry_blossoms", cherry_blossoms);
        }
        if (imageDBHelper.getImage("bellbind") == null) {
            Bitmap bellbind = BitmapFactory.decodeResource(getResources(), R.drawable.bellbind);
            imageDBHelper.addImage("bellbind", bellbind);
        }
        if (imageDBHelper.getImage("pine") == null) {
            Bitmap pine = BitmapFactory.decodeResource(getResources(), R.drawable.pine);
            imageDBHelper.addImage("pine", pine);
        }
        if (imageDBHelper.getImage("ginkgo") == null) {
            Bitmap ginkgo = BitmapFactory.decodeResource(getResources(), R.drawable.ginkgo);
            imageDBHelper.addImage("ginkgo", ginkgo);
        }
        if (imageDBHelper.getImage("maple") == null) {
            Bitmap maple = BitmapFactory.decodeResource(getResources(), R.drawable.maple);
            imageDBHelper.addImage("maple", maple);
        }

        dbHelper = new DBHelper(this);

        button.setOnClickListener(view -> {
            ArrayList<String> natureNames = sqLiteHelper.getNatureNames();

            if (currentNatureIndex < natureNames.size()) {
                String nextName = natureNames.get(currentNatureIndex);
                textView.setText(nextName);
                tts.speak(nextName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(nextName, "Nature");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }

                currentNatureIndex++;
            } else {
                currentNatureIndex = 0;
                String firstName = natureNames.get(currentNatureIndex);
                textView.setText(firstName);
                tts.speak(firstName, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");

                String englishName = sqLiteHelper.getEnglishName(firstName, "Nature");
                if (englishName != null) {
                    Bitmap imageFromDB = imageDBHelper.getImage(englishName);
                    if (imageFromDB != null) {
                        imageView.setImageBitmap(imageFromDB);
                    }
                }
            }

            // 진행 상황 업데이트
            String progress = (currentNatureIndex + 1) + "/" + natureNames.size();
            progressTextView.setText(progress);

            // SharedPreferences에 진행 상황 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREFS_KEY_PROGRESS, currentNatureIndex);
            editor.apply();

            // 음악 서비스 중지
            if (MainActivity.SoundCheck) {
                Intent intent = new Intent(NatureActivity.this, MusicService.class);
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
