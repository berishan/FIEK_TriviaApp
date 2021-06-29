package com.unipr.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipr.triviaapp.entities.User;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import java.util.UUID;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity{

    private Button btnLogin;
    private EditText etEmail, etPassword;
    private TextView twRegister, twForgotPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox cbRememberMe;


    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String firebaseUserId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        twRegister = findViewById(R.id.twRegister);
        btnLogin = findViewById(R.id.btnLogin);
        twForgotPassword = findViewById(R.id.twForgotPassword);
        progressBar = findViewById(R.id.progressBar2);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        String email = getIntent().getStringExtra(ExtrasHelper.EMAIL);
        if(email != null){
            etEmail.setText(email);
        }

        //Task<DataSnapshot> a = FirebaseDatabase.getInstance().getReference("Users")
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        twRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        twForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();



                }
                else if(!buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }


    public void login() {

        String email = etEmail.getEditableText().toString().trim();
        String password = etPassword.getEditableText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.email_required));
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.email_valid));
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.password_required));
            etPassword.requestFocus();
            return;
        }
        // TODO passwordValidation

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task ->
        {

            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(!user.isEmailVerified()){
                    Toast.makeText(this.getApplicationContext(), getString(R.string.login_fail_verify), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Intent intent = new Intent(this.getApplicationContext(), CoreActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(this.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }

        });

    }
}