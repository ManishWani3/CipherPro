package com.manish.cipher_pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btnlogin;
    TextView textViewSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog mLodingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnlogin = findViewById(R.id.btnlogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        mAuth = FirebaseAuth.getInstance();
        mLodingBar = new ProgressDialog(LoginActivity.this);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });
    }

    private void checkCrededentials() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Enter correct Email-Id ");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "Password must be 7-Character");
        } else {
            mLodingBar.setTitle("Login");
            mLodingBar.setMessage("Checking your credentials");
            mLodingBar.setCanceledOnTouchOutside(false);
            mLodingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        mLodingBar.dismiss();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
                        mLodingBar.dismiss();
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}