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

        sign = findViewById(R.id.anibtn1);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, AniPlayActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.foodbtn2);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodPlayActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.ridebtn3);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, RidePlayActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.familybtn4);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FamilyPlayActivity.class);
            startActivity(intent);
        });
    }

}