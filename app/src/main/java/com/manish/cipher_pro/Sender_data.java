package com.manish.cipher_pro;


public class Sender_data {
    String url;
    String key;
    String iv;
    String email;

    public Sender_data() {

    }

    public Sender_data(String url, String key, String iv, String email) {
        this.url = url;
        this.key = key;
        this.iv = iv;
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getIv() {
        return iv;
    }

    public String getEmail() {
        return email;
    }
}
