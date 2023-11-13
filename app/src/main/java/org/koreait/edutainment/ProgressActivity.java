package org.koreait.edutainment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ProgressBar progressBar = findViewById(R.id.progressBar);

// 진행 상황 설정 (0-100)
        progressBar.setProgress(50);
    }

}