package com.apathyforge.hubbub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity
{

    protected DatabaseReference mUsersRef;
    private DatabaseReference mCurUser;
    private FirebaseUser user;
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        username.setText("Name: " + user.getDisplayName());
        email.setText("Email: " + user.getEmail());

        //set up database reference
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().
                getReference();
        mUsersRef = mRootRef.child("users");
        mCurUser = mUsersRef.child(user.getUid());
        getUserInfo();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    public void getUserInfo()
    {
        mCurUser.child("aboutMe").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(!(snapshot.getValue() == null))
                {
                    input.setText(snapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    public void updateUserInfo(View view)
    {
        mCurUser.child("email").setValue(user.getEmail());
        mCurUser.child("username").setValue(user.getDisplayName());
        mCurUser.child("aboutMe").setValue(input.getText().toString());
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
