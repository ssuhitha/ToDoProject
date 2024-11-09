package com.example.todo;

// Task.java
public class Task {
    public int id;
    public String name;
    public String deadline;
    public boolean isCompleted;

    public Task(int id, String name, String deadline, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
    }
}
