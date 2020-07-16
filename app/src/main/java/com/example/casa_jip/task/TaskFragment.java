package com.example.casa_jip.task;

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


import com.example.casa_jip.R;

public class TaskFragment extends Fragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
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
                String taskMessage = EditText_taskMessage.getText().toString();
                String strDue = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    strDue = LocalDate.now().toString();
                }

                if (taskMessage.length() > 0) {
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

        recyclerView = getView().findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        taskList = new ArrayList<>();
        mAdapter = new TaskAdapter(taskList, getActivity());
        recyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://casajip-ddbc9.firebaseio.com/");
        myRef = database.getReference();

        myRef.addChildEventListener(new ChildEventListener() {


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