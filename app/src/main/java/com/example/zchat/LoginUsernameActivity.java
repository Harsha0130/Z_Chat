package com.example.zchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.zchat.model.UserModel;
import com.example.zchat.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUsernameActivity extends AppCompatActivity {

    EditText usernameInput;
    Button letMeInBun;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);

        usernameInput = findViewById(R.id.login_username);
        letMeInBun = findViewById(R.id.login_let_me_in_btn);
        progressBar = findViewById(R.id.login_progress_bar);

        phoneNumber = getIntent().getExtras().getString("Phone");
        getUsername();

        letMeInBun.setOnClickListener((v->{
            setUsername();
        }));
    }


    void setUsername() {

        String username = usernameInput.getText().toString();
        if (username.isEmpty() || username.length()<3) {
            usernameInput.setError("Username length should be at least 3 characters");
            return;
        }
        setInProgress(true);

        if (userModel != null) {
            userModel.setUsername(username);
        }
        else {
            userModel = new UserModel(phoneNumber, username, Timestamp.now(),FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserdetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginUsernameActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }


    void getUsername() {
        setInProgress(true);
        FirebaseUtil.currentUserdetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if (task.isSuccessful()) {
                   userModel = task.getResult().toObject(UserModel.class);
                   if (userModel != null) {
                       usernameInput.setText(userModel.getUsername());
                   }
                }
            }
        });

    }

    void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            letMeInBun.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            letMeInBun.setVisibility(View.VISIBLE);
        }
    }
}