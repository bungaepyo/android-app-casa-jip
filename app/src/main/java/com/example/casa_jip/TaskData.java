package com.example.casa_jip;

import android.widget.CheckBox;

import java.io.Serializable;

public class TaskData implements Serializable {
    private String username;
    private String taskMessage;
    private boolean checkBool;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }

    public boolean getTaskChecked() {
        if (checkBool) {
            return true;
        } else {
            return false;
        }
    }

    public void setTaskChecked(boolean checkBool) {
        this.checkBool = checkBool;
    }
}
