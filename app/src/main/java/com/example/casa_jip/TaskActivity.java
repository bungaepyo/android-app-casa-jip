package com.example.casa_jip;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TaskData> taskList;
    private EditText EditText_taskMessage;
    private Button btn_add;
    private DatabaseReference myRef;

    private CheckBox CheckBox_taskChecked;
    private boolean taskBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // add more tasks
        EditText_taskMessage = findViewById(R.id.EditText_taskMessage);
        btn_add = findViewById(R.id.btn_add);

        // is the task completed
        CheckBox_taskChecked = findViewById(R.id.CheckBox_taskChecked);

        // when btn_add is clicked with text filled, it pushes the taskMessage
        btn_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String taskMessage = EditText_taskMessage.getText().toString();
                String strDue = LocalDate.now().toString();

                if (taskMessage != null) {
                    TaskData task = new TaskData();
                    task.setTaskMessage(taskMessage);
                    task.setTaskBoolean(false);
                    task.setDue(strDue);
                    myRef.push().setValue(task);
                    EditText_taskMessage.getText().clear();
                    updateToEnd();
                    closeKeyboard();
                }

            }

        });

        recyclerView = findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        taskList = new ArrayList<>();
        mAdapter = new TaskAdapter(taskList, TaskActivity.this);
        recyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaskData task = dataSnapshot.getValue(TaskData.class);
                ((TaskAdapter) mAdapter).addTask(task);
            }

            @Override
            public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("taskchecked", dataSnapshot.getValue().toString());

                CheckBox_taskChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        taskBool = CheckBox_taskChecked.isChecked();
                        if (taskBool) {
                            TaskData updatedTask = dataSnapshot.getValue(TaskData.class);
                            updatedTask.setTaskBoolean(true);
                            myRef.push().setValue(updatedTask);
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

        //Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Chat Selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        //ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_task:
                        startActivity(new Intent(getApplicationContext(),TaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_gallery:
                        startActivity(new Intent(getApplicationContext(),TestActivity_B.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


        /** This method keeps focus on the last item of the recyclerView */
        public void updateToEnd() {
            recyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        };

        /** This method clears out the EditText field*/
        public void clearText() {
            EditText_taskMessage.getText().clear();
            }

        /** This method systematically closes the keyboard */
        private void closeKeyboard() {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        };


    }


