package com.manish.cipher_pro;

public class users {

    private String Username;
    private String Email;
    private String Password;

    public users() {

    }

    public users(String username, String email) {
        this.Username = username;
        this.Email = email;
    }

    public users(String username, String email, String password) {
        this.Username = username;
        this.Email = email;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
