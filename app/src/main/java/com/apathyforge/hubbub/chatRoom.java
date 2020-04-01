package com.apathyforge.hubbub;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class chatRoom extends AppCompatActivity
{
    String locString;
    int locNum;
    FirebaseUser user;
    DatabaseReference mChatRef;
    ArrayList<ChatMessage> messageList;
    //controls
    EditText input;
    ListView messageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        input = findViewById(R.id.messageIn);
        messageBox = findViewById(R.id.messageBox);

        locString = getIntent().getStringExtra("location");
        assert locString != null;
        locNum = Integer.parseInt(locString);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mChatRef = FirebaseDatabase.getInstance().getReference();
        mChatRef = mChatRef.child("chatRooms");

        //array adapter here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        messageList = new ArrayList<>();

        MessageListAdapter adapter = new MessageListAdapter(
                this, R.layout.chat_list_adapter, messageList);
        messageBox.setAdapter(adapter);

        getChatData();
        getMessages();
    }


    private void getChatData()
    {
        switch(locNum)
        {
            case 1:
                mChatRef = mChatRef.child("chatRoom1");
                break;
            case 2:
                mChatRef = mChatRef.child("chatRoom2");
                break;
            case 3:
                mChatRef = mChatRef.child("chatRoom3");
                break;
            case 4:
                mChatRef = mChatRef.child("chatRoom4");
                break;
            case 5:
                mChatRef = mChatRef.child("chatRoom5");
                break;
            case 6:
                mChatRef = mChatRef.child("chatRoom6");
                break;
        }
    }

    public void sendMessage(View view)
    {
        if(!input.getText().toString().equals(""))
        {
            ChatMessage newMessage = new ChatMessage(user.getDisplayName()
                    , input.getText().toString());

            mChatRef.push().setValue(newMessage);

            input.setText("");
        }
    }

    public void getMessages()
    {
        Query messages = mChatRef.orderByValue();
        messages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
                                     @Nullable String s)
            {
                messageList.add(dataSnapshot.getValue(ChatMessage.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot,
                                       @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot,
                                     @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
