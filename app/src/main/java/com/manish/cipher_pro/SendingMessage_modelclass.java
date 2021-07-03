package com.manish.cipher_pro;

import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendingMessage_modelclass {
    String Url;
    String Key;
    String Iv;
    String Currentuser_email;
    String Date;

    public SendingMessage_modelclass() {

    }

    public SendingMessage_modelclass(String url, String key, String iv, String currentuser_email, String date) {
        this.Url = url;
        this.Key = key;
        this.Iv = iv;
        this.Currentuser_email = currentuser_email;
        this.Date = date;
    }

    public String getUrl() {
        return Url;
    }

    public String getKey() {
        return Key;
    }

    public String getIv() {
        return Iv;
    }

    public String getCurrentuser_email() {
        return Currentuser_email;
    }

    public String getDate() {
        return Date;
    }
}
