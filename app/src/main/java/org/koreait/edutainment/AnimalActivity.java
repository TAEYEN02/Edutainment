package org.koreait.edutainment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

public class AnimalActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;

    private int currentIndex = 0;  // 추가된 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("elephant", "코끼리");
        words.put("lion", "사자");
        words.put("monkey", "원숭이");
        words.put("dog", "강아지");
        words.put("cat", "고양이");
        words.put("tiger", "호랑이");
        words.put("kangaroo", "캥거루");
        words.put("penguin", "펭귄");
        words.put("dolphin", "돌고래");
        words.put("whale", "고래");
        words.put("panda", "판다");
        words.put("koala", "코알라");
        words.put("squirrel", "다람쥐");
        words.put("gorilla", "고릴라");
        words.put("zebra", "얼룩말");
        words.put("horse", "말");
        words.put("crocodile", "악어");
        words.put("butterfly", "나비");
        words.put("snake", "뱀");
        words.put("bear", "곰");
        words.put("hippopotamus", "하마");
        //..
        // 단어추가부분.

        keys = words.keySet().toArray(new String[0]);

        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

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
        progressTextView.setText((currentIndex + 1) + " / " + keys.length);  // 추가된 코드

        tts.speak(meaning, TextToSpeech.QUEUE_FLUSH, null, "WordSpeak");

        currentIndex = (currentIndex + 1) % keys.length; // 수정된 부분
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