package org.koreait.edutainment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeInfoActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        EditText currentPassword = findViewById(R.id.currentPassword);
        EditText newEmail = findViewById(R.id.newEmail);
        EditText newPassword = findViewById(R.id.newPassword);
        EditText confirmNewPassword = findViewById(R.id.confirmNewPassword);
        EditText newName = findViewById(R.id.newName);
        Button changeEmailButton = findViewById(R.id.changeEmailButton);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        Button changeNameButton = findViewById(R.id.changeNameButton);

        changeEmailButton.setOnClickListener(v -> {
            String newEmailInput = newEmail.getText().toString();
            if (user != null) {
                user.updateEmail(newEmailInput)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeInfoActivity.this, "이메일이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeInfoActivity.this, "이메일 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        changePasswordButton.setOnClickListener(v -> {
            String currentPasswordInput = currentPassword.getText().toString();
            String newPasswordInput = newPassword.getText().toString();
            String confirmNewPasswordInput = confirmNewPassword.getText().toString();

            if (!newPasswordInput.equals(confirmNewPasswordInput)) {
                Toast.makeText(ChangeInfoActivity.this, "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else if (user != null && user.getEmail() != null && user.getClass().equals(currentPasswordInput)) {
                user.updatePassword(newPasswordInput)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeInfoActivity.this, "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeInfoActivity.this, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(ChangeInfoActivity.this, "현재 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        changeNameButton.setOnClickListener(v -> {
            String newNameInput = newName.getText().toString();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newNameInput)
                    .build();

            if (user != null) {
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeInfoActivity.this, "이름이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeInfoActivity.this, "이름 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}