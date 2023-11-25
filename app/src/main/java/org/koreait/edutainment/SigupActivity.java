package org.koreait.edutainment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtname, mEtid, mEtpw, mEtpw2, msignAge; //회원가입 입력필드
    private TextView mEtback;
    private Button mBtpwcheck, mBtSignup; //버튼 생성


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Auth




        //뒤로 가기 버튼
        mEtback = findViewById(R.id.back);
        mEtback.setOnClickListener(v -> onBackPressed());

        //기입 항목
        mEtname = findViewById(R.id.signName);
        mEtid = findViewById(R.id.signID);
        mEtpw = findViewById(R.id.signPW);
        mEtpw2 = findViewById(R.id.signPW2);
        msignAge = findViewById(R.id.signAge);

        mBtSignup = findViewById(R.id.signupbutton);

        mBtSignup.setOnClickListener(view -> { //회원가입 처리 시작
            String email = mEtid.getText().toString();
            String password = mEtpw.getText().toString();
            String password2 = mEtpw2.getText().toString();
            String name = mEtname.getText().toString();
            int age = Integer.parseInt(msignAge.getText().toString());

            mDatabaseRef  = FirebaseDatabase.getInstance().getReference(email);
            User newUser = new User(email, password, password2, name,age);
            mDatabaseRef.child(email).setValue(newUser);
            Toast.makeText(SigupActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_LONG).show();


        });
    }
}