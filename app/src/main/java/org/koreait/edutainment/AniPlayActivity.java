package org.koreait.edutainment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AniPlayActivity extends AppCompatActivity {

    Button startButton;
    private String[] keys;
    private String correctWord;
    private int correctCount = 0;
    private int totalCount = 0;
    TextView instructionTextView;
    Handler handler = new Handler();
    TextView resultTextView;
    private HashMap<String, Bitmap> images = new HashMap<>();
    private TextToSpeech tts;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private TextView statusTextView;
    private TextView wordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniplay);

        // DBHelper와 ImageDBHelper 인스턴스 생성
        DBHelper dbHelper = new DBHelper(this);
        ImageDBHelper imageDBHelper = new ImageDBHelper(this);

        // 동물 이름 불러오기
        ArrayList<String> animalNames = dbHelper.getAnimalNames();

        // 각 동물 이름에 대해 이미지 불러오기
        for (String name : animalNames) {
            Bitmap image = imageDBHelper.getImage(name);
            images.put(name, image);
        }

        keys = images.keySet().toArray(new String[0]);

        startButton = findViewById(R.id.startbutton);
        instructionTextView = findViewById(R.id.instructionTextView);
        resultTextView = findViewById(R.id.resultTextView);

        startButton.setOnClickListener(v -> {
            instructionTextView.setVisibility(View.GONE);
            startButton.setVisibility(View.GONE);
            correctCount = 0;
            totalCount = 0;
            showImages();
        });

        // TTS를 설정
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> restartGame());

        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        statusTextView = findViewById(R.id.statusTextView);
        wordTextView = findViewById(R.id.wordTextView);

    }


    private void showImages() {

        Random random = new Random();
        List<String> keysCopy = new ArrayList<>(Arrays.asList(keys));
        Collections.shuffle(keysCopy);

        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);

        correctWord = keysCopy.get(0);  // 정답 단어 선택

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(correctWord);
        tts.playSilentUtterance(4000, TextToSpeech.QUEUE_ADD, null);
        tts.speak(correctWord, TextToSpeech.QUEUE_FLUSH, null); //단어 읽어주기

        TextView statusTextView = findViewById(R.id.statusTextView);
        statusTextView.setText("현재 진행 상황: " + totalCount + "/15");

        ImageView[] imageViews = new ImageView[4];
        imageViews[0] = findViewById(R.id.imageView1);
        imageViews[1] = findViewById(R.id.imageView2);
        imageViews[2] = findViewById(R.id.imageView3);
        imageViews[3] = findViewById(R.id.imageView4);

        // 정답 이미지가 표시될 ImageView를 랜덤으로 선택
        int correctImageViewIndex = random.nextInt(imageViews.length);

        // 이미지 뷰에 이미지 설정
        for (int i = 0; i < imageViews.length; i++) {
            if (i == correctImageViewIndex) {
                setImageResource(imageViews[i], correctWord);
                imageViews[i].setOnClickListener(v -> checkAnswer(correctWord));
            } else {
                setImageResource(imageViews[i], keysCopy.get(i + 1));
                int finalI = i;
                imageViews[i].setOnClickListener(v -> checkAnswer(keysCopy.get(finalI + 1)));
            }
        }
    }

    private void setImageResource(ImageView imageView, String key) {
        Bitmap bitmap = images.get(key);
        if (bitmap != null) {
            // 이미지 크기를 줄이기 위한 옵션 설정
            RequestOptions options = new RequestOptions();
            options.override(100, 100);  // 이미지의 가로 세로 크기를 100px로 줄입니다.

            // Glide를 사용하여 이미지 로딩
            Glide.with(this)
                    .load(bitmap)
                    .apply(options)
                    .into(imageView);
        } else {
            // 키에 해당하는 이미지가 없는 경우 처리
        }
    }


    private void checkAnswer(String selectedWord) {
        totalCount++;
        resultTextView.setVisibility(View.VISIBLE); // 추가된 코드
        if (selectedWord.equals(correctWord)) {
            correctCount++;
            resultTextView.setText("정답입니다!!");
        } else {
            resultTextView.setText("아깝다!");
        }
        handler.postDelayed(() -> {
            resultTextView.setText("");
            if (totalCount < 15) {
                resultTextView.setVisibility(View.GONE); // 추가된 코드
            }
            if (totalCount == 15) {
                onGameEnd();
            } else {
                showImages();  // 다음 문제로 넘어감
            }
        }, 1000);
    }

    void onGameEnd() {
        // 결과 텍스트를 설정
        String resultText = correctCount + "개 맞았어요!!";
        resultTextView.setText(resultText);
        resultTextView.setVisibility(View.VISIBLE); // 결과 텍스트를 보이게 함

        // '다시 시작하기' 버튼을 보이게 함
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setText("다시 시작하기");
        restartButton.setVisibility(View.VISIBLE);

        // statusTextView와 wordTextView를 숨김
        statusTextView.setVisibility(View.GONE);
        wordTextView.setVisibility(View.GONE);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
    }


    // 게임을 다시 시작하는 메소드
    void restartGame() {
        // 맞힌 문제의 수를 0으로 초기화
        correctCount = 0;
        totalCount = 0;

        // '다시 시작하기' 버튼을 숨김
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setVisibility(View.GONE);

        // 결과 텍스트를 숨김
        resultTextView.setVisibility(View.GONE);

        statusTextView.setVisibility(View.VISIBLE);
        wordTextView.setVisibility(View.VISIBLE);

        // 이미지 레이아웃을 다시 보이게 함
        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);

        // 새로운 게임 시작
        showImages();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}
