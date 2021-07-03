package com.manish.cipher_pro;

import android.util.Log;

public class Model_class_useremailCardView {

    String CText;
    String currentuser_email;
    String url;
    String key;
    String iv;
    String date;
    String TM;

    Model_class_useremailCardView() {

    }

    // Code for Files Messages

    public Model_class_useremailCardView(String currentuser_email, String url, String key, String iv, String date) {
        this.currentuser_email = currentuser_email;
        this.date = date;
        this.iv = iv;
        this.key = key;
        this.url = url;
    }

    public String getCurrentuser_email() {
        return currentuser_email;
    }

    public void setCurrentuser_email(String currentuser_email) {
        this.currentuser_email = currentuser_email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //Code for files Messages

    //Code for Text Messages

    public Model_class_useremailCardView(String CText, String currentuser_email, String key, String iv, String date, String TM) {
        this.CText = CText;
        this.currentuser_email = currentuser_email;
        this.key = key;
        this.iv = iv;
        this.date = date;
        this.TM = TM;
    }

    public String getCText() {
        return CText;
    }

    public void setCText(String CText) {
        this.CText = CText;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    //Code for Text Messages
}
