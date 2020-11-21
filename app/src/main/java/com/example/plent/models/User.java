package com.example.plent.models;

public class User {
    private String name;
    private String email;
    private String studentId;
    private String password;
    private String id;
    private int permission; // 0 normal, 1 creator

    public int getPermission() {
        return permission;
    }
    public void setPermission(int permission) {
        this.permission = permission;
    }
    public User(String name, String email, String studentId, String password) {
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void removePassword() {
        this.password = "";
    }

}