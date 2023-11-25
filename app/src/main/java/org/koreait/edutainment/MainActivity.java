package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView sign;
    @Override
    protected void onPostCreate(Bundle saveInstanceState) {
        super.onPostCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        sign = findViewById(R.id.ProgressButton);
        sign.setOnClickListener(v ->{
            Intent intent = new Intent(this,ProgressActivity.class);
            startActivity(intent);
        });

        sign = findViewById(R.id.CardButton);
        sign.setOnClickListener(v ->{
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.PlayButton);
        sign.setOnClickListener(v ->{
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        });
    }

}