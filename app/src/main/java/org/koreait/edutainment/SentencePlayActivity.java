package org.koreait.edutainment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;


public class SentencePlayActivity extends AppCompatActivity {
    WordDBHelper sqLiteHelper;
    ImageDBHelper imageDBHelper;
    TextView sentenceTextView;
    TextView instructionTextView;
    Button startButton;
    private int currentIndex = 0;
    private TextToSpeech tts;
    private ImageView ImageView1, ImageView2, ImageView3, ImageView4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentenceplay);
        Context context = this;

        sqLiteHelper = new WordDBHelper(this);
        imageDBHelper = new ImageDBHelper(this);

        if (imageDBHelper.getImage("bear") == null) {
            Bitmap bear = BitmapFactory.decodeResource(getResources(), R.drawable.bear);
            imageDBHelper.addImage("bear", bear);
        }
        if (imageDBHelper.getImage("bag") == null) {
            Bitmap bag = BitmapFactory.decodeResource(getResources(), R.drawable.bag);
            imageDBHelper.addImage("bag", bag);
        }
        if (imageDBHelper.getImage("mom") == null) {
            Bitmap mom = BitmapFactory.decodeResource(getResources(), R.drawable.mom);
            imageDBHelper.addImage("mom", mom);
        }
        if (imageDBHelper.getImage("apple") == null) {
            Bitmap apple = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            imageDBHelper.addImage("apple", apple);
        }
        if (imageDBHelper.getImage("car") == null) {
            Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.car);
            imageDBHelper.addImage("car", car);
        }
        if (imageDBHelper.getImage("airplane") == null) {
            Bitmap airplane = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
            imageDBHelper.addImage("airplane", airplane);
        }
        if (imageDBHelper.getImage("helicopter") == null) {
            Bitmap helicopter = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
            imageDBHelper.addImage("helicopter", helicopter);
        }
        if (imageDBHelper.getImage("watermelon") == null) {
            Bitmap watermelon = BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
            imageDBHelper.addImage("watermelon", watermelon);
        }
        if (imageDBHelper.getImage("block") == null) {
            Bitmap block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
            imageDBHelper.addImage("block", block);
        }
        if (imageDBHelper.getImage("carrot") == null) {
            Bitmap carrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);
            imageDBHelper.addImage("carrot", carrot);
        }
        if (imageDBHelper.getImage("cucumber") == null) {
            Bitmap cucumber = BitmapFactory.decodeResource(getResources(), R.drawable.cucumber);
            imageDBHelper.addImage("cucumber", cucumber);
        }
        if (imageDBHelper.getImage("cactus") == null) {
            Bitmap cactus = BitmapFactory.decodeResource(getResources(), R.drawable.cactus);
            imageDBHelper.addImage("cactus", cactus);
        }
        if (imageDBHelper.getImage("maple") == null) {
            Bitmap maple = BitmapFactory.decodeResource(getResources(), R.drawable.maple);
            imageDBHelper.addImage("maple", maple);
        }
        if (imageDBHelper.getImage("cactus") == null) {
            Bitmap cactus = BitmapFactory.decodeResource(getResources(), R.drawable.cactus);
            imageDBHelper.addImage("cactus", cactus);
        }
        if (imageDBHelper.getImage("elephant") == null) {
            Bitmap elephant = BitmapFactory.decodeResource(getResources(), R.drawable.elephant);
            imageDBHelper.addImage("elephant", elephant);

        }
        if (imageDBHelper.getImage("banana") == null) {
            Bitmap banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
            imageDBHelper.addImage("banana", banana);

        }


        sentenceTextView = findViewById(R.id.sentenceTextView);  //문장
        instructionTextView = findViewById(R.id.instructionTextView);
        startButton = findViewById(R.id.startbutton);


        ImageView1 = findViewById(R.id.image1);
        ImageView2 = findViewById(R.id.image2);
        ImageView3 = findViewById(R.id.image3);
        ImageView4 = findViewById(R.id.image4);

        // TTS를 설정
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });


        WordDBHelper wordDBHelper = new WordDBHelper(context);
        ArrayList<String> sentenceList = wordDBHelper.getSentences();

        // 문장에 맞는 그림을 눌렀을 때 다음 문제로 넘어가는 처리
        View.OnClickListener imageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++; // 다음 문장으로 이동
                if (currentIndex < sentenceList.size()) {
                    // 문장이 남아 있다면 다음 문장과 그림을 설정하고 보여줌
                    String nextSentence = sentenceList.get(currentIndex);
                    byte[] nextImageBytes = getImageFromImagedbForSentence(nextSentence);
                    Bitmap nextBitmap = BitmapFactory.decodeByteArray(nextImageBytes, 0, nextImageBytes.length);
                    ImageView1.setImageBitmap(nextBitmap);
                    ImageView2.setImageBitmap(nextBitmap);
                    ImageView3.setImageBitmap(nextBitmap);
                    ImageView4.setImageBitmap(nextBitmap);
                } else {
                    // 모든 문장을 다 보았을 경우 초기화
                    currentIndex = 0;
                    String firstName = sentenceList.get(currentIndex);
                    byte[] firstImageBytes = getImageFromImagedbForSentence(firstName);
                    Bitmap firstBitmap = BitmapFactory.decodeByteArray(firstImageBytes, 0, firstImageBytes.length);
                    ImageView1.setImageBitmap(firstBitmap);
                    ImageView2.setImageBitmap(firstBitmap);
                    ImageView3.setImageBitmap(firstBitmap);
                    ImageView4.setImageBitmap(firstBitmap);
                }
            }
        };


        ImageView1.setOnClickListener(imageClickListener);
        ImageView2.setOnClickListener(imageClickListener);
        ImageView3.setOnClickListener(imageClickListener);
        ImageView4.setOnClickListener(imageClickListener);

    }

    private byte[] getImageFromImagedbForSentence(String sentence) {
        // 문장에 해당하는 이미지 이름을 가져옵니다.
        // 이 예제에서는 문장 자체를 이미지 이름으로 사용합니다.
        // 실제로는 문장을 분석하여 해당하는 이미지 이름을 결정해야 할 수 있습니다.
        String imageName = sentence;

        // 데이터베이스에서 이미지를 가져옵니다.
        Bitmap image = imageDBHelper.getImage(imageName);

        // 이미지를 byte 배열로 변환합니다.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}









