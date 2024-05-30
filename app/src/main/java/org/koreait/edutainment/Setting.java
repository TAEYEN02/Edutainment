package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button Btn_Home = (Button) findViewById(R.id.Btn_Home);

        Switch switchButton = findViewById(R.id.switch1);

        if (MainActivity.SoundCheck) {
            switchButton.setChecked(true);
        } else {
            switchButton.setChecked(false);

        }

        Btn_Home.setOnClickListener(v -> {

            Intent intent = new Intent(Setting.this, MainActivity.class);
            startActivity(intent);

        });

        Button changeInfoButton = findViewById(R.id.change_info_button);
        changeInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Setting.this, ChangeInfoActivity.class);
            startActivity(intent);
        });


        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                MainActivity.SoundCheck = true;
                // 음악 서비스 시작
                Intent intent = new Intent(Setting.this, MusicService.class);
                startService(intent);
            } else {
                MainActivity.SoundCheck = false;
                // 음악 서비스 중지
                Intent intent2 = new Intent(Setting.this, MusicService.class);
                stopService(intent2);
            }
        });

    }

}
