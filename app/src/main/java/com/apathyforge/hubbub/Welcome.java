package com.apathyforge.hubbub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Random;

public class Welcome extends AppCompatActivity {

    Button rideShareButton, navigationButton, friendsButton,
            profileButton, settingsButton,signOutButton;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        //bind buttons to button variables
        rideShareButton = findViewById(R.id.rideShare_btn);
        navigationButton = findViewById(R.id.navigation_btn);
        friendsButton = findViewById(R.id.friends_btn);
        profileButton = findViewById(R.id.profile_btn);
        settingsButton = findViewById(R.id.settings_btn);
        signOutButton = findViewById(R.id.logout_btn);
        //create onclick listeners for buttons
        rideShareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Welcome.this, RideShare.class));
            }
        });
        navigationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Welcome.this, Navigation.class));
            }
        });
        friendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Welcome.this, Friends.class));
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Welcome.this, User_Profile.class));
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Welcome.this, SettingsActivity.class));
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });



        getUserDetails();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signOut(){
        // Firebase sign out
        mAuth.signOut();

        // Google sign
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Welcome.this, MainActivity.class));
                    }
                });
    }

    public void signOutRevokeAccess(){ //not quite working yet
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Welcome.this, MainActivity.class));
                    }
                });
    }

    public void getUserDetails(){
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        FirebaseUser user = mAuth.getCurrentUser();
        welcomeMessage.setText(getWelcomeMessage() + "\n" + user.getDisplayName() + "!");
        //log the user out and revoke access for their account if the user
        //logs in with an email address that is not a .edu address
/*      String userEmail = user.getEmail();
        if(!userEmail.contains(".edu")){
            signOutRevokeAccess();
        }
*/  }



    public String getWelcomeMessage(){
        Random rand = new Random();
        switch (rand.nextInt(11)){
            case 1:
                return "Welcome";
            case 2:
                return "Howdy";
            case 3:
                return "Salud";
            case 4:
                return "Hello";
            case 5:
                return "Greetings";
            case 6:
                return "Hola";
            case 7:
                return "Aloha";
            case 8:
                return "Screeeee";
            case 9:
                return "Oh?";
            case 10:
                return "Welcome Back";
        }

        return "Welcome";
    }

}
