package com.manish.cipher_pro;

public class SendingMessageText_modelclass {

    String Ciphertext;
    String Key;
    String Iv;
    String Currentuser_email;
    String Date;
    String TM;


    public SendingMessageText_modelclass() {
    }

    public SendingMessageText_modelclass(String ciphertext, String key, String iv, String currentuser_email, String date) {
        Ciphertext = ciphertext;
        Key = key;
        Iv = iv;
        Currentuser_email = currentuser_email;
        Date = date;
        TM = "TM";
    }

    public String getCiphertext() {
        return Ciphertext;
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

    public String getTM() {
        return TM;
    }
}
