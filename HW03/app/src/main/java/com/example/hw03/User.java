package com.example.hw03;

public class User {
    private String id;
    private String name;
    private String className;
    private double gpa;
    private int avatarResId;

    public User(String id, String name, String className, double gpa, int avatarResId) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.gpa = gpa;
        this.avatarResId = avatarResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getClassName() { return className; }
    public double getGpa() { return gpa; }
    public int getAvatarResId() { return avatarResId; }
}
