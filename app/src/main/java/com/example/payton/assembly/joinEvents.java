package com.example.payton.assembly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class joinEvents extends AppCompatActivity{
    Button joinButton;
    TextView codeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_events);
        joinButton = findViewById(R.id.joinButton);
        codeText = findViewById(R.id.codeText);
    }
}
