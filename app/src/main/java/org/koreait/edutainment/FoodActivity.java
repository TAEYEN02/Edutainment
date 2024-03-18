package org.koreait.edutainment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

public class FoodActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("pizza", "피자");
        words.put("chicken", "치킨");
        words.put("kimchi", "김치");
        words.put("hamburger", "햄버거");
        words.put("noodles", "국수");
        words.put("kimbap", "김밥");
        words.put("ice cream", "아이스크림");
        words.put("chocolate", "초콜릿");
        words.put("candy", "사탕");
        words.put("curry", "카레");
        words.put("tteokbokki", "떡볶이");
        words.put("banana", "바나나");
        words.put("apple", "사과");
        words.put("orange", "오렌지");
        words.put("grapes", "포도");
        words.put("strawberry", "딸기");
        words.put("watermelon", "수박");
        words.put("pineapple", "파인애플");
        words.put("mango", "망고");
        words.put("peach", "복숭아");
        words.put("cherry", "체리");
        words.put("blueberry", "블루베리");
        //..
        // 단어추가부분.

        keys = words.keySet().toArray(new String[0]);

        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
        // SharedPreferences에서 진행 상황을 불러옵니다.
        SharedPreferences sharedPreferences = getSharedPreferences("FamilyCardProgress", MODE_PRIVATE);
        currentIndex = sharedPreferences.getInt("FamilyCardProgress", 0);


        Button button = findViewById(R.id.nextbutton);
        button.setOnClickListener(v -> showCard());
    }

    private void showCard() {
        if (currentIndex >= keys.length) {  // 추가된 코드
            tts.speak("끝", TextToSpeech.QUEUE_FLUSH, null, "EndSpeak");
            return;  // 추가된 코드
        }
        String word = keys[currentIndex];// 수정된 부분
        String meaning = words.get(word);

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        TextView progressTextView = findViewById(R.id.progressTextView);  // 추가된 코드

        wordTextView.setText(word);
        meaningTextView.setText(meaning);

        tts.speak(meaning, TextToSpeech.QUEUE_FLUSH, null, "WordSpeak");
        currentIndex = (currentIndex + 1) % keys.length; // 수정된 부분
        progressTextView.setText((currentIndex + 1) + " / " + keys.length);  // 추가된 코드

        // SharedPreferences에 진행 상황을 저장합니다.
        SharedPreferences sharedPreferences = getSharedPreferences("FamilyCardProgress", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("FamilyCardProgress", currentIndex);
        editor.apply();
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