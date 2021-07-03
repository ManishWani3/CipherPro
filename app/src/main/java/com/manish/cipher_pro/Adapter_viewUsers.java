package com.manish.cipher_pro;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class Adapter_viewUsers extends FirebaseRecyclerAdapter<Model_class_useremailCardView, Adapter_viewUsers.myviewholder> {

    public Adapter_viewUsers(@NonNull FirebaseRecyclerOptions<Model_class_useremailCardView> options) {
        super(options);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull Model_class_useremailCardView model) {
        final String url1 = model.getUrl();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url1));
                (v.getContext()).startActivity(intent);
            }
        });
        holder.sender_email.setText(model.getCurrentuser_email());
        holder.doc_url.setText(model.getUrl());
        holder.sec_key.setText(model.getKey());
        holder.sec_iv.setText(model.getIv());
        holder.sending_date.setText(model.getDate());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayout, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView sender_email, doc_url, sec_key, sec_iv, sending_date;
        View view;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            sender_email = (TextView) itemView.findViewById(R.id.sender_email);
            doc_url = (TextView) itemView.findViewById(R.id.doc_url);
            sec_key = (TextView) itemView.findViewById(R.id.sec_key);
            sec_iv = (TextView) itemView.findViewById(R.id.sec_iv);
            sending_date = (TextView) itemView.findViewById(R.id.sending_date);

            view = itemView;
        }
    }

    class myviewholderTwo extends RecyclerView.ViewHolder {
        TextView sender_email, cipher_text, sec_key, sec_iv, sending_date;
        View view;

        public myviewholderTwo(@NonNull View itemView) {
            super(itemView);
            sender_email = (TextView) itemView.findViewById(R.id.sender_email);
            cipher_text = (TextView) itemView.findViewById(R.id.cipher_text);
            sec_key = (TextView) itemView.findViewById(R.id.sec_key);
            sec_iv = (TextView) itemView.findViewById(R.id.sec_iv);
            sending_date = (TextView) itemView.findViewById(R.id.sending_date);

            view = itemView;
        }
    }




}