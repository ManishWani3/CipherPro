package com.manish.cipher_pro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView text_button, video_button, audio_button, image_button, menu_back;
    TextView textView2, user_id;
    Animation home_menu;
    LinearLayout menu;
    Button logoutButton, received;
    String UID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        animation
        menu_back = findViewById(R.id.menu_back);
        textView2 = findViewById(R.id.textView2);
        logoutButton = findViewById(R.id.logoutButton);
        received = findViewById(R.id.received);
        user_id = findViewById(R.id.user_id);
        home_menu = AnimationUtils.loadAnimation(this, R.anim.homescreen_textview);
        menu = findViewById(R.id.menu);

        menu_back.animate().alpha(1).setDuration(1500);
        textView2.startAnimation(home_menu);
        menu.startAnimation(home_menu);
        logoutButton.startAnimation(home_menu);
        received.startAnimation(home_menu);
        user_id.startAnimation(home_menu);

        //   code
        text_button = findViewById(R.id.text_button);
        video_button = findViewById(R.id.video_button);
        audio_button = findViewById(R.id.audio_button);
        image_button = findViewById(R.id.image_button);
        mAuth = FirebaseAuth.getInstance();

        UID = mAuth.getCurrentUser().getUid();
        user_id.setText(UID);

        text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent text_intent = new Intent(MainActivity.this, Text.class);
                startActivity(text_intent);
            }
        });

        video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent video_intent = new Intent(MainActivity.this, Video.class);
                startActivity(video_intent);
            }
        });
        audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent audio_intent = new Intent(MainActivity.this, Audio.class);
                startActivity(audio_intent);
            }
        });
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_intent = new Intent(MainActivity.this, Image.class);
                startActivity(image_intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, viewUsersMessages.class);
                startActivity(intent);
            }
        });
    }

}
