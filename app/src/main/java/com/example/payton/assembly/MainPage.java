package com.example.payton.assembly;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        dl = (DrawerLayout)findViewById(R.id.activity_main_page);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open,R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.gohome:
                        finish();
                        Intent homepage_redirect = new Intent(MainPage.this, MainPage.class);
                        startActivity(homepage_redirect);
                        return true;
                    case R.id.events:
                        Intent myevents_redirect = new Intent(MainPage.this, Events.class);
                        startActivity(myevents_redirect);
                        return true;
                    case R.id.schedule:
                        Intent myschedule_redirect = new Intent(MainPage.this, mySchedule.class);
                        startActivity(myschedule_redirect);
                        return true;
                    case R.id.account:
                        Intent myaccount_redirect = new Intent(MainPage.this, account.class);
                        startActivity(myaccount_redirect);
                        return true;
                    case R.id.logout:
                        MyCustomAlertDialog();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void MyCustomAlertDialog(){
        final Dialog myDialog = new Dialog(MainPage.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.setTitle("My Custom Dialog");
        Button hello = (Button)myDialog.findViewById(R.id.hello);
        Button close = (Button)myDialog.findViewById(R.id.close);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        hello.setTypeface(typeface);
        close.setTypeface(typeface);
        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                Intent signout_redirect = new Intent(MainPage.this, opening.class);
                startActivity(signout_redirect);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });

        myDialog.show();
    }
}
