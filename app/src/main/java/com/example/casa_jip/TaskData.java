package com.example.casa_jip;

import android.os.Build;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TaskData implements Serializable {
    private String taskMessage;
    private boolean taskBool;
    private Date dueDate;


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

    public Date getDueDate() {
        return dueDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate.getTime();
    }
}
