package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class PlayActivity extends AppCompatActivity {
   TextView sign;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_play);

      sign = findViewById(R.id.StartButton);
      sign.setOnClickListener(v -> {
         Intent intent = new Intent(this, StartExActivity.class);
         startActivity(intent);
      });
      sign = findViewById(R.id.STTbutton);
      sign.setOnClickListener(v -> {
         Intent intent = new Intent(this,STTActivity.class);
         startActivity(intent);
      });

   }
}