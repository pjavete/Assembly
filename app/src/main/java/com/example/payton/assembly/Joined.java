package com.example.payton.assembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class Joined extends AppCompatActivity  implements JoinedFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined);



        if (savedInstanceState == null) {
            // Instance of first fragment
            JoinedFragment firstFragment = new JoinedFragment();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer2, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
            // commit FragmentTransaction
        }
    }

    @Override
    public void onEventItemSelected2(int position) {

        // Load Pizza Detail Fragment
        EventDetailFragment2 secondFragment = new EventDetailFragment2();

        Bundle args = new Bundle();
        args.putInt("position", position);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer2, secondFragment) // replace flContainer
                .addToBackStack(null)
                .commit();

    }
}