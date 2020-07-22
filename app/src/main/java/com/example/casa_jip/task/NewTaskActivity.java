package com.example.casa_jip.task;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casa_jip.R;
import com.example.casa_jip.models.User;
import com.example.casa_jip.models.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.casa_jip.BaseActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTaskActivity extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TaskData> taskList;
    private EditText EditText_taskMessage;
    private Button btn_add;
    private CheckBox CheckBox_taskChecked;
    private boolean taskBool;

    private static final String TAG = "NewTaskActivity";
    private static final String REQUIRED = "Required";
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = getView().findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        taskList = new ArrayList<>();
        mAdapter = new TaskAdapter(taskList, getActivity());
        recyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://casajip-ddbc9.firebaseio.com/");
        mDatabase = database.getReference();

        // add more tasks
        EditText_taskMessage = getView().findViewById(R.id.EditText_taskMessage);
        btn_add = getView().findViewById(R.id.btn_add);

        // is the task completed
        CheckBox_taskChecked = getView().findViewById(R.id.CheckBox_taskChecked);

        // when btn_add is clicked with text filled, it pushes the taskMessage
        btn_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void addTask() {
        final String taskMessage = EditText_taskMessage.getText().toString();
        Toast.makeText(this, "Adding...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // get user value
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Log.e(TAG, "User" + userId + "is unexpectedly null");
                    Toast.makeText(NewTaskActivity.this, "Error: could not fetch user.", Toast.LENGTH_SHORT).show();
                } else {
                    // add new task
                    addNewTask(userId, user.username, taskMessage)
                }
                // finish this activity, back to the main task
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // [START_EXCLUDE]

            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaskData task = dataSnapshot.getValue(TaskData.class);
                ((TaskAdapter) mAdapter).addTask(task);
            }

            @Override
            public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("taskChecked", dataSnapshot.getValue().toString());

                CheckBox_taskChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        taskBool = CheckBox_taskChecked.isChecked();
                        if (taskBool) {
                            TaskData updatedTask = dataSnapshot.getValue(TaskData.class);
                            updatedTask.setTaskBoolean(true);
                            mDatabase.push().setValue(updatedTask);
                        }
                    }
                });
            }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }


    private void addNewTask(String userId, String username, String taskMessage, boolean taskBool, String dueDate) {
        //create new post at /user-task/$userid/@postid and at
        // /tasks/$taskid simultaneously

        String key = mDatabase.child("tasks").push().getKey();
        Task task = new Task(userId, username, taskMessage, taskBool, dueDate);
        Map<String, Object> taskValues = task.toMap();

        Map<String, Object> childupdates = new HashMap<>();
        childupdates.put("/tasks" + key, taskValues);
        childupdates.put("/user-posts/" + userId + "/" + key,taskValues);

        mDatabase.updateChildren(childupdates);
    }

    /** This method keeps focus on the last item of the recyclerView */
    public void updateToEnd() {
        recyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    /** This method clears out the EditText field*/
    public void clearText() {
        EditText_taskMessage.getText().clear();
    }

    /** This method systematically closes the keyboard */
    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
