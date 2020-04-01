package com.apathyforge.hubbub;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Objects;

public class Friends extends AppCompatActivity
{
    DatabaseReference mUserDB;
    ArrayList<User> userList;
    //controls
    ListView searchResults;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //attach controls to objects
        searchResults = findViewById(R.id.friendSearchView);
        input = findViewById(R.id.friendSearchBar);


        userList= new ArrayList<>();

        FriendSearchAdapter adapter = new FriendSearchAdapter(
                this, R.layout.friends_search_adapter, userList);
        searchResults.setAdapter(adapter);

        mUserDB = FirebaseDatabase.getInstance().getReference();
        mUserDB = mUserDB.child("users");

        getUserList("");
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    public void search(View view)
    {
        userList.clear();
        getUserList(input.getText().toString());
    }

    public void getUserList(final String input)
    {
        Query users = mUserDB.orderByValue();
        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
                                     @Nullable String s)
            {
                if(input.equals("") ||
                    Objects.requireNonNull(dataSnapshot.getValue(User.class))
                            .getUserName()
                    .contains(input) ||
                    Objects.requireNonNull(dataSnapshot.getValue(User.class))
                            .getUserName()
                    .contains(input))
                {
                    userList.add(dataSnapshot.getValue(User.class));
                }

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
