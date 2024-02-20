package org.koreait.edutainment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FamilyPlayActivity extends AppCompatActivity {

    private HashMap<String, Integer> images = new HashMap<>();
    private String[] keys;
    private String correctWord;
    private int correctCount = 0;
    private int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familyplay);

        // 이미지 DB
        images.put("코끼리", R.drawable.elephant);
        images.put("사자", R.drawable.lion);
        images.put("원숭이", R.drawable.monkey);
        images.put("강아지", R.drawable.dog);
        images.put("고양이", R.drawable.cat);
        images.put("호랑이", R.drawable.tiger);
        images.put("캥거루", R.drawable.kangaroo);
        images.put("팽귄", R.drawable.penguin);
        images.put("돌고래", R.drawable.dolphin);
        images.put("고래", R.drawable.whale);
        images.put("판다", R.drawable.panda);
        images.put("코알라", R.drawable.koala);
        images.put("다람쥐", R.drawable.squirrel);
        images.put("고릴라", R.drawable.gorilla);
        images.put("얼룩말", R.drawable.zebra);
        images.put("말", R.drawable.horse);
        // 이미지 추가 부분.

        keys = images.keySet().toArray(new String[0]);

        Button button = findViewById(R.id.startbutton);
        button.setOnClickListener(v -> {
            correctCount = 0;
            totalCount = 0;
            showImages();
        });
    }

    private void showImages() {
        if (totalCount >= 10) {
            Toast.makeText(this, "게임이 끝났습니다! 정답 수: " + correctCount, Toast.LENGTH_LONG).show();
            return;
        }

        Random random = new Random();
        List<String> keysCopy = new ArrayList<>(Arrays.asList(keys));
        Collections.shuffle(keysCopy);

        correctWord = keysCopy.get(0);  // 정답 단어 선택

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(correctWord);

        TextView statusTextView = findViewById(R.id.statusTextView);
        statusTextView.setText("현재 진행 상황: " + totalCount + "/10");

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
        Integer resId = images.get(key);
        if (resId != null) {
            imageView.setImageResource(resId);
        } else {
            // 키에 해당하는 이미지 리소스 ID가 없는 경우 처리
        }
    }

    private void checkAnswer(String selectedWord) {
        totalCount++;
        if (selectedWord.equals(correctWord)) {
            correctCount++;
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
        }
        showImages();  // 다음 문제로 넘어감
    }
}