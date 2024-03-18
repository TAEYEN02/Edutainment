package org.koreait.edutainment;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReadActivity extends AppCompatActivity {

    private static final double THRESHOLD = 70.0;
    private HashMap<String, String> words = new HashMap<>();
    private HashMap<String, Integer> images = new HashMap<>();
    private String[] keys;
    private SpeechRecognizer mRecognizer;
    private Intent intent;
    private TextToSpeech tts; // TextToSpeech 객체 추가
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        // 단어 DB를 여기에 붙이면 될 것 같은데..
        words.put("elephant", "코끼리");
        images.put("elephant", R.drawable.elephant);
        words.put("lion", "사자");
        images.put("lion", R.drawable.lion);


        keys = words.keySet().toArray(new String[0]);

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
    }

    private void speakCard() {
        String word = keys[currentIndex];

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        tts.speak(words.get(word), TextToSpeech.QUEUE_FLUSH, params, "UniqueID");

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

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
                String meaning = words.get(word);
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    for (String match : matches) {
                        if (match.equals(meaning)) {
                            // 발음이 정확합니다.
                            tts.speak("발음이 정확합니다.", TextToSpeech.QUEUE_FLUSH, null);
                            // ETRI API를 호출하여 발음 평가
                            evaluatePronunciation(match, meaning);
                            break;
                        } else {
                            // 발음이 정확하지 않습니다. 다시 시도해주세요.
                            tts.speak("발음이 정확하지 않습니다. 다시 시도해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        mRecognizer.startListening(intent);
    }

    private void showCard() {
        String word = keys[currentIndex];
        String meaning = words.get(word);
        Integer imageResource = images.get(word);

        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        ImageView ImageView = findViewById(R.id.ImageView);

        wordTextView.setText(word);
        meaningTextView.setText(meaning);

        if (imageResource != null) {  // null 체크
            ImageView.setImageResource(imageResource);
        }
    }

    private void evaluatePronunciation(String userSpeech, String script) {
        // ETRI API를 호출하여 발음 평가
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Pronunciation";
        String accessKey = "44d0d74b-27bd-4538-9d2c-75c6b919a0ca"; // 발급받은 API Key 입력
        String languageCode = "korean"; // 언어 코드

        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("language_code", languageCode);
        argument.put("script", script); // 스크립트 사용

        request.put("access_key", accessKey);
        request.put("argument", argument);

        try {
            URL url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);

            String responBody = new String(buffer);

            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println(responBody);

            // API 응답 파싱 (수정 필요)
            Map<String, Object> response = gson.fromJson(responBody, Map.class);
            double score = Double.parseDouble(response.get("score").toString()); // 'score' 필드명 확인 필요

            // 발음 평가 결과에 따라 다음 단계 진행
            if (score >= THRESHOLD) {
                currentIndex = (currentIndex + 1) % keys.length; // 다음 카드로 넘어감
            } else {
                // 발음이 정확하지 않으므로 다시 시도
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("API 호출에 실패했습니다: " + e.getMessage());
            // 오류 처리 (선택적)
        }
    }
}



