package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    private TextView greetingTextView;
    private FirebaseAuth mAuth;
    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greetingTextView = findViewById(R.id.greeting);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(name).child("name")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.getValue(String.class);
                            if (name != null) {
                                String greeting = getGreeting();
                                greetingTextView.setText(name + "!! " + greeting);
                            } else {
                                greetingTextView.setText("환영합니다!");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "Failed to read value.", databaseError.toException());
                            greetingTextView.setText("데이터를 불러오는 데 실패했습니다.");
                        }
                    });
        } else {
            greetingTextView.setText("환영합니다!");
        }

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
        sign = findViewById(R.id.readingPracticeButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadActivity.class);
            startActivity(intent);
        });
        sign = findViewById(R.id.gameButton);
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameListActivity.class);
            startActivity(intent);
        });


    }

    private String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 7 && hour < 12) {
            greeting = "안녕하세요! 좋은 아침입니다!!";
        } else if (hour >= 12 && hour < 16) {
            greeting = "점심맛있게 먹었어요? 뭐 먹었어요?";
        } else {
            greeting = "오늘 하루는 어땠어요?";
        }

        return greeting;
    }
}
