package com.apathyforge.hubbub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SignInButton signInButton;
    Button signOutButton;
    TextView statusTextView;
    GoogleApiClient mGoogleApiClient;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* Fragment Activity */, this /* Fragment Activity */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        statusTextView = (TextView) findViewById(R.id.status_textView);
        signInButton = (SignInButton) findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(this);

        signOutButton = (Button) findViewById(R.id.SignOutButton);
        signOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.SignInButton:
                signIn();
                break;
            case R.id.SignOutButton:
                signOut();
                break;
        }
    }

    private void signIn(){

    }

    private void signOut(){

    }
}
