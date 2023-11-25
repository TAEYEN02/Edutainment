package org.koreait.edutainment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class AnimalActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("elephant", "코끼리");
        words.put("lion", "사자");
        words.put("monkey", "원숭이");//..
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
        int index = new Random().nextInt(keys.length);
        String word = keys[index];
        String meaning = words.get(word);

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);

        wordTextView.setText(word);
        meaningTextView.setText(meaning);

        tts.speak(meaning, TextToSpeech.QUEUE_FLUSH, null, "WordSpeak");
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