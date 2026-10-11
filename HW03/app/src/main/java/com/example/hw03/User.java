package com.example.hw03;

public class User {
    private String name;
    private String phone;
    private int avatarResId;

    public User(String name, String phone, int avatar) {
        this.name = name;
        this.phone = phone;
        this.avatarResId = avatar;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getAvatar() {
        return avatarResId;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatar(int avatar) {
        this.avatarResId = avatar;
    }
}
