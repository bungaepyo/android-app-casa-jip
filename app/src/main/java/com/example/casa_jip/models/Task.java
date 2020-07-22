package com.example.casa_jip.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Task {

    public String uid;
    public String username;
    public String taskMessage;
    public boolean taskBool;
    public String dueDate;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String uid, String username, String taskMessage, boolean taskBool, String dueDate) {
        this.uid = uid;
        this.username = username;
        this.taskMessage = taskMessage;
        this.taskBool = taskBool;
        this.dueDate = dueDate;
    }

    // post to map
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("userAssigned", username);
        result.put("taskMessage", taskMessage);
        result.put("taskBool", taskBool);
        result.put("dueDate", dueDate);

        return result;
    }
}
