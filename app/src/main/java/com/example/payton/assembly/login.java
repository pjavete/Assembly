package com.example.payton.assembly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {


    database peopleDB;
    EditText username, password;
    Button verifylogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        peopleDB = new database(this);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        checkLogin();
    }


    public void checkLogin(){
        verifylogin = (Button)findViewById(R.id.verifylogin);
        verifylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if text fields are empty the application crashes this doesnt fix it??
                if(username.getText()==null || password.getText()==null){
                    Toast.makeText(login.this, "Text fields are empty.", Toast.LENGTH_LONG).show();
                    checkLogin();
                }

                String user = username.getText().toString();
                String pass = password.getText().toString();


                boolean checkUsername = peopleDB.checkUsername(user);
                boolean checkPassword = peopleDB.checkPassword(user,pass);

                //unique username already exists then it returns false
                //does not check strings properly and crashes when you enter a string although the strings are saved in the db???
                //checking for integers works fine
                if(checkUsername==true) {
                    Toast.makeText(login.this, "Username doesn't exist.", Toast.LENGTH_LONG).show();
                    username.getText().clear();
                    password.getText().clear();
                    checkLogin();
                }else{
                    Toast.makeText(login.this, "Username does exist.", Toast.LENGTH_LONG).show();
                    if(checkPassword==false){
                        Toast.makeText(login.this, "Password incorrect.", Toast.LENGTH_LONG).show();
                        password.getText().clear();
                        checkLogin();
                    }else{
                        Toast.makeText(login.this, "You may pass.", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
