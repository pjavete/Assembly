package com.example.payton.assembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class opening extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        login_redirect();
        signup_redirect();
    }

    private Button login;
    public void login_redirect (){
        login = (Button)findViewById(R.id.login);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        login.setTypeface(typeface);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_redirect = new Intent(opening.this, login.class);
                startActivity(login_redirect);
            }
        });
    }

    private Button signup;
    public void signup_redirect (){
        signup = (Button)findViewById(R.id.signup);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        signup.setTypeface(typeface);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_redirect = new Intent(opening.this, signup.class);
                startActivity(signup_redirect);
            }
        });
    }
}
