package com.example.casa_jip.chat;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casa_jip.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String nickname = "nickname_2";
    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference chatRef;
    private String sendTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        EditText_chat = getView().findViewById(R.id.EditText_chat);
        Button_send = getView().findViewById(R.id.Button_send);

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

        mRecyclerView = getView().findViewById(R.id.chat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, getActivity(), nickname);
        mRecyclerView.setAdapter(mAdapter);


        FirebaseDatabase chatDb = FirebaseDatabase.getInstance("https://casajip-ddbc9-efda1.firebaseio.com/");
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

    }

    /** This method keeps focus on the last item of the recyclerView */
    public void updateToEnd(){
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    /** This method clears out the EditText field*/
    public void clearText() {
        EditText_chat.getText().clear();
    }

}