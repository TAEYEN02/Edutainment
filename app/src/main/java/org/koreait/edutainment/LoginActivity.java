package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // FirebaseAuth 객체 생성
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtId, mEtPw;

    private TextView mBtlogin;
    private TextView mBtsignup; //버튼 생성


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        mEtId = findViewById(R.id.editID);
        mEtPw = findViewById(R.id.ediPassword);

        mBtsignup = findViewById(R.id.signin);
        mBtlogin = findViewById(R.id.loginbutton);

        mBtlogin.setOnClickListener(view -> {
            String email = mEtId.getText().toString();
            String password = mEtPw.getText().toString();

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(email);
            mDatabaseRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // 데이터를 가져왔을 때 호출되는 메서드
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if(password.equals(user.password)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "로그인에 성공했습니다", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_LONG).show();

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // 데이터 가져오기가 실패했을 때 호출되는 메서드
                    System.out.println("Error: " + databaseError.getMessage());
                }
            });
        });
        mBtsignup = findViewById(R.id.signin);

        //회원가입 눌렀을때 화면 전환
        mBtsignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SigupActivity.class);
            startActivity(intent);
        });

    }
}