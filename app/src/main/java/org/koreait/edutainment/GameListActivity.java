package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameListActivity extends AppCompatActivity {
    TextView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);


        sign = findViewById(R.id.wordbtn);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, AniPlayActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.photobtn);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, SentencePlayActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.setencebtn);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, SentenceGameActivity.class);
            startActivity(intent);
        });

    }
}
