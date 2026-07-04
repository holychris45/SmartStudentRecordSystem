package com.ssrmt.modules;

import com.ssrmt.modules.Person;

public class Student extends Person {
    private String program;
    private double gpa;
    private int level; // e.g., 100, 200, 300, 400

    public Student(String id, String fullName, String email, String program, int level, double gpa) {
        super(id, fullName, email); // Invoking parent constructor
        setProgram(program);
        setLevel(level);
        setGpa(gpa);
    }

    // Encapsulation with business logic validation
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public int getLevel() { return level; }
    public void setLevel(int level) {
        if(level < 100 || level > 400) {
            throw new IllegalArgumentException("Level must be between 100 and 400.");
        }
        this.level = level;
    }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    // Polymorphism: Overriding abstract method
    @Override
    public String getRoleDetails() {
        return String.format("Student enrolled in %s (Level %d) with a current GPA of %.2f", program, level, gpa);
    }
}
