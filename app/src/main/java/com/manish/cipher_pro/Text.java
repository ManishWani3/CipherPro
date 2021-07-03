package com.manish.cipher_pro;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Text extends AppCompatActivity {
    EditText plain_text1, secretkey1, iv1;
    TextView cipher_text1;
    Button encr, copybutton,share_button;
    String encryptoutput, decryptoutput;
    RadioButton radio_encr, radio_decr;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
//        encr.setText("Encrypt Text");

        plain_text1 = findViewById(R.id.editTextTextMultiLine1);
        secretkey1 = findViewById(R.id.editTextTextPassword1);
        iv1 = findViewById(R.id.editTextTextPasswordI1);
        cipher_text1 = findViewById(R.id.textViewTextMultiLineCipher1);
        encr = findViewById(R.id.encro_text);
        radio_encr = findViewById(R.id.radio_encrypt);
        radio_decr = findViewById(R.id.radio_decrypt);
        copybutton = findViewById(R.id.copybutton);
        share_button=findViewById(R.id.share_button_text);
        mAuth = FirebaseAuth.getInstance();

        radio_encr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_encr.setTextColor(Color.WHITE);
                radio_decr.setTextColor(Color.rgb(128, 128, 128));
                encr.setText("Encrypt Text");
                plain_text1.setHint("Enter Plain-Text");
                secretkey1.setHint("Create Secret-Key");
                iv1.setHint("Create Initialization-Vector");
                plain_text1.setText("");
                secretkey1.setText("");
                iv1.setText("");
                cipher_text1.setText("");
                share_button.setEnabled(true);
                share_button.setVisibility(View.VISIBLE);
            }
        });

        radio_decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_decr.setTextColor(Color.WHITE);
                radio_encr.setTextColor(Color.rgb(128, 128, 128));
                encr.setText("Decrypt Text");
                plain_text1.setHint("Enter Cipher-Text");
                secretkey1.setHint("Enter Secret-Key");
                iv1.setHint("Enter Initialization-Vector");
                plain_text1.setText("");
                secretkey1.setText("");
                iv1.setText("");
                cipher_text1.setText("");
                share_button.setEnabled(false);
                share_button.setVisibility(View.GONE);
            }
        });

        copybutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipboardManager clipbord = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Cipher-pro", cipher_text1.getText().toString());
                clipbord.setPrimaryClip(clip);
                Toast.makeText(Text.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        encr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((encr.getText()) == "Encrypt Text") {
                    if (plain_text1.length() == 0) {
                        plain_text1.setError("Enter Text");
                    }
                    if (secretkey1.length() == 0) {
                        secretkey1.setError("Create Secret Key");
                    }
                    if (iv1.length() > 16 || iv1.length() < 16) {
                        iv1.setError("Length should be 16-Bytes");
                    }
                    try {
                        encryptoutput = Algorithm.encrypt(secretkey1.getText().toString(), iv1.getText().toString(), plain_text1.getText().toString());
                        Log.i("Encrypt button", "Cipher Text is: " + encryptoutput);
                        cipher_text1.setText(encryptoutput);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (plain_text1.length() == 0) {
                        plain_text1.setError("Enter Cipher-Text");
                    }
                    if (secretkey1.length() == 0) {
                        secretkey1.setError("Enter Secret Key");
                    }
                    if (iv1.length() > 16 || iv1.length() < 16) {
                        iv1.setError("Length should be 16-Bytes");
                    }
                    try {
                        decryptoutput = Algorithm.decrypt(secretkey1.getText().toString(), iv1.getText().toString(), plain_text1.getText().toString());
                        Log.i("Decrypt button", "Cipher Text is: " + decryptoutput);
                        cipher_text1.setText(decryptoutput);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Text.this, SendMessagesText.class);
                intent.putExtra("uploadedMessage_key", secretkey1.getText().toString());
                intent.putExtra("uploadedMessage_iv", iv1.getText().toString());
                intent.putExtra("uploadedMessage_ciphertext", cipher_text1.getText().toString());
                intent.putExtra("sendingUser_Email", mAuth.getCurrentUser().getEmail());
                startActivity(intent);
            }
        });
    }
    }



