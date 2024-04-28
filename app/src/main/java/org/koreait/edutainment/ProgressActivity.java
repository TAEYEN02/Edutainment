package org.koreait.edutainment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {
    Button nextButton;
    TextView activityNameTextView;
    TextView progressTextView; // 진행률을 표시할 TextView
    PieChart pieChart;

    // Activity 이름 배열
    String[] activityNames = {"동물", "가족", "자연", "사물", "이동수단", "음식"};
    int currentActivityIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        nextButton = findViewById(R.id.nextButton);
        activityNameTextView = findViewById(R.id.activityNameTextView);
        progressTextView = findViewById(R.id.progressTextView); // 진행률 TextView 초기화
        pieChart = findViewById(R.id.pieChart);

        nextButton.setOnClickListener(v -> {
            // 다음 Activity의 진행 상황을 가져오는 코드
            currentActivityIndex = (currentActivityIndex + 1) % activityNames.length;

            // 현재 표시되고 있는 Activity의 이름을 TextView에 설정
            String currentActivityName = activityNames[currentActivityIndex];
            activityNameTextView.setText(currentActivityName);

            // PieChart 업데이트
            updatePieChart();
        });

        // 초기 PieChart 설정
        updatePieChart();
    }

    private void updatePieChart() {
        // SharedPreferences에서 각 Activity의 진행 상황을 불러옵니다.
        SharedPreferences sharedPreferences = getSharedPreferences("Progress", MODE_PRIVATE);
        float[] segmentValues = new float[]{
                sharedPreferences.getInt("AnimalActivityProgress", 0),
                sharedPreferences.getInt("FamilyActivityProgress", 0),
                sharedPreferences.getInt("NatureActivityProgress", 0),
                sharedPreferences.getInt("ThingsActivityProgress", 0),
                sharedPreferences.getInt("RideActivityProgress", 0),
                sharedPreferences.getInt("FoodActivityProgress", 0)
        };

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (float value : segmentValues) {
            entries.add(new PieEntry(value));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Activities");

        // 강조 색상 및 기본 색상 설정
        int highlightColor = Color.parseColor("#FF9191"); // 강조 색상
        int[] colors = new int[]{
                Color.parseColor("#FADADD"),
                Color.parseColor("#FFE4E1"),
                Color.parseColor("#FFDAB9"),
                Color.parseColor("#FFFACD"),
                Color.parseColor("#E6E6FA"),
                Color.parseColor("#D8BFD8")
        };

        // 현재 선택된 Activity에 대한 색상 강조
        colors[currentActivityIndex] = highlightColor;

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        // 진행률 표시
        float progress = segmentValues[currentActivityIndex];
        progressTextView.setText(String.format("%d%% 완료", (int) progress));
    }
}
