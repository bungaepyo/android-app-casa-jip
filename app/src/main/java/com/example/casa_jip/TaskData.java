package com.example.casa_jip;

import android.widget.CheckBox;

import java.io.Serializable;

public class TaskData implements Serializable {
    private String taskMessage;
    private boolean taskBool;

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }

    public boolean getTaskBoolean() {
        if (taskBool) {
            return true;
        } else {
            return false;
        }
    }

    public void setTaskBoolean(boolean taskBool) {
        this.taskBool = taskBool;
    }
}
