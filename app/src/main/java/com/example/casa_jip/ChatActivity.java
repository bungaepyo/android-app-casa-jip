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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String nickname = "nickname_2";
    private EditText EditText_chat;
    private Button Button_send;
    //private DatabaseReference taskRef;
    private DatabaseReference chatRef;
    private String sendTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EditText_chat = findViewById(R.id.EditText_chat);
        Button_send = findViewById(R.id.Button_send);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String message = EditText_chat.getText().toString();
                String sendHour = LocalTime.now().toString().substring(0,2);
                String sendMinute = LocalTime.now().toString().substring(2,5);
                int sendHr = Integer.valueOf(sendHour);

                if(sendHr > 12){
                    sendTime = (sendHr - 12) + sendMinute + " PM";
                } else {
                    sendTime = sendHour + sendMinute + " AM";
                }

                if(message.length() > 0) {
                    ChatData chat = new ChatData();
                    chat.setNickname(nickname);
                    chat.setMessage(message);
                    chat.setSendTime(sendTime);
                    chatRef.push().setValue(chat);
                    clearText();
                    updateToEnd();
                }
            }
        });

        mRecyclerView = findViewById(R.id.chat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nickname);
        mRecyclerView.setAdapter(mAdapter);


        //FirebaseDatabase taskDb = FirebaseDatabase.getInstance("https://casajip-c4cc9.firebaseio.com/");
        //taskRef = taskDb.getReference();

        FirebaseDatabase chatDb = FirebaseDatabase.getInstance("https://casajip-c4cc9-50ef0.firebaseio.com/");
        chatRef = chatDb.getReference();

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                ChatData chat = dataSnapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
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
                        startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    /** This method keeps focus on the last item of the recyclerView */
    public void updateToEnd(){
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    /** This method clears out the EditText field*/
    public void clearText() {
        EditText_chat.getText().clear();
    }

    /** This method systematically closes the keyboard */
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}