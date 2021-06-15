package com.unipr.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unipr.triviaapp.entities.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etLastName, etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }


    public void registerUser() {
        String name = etName.getEditableText().toString().trim();
        String lastName = etLastName.getEditableText().toString().trim();
        String email = etEmail.getEditableText().toString().trim();
        String password = etPassword.getEditableText().toString().trim();

        if (name.isEmpty()) {
            etName.setError(getString(R.string.name_required));
            etName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            etLastName.setError(getString(R.string.lastname_required));
            etLastName.requestFocus();
            return;
        }
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
//        if (password.length() < 7) {
//            etPassword.setError("Password's length should be greater than 7");
//            etPassword.requestFocus();
//            return;
//        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(name, lastName, email);

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_LONG).show();
                        mAuth.getCurrentUser().sendEmailVerification();
                    } else
                    {
                        Toast.makeText(RegisterActivity.this, getString(R.string.register_fai), Toast.LENGTH_LONG).show();
                    }
                });
            } else
            {
                Toast.makeText(RegisterActivity.this, getString(R.string.register_fail_uc), Toast.LENGTH_LONG).show();
            }
        });

    }
}