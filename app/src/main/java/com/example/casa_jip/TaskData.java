package com.example.casa_jip;

import java.io.Serializable;

public class TaskData implements Serializable {
    private String username;
    private String taskMessage;


    public String getUsername() {
        return username;
    }

    public void setUsername(String task) {
        this.username = username;
    }

    public String getTaskOnline() {
        return taskMessage;
    }

    public void setTask(String task) {
        this.taskMessage = taskMessage;
    }
}
