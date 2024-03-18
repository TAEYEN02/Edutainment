package org.koreait.edutainment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

public class RideActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("bicycle", "자전거");
        words.put("bus", "버스");
        words.put("car", "자동차");
        words.put("motorcycle", "오토바이");
        words.put("train", "기차");
        words.put("subway", "지하철");
        words.put("taxi", "택시");
        words.put("airplane", "비행기");
        words.put("helicopter", "헬리콥터");
        words.put("boat", "보트");
        words.put("rocket", "로켓");
        words.put("skateboard", "스케이트보드");
        words.put("space shuttle", "우주선");
        words.put("submersible vehicle", "잠수함");
        words.put("sled", "썰매");
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

        tts.speak(meaning, TextToSpeech.QUEUE_FLUSH, null, "WordSpeak");
        currentIndex = (currentIndex + 1) % keys.length; // 수정된 부분
        progressTextView.setText((currentIndex + 1) + " / " + keys.length);  // 추가된 코드
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