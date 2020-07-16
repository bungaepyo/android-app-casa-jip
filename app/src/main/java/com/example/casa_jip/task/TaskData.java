package com.example.casa_jip.task;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;

public class TaskData implements Serializable {
    private boolean taskBool;
    private String taskMessage;
    private String dueDate;

    // note due is string and dueDate is a date Object

    public boolean getTaskBoolean() {
        return taskBool;
    }

    public void setTaskBoolean(boolean taskBool) {
        this.taskBool = taskBool;
    }


    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }

    public String getDue() {
        return dueDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDue(String dueDate) {
        this.dueDate = dueDate;
    }
}
