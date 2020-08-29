package com.example.todoapp;

public class Task {
    private String TaskName;
    private String TaskDueDate;

    public Task(String task, String duedate) {
        TaskName = task;
        TaskDueDate = duedate;
    }

    public String getTaskName() {
        return TaskName;
    }

    public String getTaskDueDate() {
        return TaskDueDate;
    }

}
