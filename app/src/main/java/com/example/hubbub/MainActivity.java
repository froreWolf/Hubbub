package com.example.hubbub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickMeButton = findViewById(R.id.clickMeButton);
        final TextView resultsText = findViewById(R.id.clickMeResults);
        clickMeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                resultsText.setText("Hello There!");
            }
        });


    }
}
