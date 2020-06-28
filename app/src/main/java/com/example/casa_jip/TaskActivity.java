package com.example.casa_jip;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

    // datepicker
    private DatePickerDialog datePicker;
    private EditText textDate;

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
                if (taskMessage != null) {

                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);


                    TaskData task = new TaskData();
                    task.setTaskMessage(taskMessage);
                    task.setTaskBoolean(false);
                    task.setDueDate(cldr);
                    myRef.push().setValue(task);
                    EditText_taskMessage.setText("");
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
                Log.d("TASKTASK", dataSnapshot.getValue().toString());
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
    }
}

