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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    EditText inputUsername, inputEmail, inputPassword, inputConformPassword;
    Button btnRegister;
    TextView alreadyHaveAccount;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private ProgressDialog mLodingBar;
    private String USER = "Users";
    private users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        btnRegister = findViewById(R.id.btnRegister);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mLodingBar = new ProgressDialog(RegisterActivity.this);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });
    }

    private void checkCrededentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conformpassword = inputConformPassword.getText().toString();

        user = new users(username, email, password);

        if (username.isEmpty() || username.length() < 7) {
            showError(inputUsername, "Your Username is not valid");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Enter valid Email-Id ");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "Password must be 7-Character");
        } else if (conformpassword.isEmpty() || !conformpassword.equals(password)) {
            showError(inputConformPassword, "Re-Enter Password");
        } else {
            mLodingBar.setTitle("Registration");
            mLodingBar.setMessage("Checking your credentials");
            mLodingBar.setCanceledOnTouchOutside(false);
            mLodingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(RegisterActivity.this, "Registration Successfull.", Toast.LENGTH_SHORT).show();
                        FirebaseUser curr_user = mAuth.getCurrentUser();
                        updateUI(curr_user);
                        mLodingBar.dismiss();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Registration Failed "+task.getException().toString(), Toast.LENGTH_LONG).show();
                        mLodingBar.dismiss();
                    }
                }
            });
        }

    }

    public void updateUI(FirebaseUser currentUser) {
//        String keyID = mDatabase.push().getKey();
        String UID = currentUser.getUid();
        mDatabase.child(USER).child(UID).setValue(user);
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}