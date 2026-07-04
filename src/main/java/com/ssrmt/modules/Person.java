package com.ssrmt.modules;

import java.io.Serializable;

// Demonstration of Abstraction
public abstract class Person implements Serializable {
    private String id;
    private String fullName;
    private String email;

    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    // Encapsulation: Setters and Getters with validation
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        if(fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.fullName = fullName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if(!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    // Abstract method to be overridden by subclasses (Polymorphism)
    public abstract String getRoleDetails();
}