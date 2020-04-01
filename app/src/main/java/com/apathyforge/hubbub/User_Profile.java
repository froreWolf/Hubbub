package com.apathyforge.hubbub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class User_Profile extends AppCompatActivity
{

    protected DatabaseReference mUsersList;
    private FirebaseUser FBUser;
    private User user;
    private String key;
    //controls
    private EditText input;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        //link controls
        TextView username = findViewById(R.id.profileUserName);
        TextView email = findViewById(R.id.profileEmail);
        input = findViewById(R.id.userDescEntry);

        //get user information from account
        FBUser = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        username.setText("Name: " + FBUser.getDisplayName());
        email.setText("Email: " + FBUser.getEmail());

        //set up database reference
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().
                getReference();
        mUsersList = mRootRef.child("users");
        user = new User();
        getUserInfo();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void getUserInfo()
    {
        Query users = mUsersList.orderByChild("userID")
                .equalTo(FBUser.getUid());


        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
                                     @Nullable String s)
            {
                if(key != null)
                {
                    mUsersList.child(key).removeValue();
                    key = null;
                }

                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    input.setText(user.getUserIntro());
                    key = dataSnapshot.getKey();
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



    public void onClick(View view)
    {
        updateUserInfo();
    }

    public void updateUserInfo()
    {
        user.setUserName(FBUser.getDisplayName());
        user.setUserEmail(FBUser.getEmail());
        user.setUserID(FBUser.getUid());
        user.setUserIntro(input.getText().toString());

        mUsersList.push().setValue(user);
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
}
