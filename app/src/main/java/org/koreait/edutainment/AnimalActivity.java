package org.koreait.edutainment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Locale;

public class AnimalActivity extends AppCompatActivity {

    private HashMap<String, String> words = new HashMap<>();
    private HashMap<String, String> images = new HashMap<>();
    private String[] keys;
    private TextToSpeech tts;

    private int currentIndex = 0;

    // Firebase Realtime Database에 연결
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    // Firebase Storage에 연결
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        // 단어와 이미지 이름을 Firebase Realtime Database에서 불러옴
        mDatabase.child("words").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // dataSnapshot 객체에는 Firebase Realtime Database의 데이터가 포함되어 있습니다.
                words = (HashMap<String, String>) dataSnapshot.getValue();
                keys = words.keySet().toArray(new String[0]);
                showCard();  // 단어를 불러온 후 카드를 보여줍니다.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터를 읽는 데 실패했을 때 호출됩니다.
            }
        });

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
        String imageName = words.get(word + "_image");

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        TextView progressTextView = findViewById(R.id.progressTextView);
        ImageView animalImageView = findViewById(R.id.animalImageView);

        wordTextView.setText(word);
        meaningTextView.setText(meaning);
        progressTextView.setText((currentIndex + 1) + " / " + keys.length);

        // Firebase Storage에서 이미지 불러오기
        storage.getReference().child("animal/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // 파일 URL 가져오기 성공
                String url = uri.toString();
                // Glide 라이브러리를 사용하여 이미지 뷰에 이미지 로드
                Glide.with(AnimalActivity.this).load(url).into(animalImageView);
            }
        }).addOnFailureListener(exception -> {
            // 파일 URL 가져오기 실패
        });

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
