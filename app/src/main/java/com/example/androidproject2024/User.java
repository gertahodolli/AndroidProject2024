package com.example.androidproject2024;
public class User {
    public String name;
    public String surname;
    public String email;
    public String bio;
    public String location;
    public String contactNumber;

    // Default constructor required for Firebase
    public User() {
    }

    // Constructor with parameters
    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;

    }
}
