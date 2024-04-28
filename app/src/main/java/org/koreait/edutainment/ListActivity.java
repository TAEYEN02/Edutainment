package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    ImageView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        sign = findViewById(R.id.anibtn1);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, AnimalActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.foodbtn2);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.ridebtn3);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, RideActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.familybtn5);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FamilyActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.thingsbtn7);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThingsActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.naturebtn7);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, NatureActivity.class);
            startActivity(intent);
        });
    }

}