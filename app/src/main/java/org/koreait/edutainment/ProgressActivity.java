package org.koreait.edutainment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // SharedPreferences에서 동물카드 액티비티의 진행 상황을 불러옵니다.
        SharedPreferences sharedPreferences = getSharedPreferences("AnimalCardProgress", MODE_PRIVATE);
        int animalCardProgress = sharedPreferences.getInt("AnimalCardProgress", 0);

        // SharedPreferences에서 가족카드 액티비티의 진행 상황을 불러옵니다.
        sharedPreferences = getSharedPreferences("FamilyCardProgress", MODE_PRIVATE);
        int familyCardProgress = sharedPreferences.getInt("FamilyCardProgress", 0);

        sharedPreferences = getSharedPreferences("FoodCardProgress", MODE_PRIVATE);
        int foodCardProgress = sharedPreferences.getInt("FoodCardProgress", 0);

        sharedPreferences = getSharedPreferences("RideCardProgress", MODE_PRIVATE);
        int rideCardProgress = sharedPreferences.getInt("RideCardProgress", 0);

        // 진행상황을 ProgressBar에 표시합니다.
        ProgressBar animalCardProgressBar = findViewById(R.id.animalCardProgressBar);
        animalCardProgressBar.setProgress(animalCardProgress);
        animalCardProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(this, AnimalActivity.class);
            startActivity(intent);
        });


        ProgressBar familyCardProgressBar = findViewById(R.id.familyCardProgressBar);
        familyCardProgressBar.setProgress(familyCardProgress);
        familyCardProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FamilyActivity.class);
            startActivity(intent);
        });

        ProgressBar foodCardProgressBar = findViewById(R.id.foodCardProgressBar);
        foodCardProgressBar.setProgress(foodCardProgress);
        foodCardProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        });


        ProgressBar rideCardProgressBar = findViewById(R.id.rideCardProgressBar);
        rideCardProgressBar.setProgress(rideCardProgress);
        rideCardProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(this, RideActivity.class);
            startActivity(intent);
        });

    }
}