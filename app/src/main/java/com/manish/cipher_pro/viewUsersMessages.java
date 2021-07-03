package com.manish.cipher_pro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class viewUsersMessages extends AppCompatActivity {
    String UID;

    FirebaseUser currentuser;

    RecyclerView recyclerView;
    Adapter_viewUsers adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users_messages);

        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        UID = currentuser.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model_class_useremailCardView> options =
                new FirebaseRecyclerOptions.Builder<Model_class_useremailCardView>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Chats").child(UID), Model_class_useremailCardView.class)
                        .build();

        adapter = new Adapter_viewUsers(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}