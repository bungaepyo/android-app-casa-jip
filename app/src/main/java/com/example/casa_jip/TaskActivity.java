package com.example.casa_jip;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TaskData> taskList;
    private String username = "username_1";
    private EditText EditText_taskMessage;
    private Button btn_add;
    private DatabaseReference myRef;
    private CheckBox taskChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

//        taskChecked = findViewById(R.id.CheckBox_taskChecked);
//
//        taskChecked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                taskChecked.setChecked(true);
//            }
//        });


        // add more tasks
        EditText_taskMessage = findViewById(R.id.EditText_taskMessage);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskMessage = EditText_taskMessage.getText().toString();

                if(taskMessage != null){
                    TaskData task = new TaskData();
                    task.setUsername(username);
                    task.setTaskMessage(taskMessage);
                    task.setTaskChecked(false);
                    myRef.push().setValue(task);
                }
            }
        });




        recyclerView = findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        taskList = new ArrayList<>();
        mAdapter = new TaskAdapter(taskList, TaskActivity.this, username);
        recyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TASKTASK", dataSnapshot.getValue().toString());
                TaskData task = dataSnapshot.getValue(TaskData.class);
                ((TaskAdapter) mAdapter).addTask(task);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
}