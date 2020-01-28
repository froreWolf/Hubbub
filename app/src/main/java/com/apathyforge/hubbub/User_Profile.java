package com.apathyforge.hubbub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Profile extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        TextView username = findViewById(R.id.profileUserName);
        TextView email = findViewById(R.id.profileEmail);
        ImageView pfp = findViewById(R.id.profilePicture);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username.setText("Name: " + user.getDisplayName());
        email.setText("Email: " + user.getEmail());

        getUserInfo();

        //get rid of this later please!!!!!
        updateUserInfo();
    }



    public void getUserInfo()
    {

    }

    public void updateUserInfo()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello World.");
    }

}
