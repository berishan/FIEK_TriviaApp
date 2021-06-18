package com.unipr.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnResetPw;
    private ProgressBar progressBar;
    private TextView twCancel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_password);
        super.onCreate(savedInstanceState);

        etEmail = findViewById(R.id.etEmail);
        btnResetPw = findViewById(R.id.btnResetPw);
        progressBar = findViewById(R.id.progressBar3);
        twCancel = findViewById(R.id.twCancel);
        mAuth = FirebaseAuth.getInstance();


        btnResetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        twCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });

    }

    private void resetPassword(){

        String email = etEmail.getText().toString().trim();
        if(email.isEmpty()){
            etEmail.setError(getString(R.string.email_required));
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getString(R.string.email_valid));
        }
         // TODO progressBar
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), getString(R.string.reset_pw_email), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.smth_wrong), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}