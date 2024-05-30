package org.koreait.edutainment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FamilyReadActivity extends AppCompatActivity {

    private static final double THRESHOLD = 50.0;
    private final ArrayList<Bitmap> images = new ArrayList<>();
    ImageDBHelper imageDBHelper;
    DBHelper dbHelper;
    private String[] keys;
    private SpeechRecognizer mRecognizer;
    private Intent intent;
    private int currentIndex = 0;
    private ArrayList<String> words = new ArrayList<>();
    private TextToSpeech tts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_read);

        // SharedPreferences 객체를 가져옵니다.
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // SharedPreferences를 확인하여 currentIndex를 복원합니다.
        currentIndex = prefs.getInt("currentIndex", 0); // 기본값은 0입니다.

        imageDBHelper = new ImageDBHelper(this);
        dbHelper = new DBHelper(this);

        // 데이터베이스에서 단어와 이미지를 불러옵니다.
        words = dbHelper.getMemberNames(); // getAnimalNames 메소드를 호출합니다.
        for (String word : words) {
            String englishName = dbHelper.getEnglishName(word, "Members");
            if (englishName != null) {
                Bitmap image = imageDBHelper.getImage(englishName);
                if (image != null) {
                    images.add(image);
                }
            }
        }

        keys = words.toArray(new String[0]);

        Button button = findViewById(R.id.speakbutton);
        button.setOnClickListener(v -> speakCard());

        Button button1 = findViewById(R.id.nextbutton);
        button1.setOnClickListener(v -> showCard());

        // TextToSpeech 객체 초기화
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String userSpeech = matches.get(0); // 사용자의 발음을 가져옵니다.
                    String script = keys[currentIndex]; // 현재 단어를 가져옵니다.

                    // 별도의 스레드에서 ETRI API를 호출하여 발음을 평가합니다.
                    new Thread(() -> {
                        double score = evaluatePronunciation(userSpeech, script);

                        // 발음 평가 결과에 따라 다음 단계 진행
                        if (score >= THRESHOLD) {
                            runOnUiThread(() -> tts.speak("발음이 정확합니다.", TextToSpeech.QUEUE_FLUSH, null, "UniqueID"));
                        } else {
                            runOnUiThread(() -> tts.speak("발음이 정확하지 않습니다. 다시 시도해주세요.", TextToSpeech.QUEUE_FLUSH, null, "UniqueID"));
                        }
                    }).start();
                }
            }


            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // SharedPreferences.Editor 객체를 가져옵니다.
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        // currentIndex를 저장합니다.
        editor.putInt("currentIndex", currentIndex);
        editor.apply();
    }


    private void speakCard() {
        String words = keys[currentIndex];

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        tts.speak(words, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");

        // 인텐트 초기화
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer.startListening(intent);
    }


    private void showCard() {
        currentIndex = (currentIndex + 1) % keys.length; // 다음 카드로 넘어감

        String word = keys[currentIndex];
        String meaning = words.get(currentIndex);
        Bitmap imageResource = images.get(currentIndex);

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        ImageView ImageView = findViewById(R.id.imageView);

        wordTextView.setText(word);
        meaningTextView.setText(meaning);

        if (imageResource != null) {  // null 체크
            ImageView.setImageBitmap(imageResource);
        }
    }


    private double evaluatePronunciation(String userSpeech, String script) {
        // ETRI API를 호출하여 발음 평가
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Pronunciation";
        String accessKey = "1ad79f9f-3267-4d03-b33c-4d7d7974ebca"; // 발급받은 API Key 입력
        String languageCode = "korean"; // 언어 코드

        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("language_code", languageCode);
        argument.put("script", script);
        argument.put("user_speech", userSpeech);

        request.put("access_key", accessKey);
        request.put("argument", argument);

        double score = 0.0;
        try {
            URL url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes(StandardCharsets.UTF_8));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);

            String responBody = new String(buffer, 0, byteRead);

            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println(responBody);

            // API 응답 파싱
            Map response = gson.fromJson(responBody, Map.class);
            Map returnObject = (Map) response.get("return_object");
            if (returnObject != null) {
                score = ((Number) returnObject.get("score")).doubleValue();
                System.out.println("[score] " + score);  // 로그 추가
            } else {
                System.out.println("Score is null");  // 로그 추가
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("API 호출에 실패했습니다: " + e.getMessage());
            // 오류 처리 (선택적)
        }
        return score;
    }
}