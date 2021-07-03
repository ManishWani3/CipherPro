package com.manish.cipher_pro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Date;

public class SendMessagesText extends AppCompatActivity {

    TextView ciphertext_view, key_view, iv_view;
    EditText receiver_email;
    Button send_button;
    String ciphertext, key, iv, currentuser_email, date, email_of_receiver;

    SendingMessageText_modelclass sendingMessageText_modelclass;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages_text);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Chats");

        ciphertext_view = findViewById(R.id.ciphertext_view);
        key_view = findViewById(R.id.key_view);
        iv_view = findViewById(R.id.iv_view);
        receiver_email = findViewById(R.id.receiver_email);
        send_button = findViewById(R.id.send_button);

        email_of_receiver = receiver_email.getText().toString();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ciphertext = bundle.getString("uploadedMessage_ciphertext");
            key = bundle.getString("uploadedMessage_key");
            iv = bundle.getString("uploadedMessage_iv");
            currentuser_email = bundle.getString("sendingUser_Email");
        }
        ciphertext_view.setText(ciphertext);
        key_view.setText(key);
        iv_view.setText(iv);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd | hh:mm:ss a");
        date = format.format(today);

        sendingMessageText_modelclass = new SendingMessageText_modelclass(ciphertext, key, iv, currentuser_email, date);

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
                    mDatabase.child(email_of_receiver).child(keyID).setValue(sendingMessageText_modelclass);
                    Toast.makeText(SendMessagesText.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SendMessagesText.this, MainActivity.class);
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
