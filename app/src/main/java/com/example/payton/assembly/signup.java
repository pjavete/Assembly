package com.example.payton.assembly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    database peopleDB;
    EditText username2, password2;
    Button verifylogin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        peopleDB = new database(this);
        username2 = (EditText)findViewById(R.id.username2);
        password2 = (EditText)findViewById(R.id.password2);
        AddData();
    }

    public void AddData(){
        verifylogin2 = (Button)findViewById(R.id.verifylogin2);
        verifylogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username2.getText().toString();
                String password = password2.getText().toString();

                boolean check = peopleDB.addData(username,password);
                //unique username already exists then it returns false
                if(check==false) {
                    Toast.makeText(signup.this, "Username already exists. Please pick another one.", Toast.LENGTH_LONG).show();
                    username2.getText().clear();
                    password2.getText().clear();
                    AddData();
                }else{
                    finish();
                    Intent signup = new Intent(signup.this, login.class);
                    startActivity(signup);
                }
            }
        });
    }


}
