package org.koreait.edutainment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FamilyActivity extends AppCompatActivity {
    public static final String COLUMN_PHOTO = "photo"; //이미지 저장할 필드
    DBHelper sqLiteHelper;
    TextView textView;
    Button button;
    SQLiteDatabase db;
    private Context context = this;
    private List<String> memberNames;
    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        button = findViewById(R.id.nextbutton);
        sqLiteHelper = new DBHelper(this);
        textView = findViewById(R.id.meaningTextView);
        byte[] photo;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext()); //멤버 이름 가져오기
                ArrayList<String> memberNames = dbHelper.getMemberNames();
                if (currentIndex < memberNames.size()) { //배열에서 다음 이름 가져오기
                    String nextName = memberNames.get(currentIndex);
                    textView.setText(nextName);
                    currentIndex++;


                } else {
                    currentIndex = 0;
                    textView.setText("모든 이름을 보여줬습니다.");

                }
            }
        });
    }
}
