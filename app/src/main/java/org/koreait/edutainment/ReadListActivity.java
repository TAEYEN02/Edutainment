package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadListActivity extends AppCompatActivity {
    ImageView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_list);

        sign = findViewById(R.id.anibtn1);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.foodbtn2);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.ridebtn3);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, RideReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.familybtn5);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, FamilyReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.thingsbtn7);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThingReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.naturebtn7);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, NatureReadActivity.class);
            startActivity(intent);
        });
    }

}