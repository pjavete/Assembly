package com.example.payton.assembly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
                    case R.id.myevents:
                        Intent myevents_redirect = new Intent(MainPage.this, MyEvents.class);
                        startActivity(myevents_redirect);
                        return true;
                    case R.id.createEvent:
                        Intent createEvent_redirect = new Intent(MainPage.this, createEvents.class);
                        startActivity(createEvent_redirect);
                        return true;
                    case R.id.joinEvent:
                        Intent joinEvent_redirect = new Intent(MainPage.this, joinEvents.class);
                        startActivity(joinEvent_redirect);
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
}
