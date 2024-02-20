package org.koreait.edutainment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

public class AnimalActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private HashMap<String, Integer> images = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("elephant", "코끼리");
        images.put("elephant", R.drawable.elephant);
        words.put("lion", "사자");
        images.put("lion", R.drawable.lion);
        words.put("monkey", "원숭이");
        images.put("monkey", R.drawable.monkey);
        words.put("dog", "강아지");
        images.put("dog", R.drawable.dog);
        words.put("cat", "고양이");
        images.put("cat", R.drawable.cat);
        words.put("tiger", "호랑이");
        images.put("tiger", R.drawable.tiger);
        words.put("kangaroo", "캥거루");
        images.put("kangaroo", R.drawable.kangaroo);
        words.put("penguin", "펭귄");
        images.put("penguin", R.drawable.penguin);
        words.put("dolphin", "돌고래");
        images.put("dolphin", R.drawable.dolphin);
        words.put("whale", "고래");
        images.put("whale", R.drawable.whale);
        words.put("panda", "판다");
        images.put("panda", R.drawable.panda);
        words.put("koala", "코알라");
        images.put("koala", R.drawable.koala);
        words.put("squirrel", "다람쥐");
        images.put("squirrel", R.drawable.squirrel);
        words.put("gorilla", "고릴라");
        images.put("gorilla", R.drawable.gorilla);
        words.put("zebra", "얼룩말");
        images.put("zebra", R.drawable.zebra);
        words.put("horse", "말");
        images.put("horse", R.drawable.horse);


        keys = words.keySet().toArray(new String[0]);

        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
        // SharedPreferences에서 진행 상황을 불러옵니다.
        SharedPreferences sharedPreferences = getSharedPreferences("AnimalCardProgress", MODE_PRIVATE);
        currentIndex = sharedPreferences.getInt("AnimalCardProgress", 0);

        Button button = findViewById(R.id.nextbutton);
        button.setOnClickListener(v -> showCard());
    }

    private void showCard() {
        if (currentIndex >= keys.length) {
            tts.speak("끝", TextToSpeech.QUEUE_FLUSH, null, "EndSpeak");
            return;
        }

        String word = keys[currentIndex];
        String meaning = words.get(word);
        Integer imageResource = images.get(word);  // 이미지 리소스 가져오기

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        TextView progressTextView = findViewById(R.id.progressTextView);
        ImageView animalImageView = findViewById(R.id.animalImageView);

        wordTextView.setText(word);
        meaningTextView.setText(meaning);
        progressTextView.setText((currentIndex + 1) + " / " + keys.length);

        if (imageResource != null) {  // null 체크
            animalImageView.setImageResource(imageResource);
        }

        tts.speak(meaning, TextToSpeech.QUEUE_FLUSH, null, "WordSpeak");

        currentIndex = (currentIndex + 1) % keys.length;

        // SharedPreferences에 진행 상황을 저장합니다.
        SharedPreferences sharedPreferences = getSharedPreferences("AnimalCardProgress", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 진행상황을 저장합니다.
        editor.putInt("AnimalCardProgress", currentIndex);
        editor.apply(); //

        currentIndex = (currentIndex + 1) % keys.length;
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
