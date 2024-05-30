package org.koreait.edutainment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static boolean SoundCheck = true;   // 앱이 시작되면 음악 스위치를 켠다.
    String class_name = MusicService.class.getName();
    TextView sign;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onPostCreate 대신 onCreate 사용
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 및 리스너 설정
        setUpButtons();

        // 음악 서비스 시작 조건 확인 및 시작
        boolean isServiceRunningCheck = isServiceRunningCheck(class_name);
        if (savedInstanceState != null) {
            // 액티비티의 상태를 복원합니다.
            userName = savedInstanceState.getString("userName");
        } else {
            // Intent로부터 사용자의 이름을 받습니다.
            userName = getIntent().getStringExtra("userName");
        }

        // 화면에 사용자의 이름을 표시합니다.
        TextView greeting = findViewById(R.id.greeting);
        greeting.setText("안녕하세요, " + userName + "님!");
    }

    private void setUpButtons() {
        sign = findViewById(R.id.studyStatusButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProgressActivity.class);
            startActivity(intent);
        });

        sign = findViewById(R.id.wordCardButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        });

        sign = findViewById(R.id.gameButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameListActivity.class);
            startActivity(intent);
        });

        sign = findViewById(R.id.readingPracticeButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadListActivity.class);
            startActivity(intent);
        });

        Button Btn_Setting = findViewById(R.id.Btn_Setting);
        Btn_Setting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 음악 서비스 시작 조건 확인 및 시작
        boolean isServiceRunningCheck = isServiceRunningCheck(class_name);
        if (SoundCheck && !isServiceRunningCheck) {    // 스위치가 켜져있는데 서비스가 꺼져 있으면 음악을 킨다.
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        }
    }

    public boolean isServiceRunningCheck(String class_name) {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (class_name.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 사용자의 이름을 액티비티의 상태로 저장합니다.
        outState.putString("userName", userName);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 액티비티의 상태를 복원합니다.
        userName = savedInstanceState.getString("userName");
    }
}
