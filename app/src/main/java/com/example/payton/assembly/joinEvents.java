package com.example.payton.assembly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class joinEvents extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button joinButton;
    EditText codeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_events);
        mAuth = FirebaseAuth.getInstance();

        joinButton = findViewById(R.id.joinButton);
        codeText = findViewById(R.id.codeText);
    }


    public void joinEvent(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        /*
        if(userID == event.userID){
            Toast.makeText(this, "Error. Event owner cannot join own event.", Toast.LENGTH_LONG).show();
            codeText.getText().clear();
        }else {
            Toast.makeText(this, "Joined", Toast.LENGTH_LONG).show();
            finish();
            onBackPressed();
        }
        */
    }
}
