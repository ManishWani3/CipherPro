package com.manish.cipher_pro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SendMessages extends AppCompatActivity {

    TextView url_view, key_view, iv_view;
    EditText receiver_email;
    Button send_button;
    String url, key, iv, currentuser_email, date, email_of_receiver;

    SendingMessage_modelclass sendingMessage_modelclass;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

//    DateTimeFormatter dtf;
//    LocalDateTime now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Chats");


        url_view = findViewById(R.id.url_view);
        key_view = findViewById(R.id.key_view);
        iv_view = findViewById(R.id.iv_view);
        receiver_email = findViewById(R.id.receiver_email);
        send_button = findViewById(R.id.send_button);

        email_of_receiver = receiver_email.getText().toString();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            url = bundle.getString("uploadedFile_url");
            key = bundle.getString("uploadedFile__key");
            iv = bundle.getString("uploadedFile_iv");
            currentuser_email = bundle.getString("sendingUser_Email");
        }
        url_view.setText(url);
        key_view.setText(key);
        iv_view.setText(iv);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd | hh:mm:ss a");
        date = format.format(today);

        sendingMessage_modelclass = new SendingMessage_modelclass(url, key, iv, currentuser_email, date);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = receiver_email.getText().toString();
                if (id.isEmpty() || id.length() < 28) {
                    showError(receiver_email, "Incorrect UID");
                } else if (id == mAuth.getCurrentUser().getUid().toString()) {
                    showError(receiver_email, "Incorrect UID");
                } else {
                    String keyID = mDatabase.push().getKey();
                    email_of_receiver = receiver_email.getText().toString();
                    mDatabase.child(email_of_receiver).child(keyID).setValue(sendingMessage_modelclass);
                    Toast.makeText(SendMessages.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SendMessages.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}