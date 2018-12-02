package com.example.payton.assembly;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class Created extends AppCompatActivity  implements CreatedFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created);



        if (savedInstanceState == null) {
            // Instance of first fragment
            CreatedFragment firstFragment = new CreatedFragment();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
            // commit FragmentTransaction
        }
    }

    @Override
    public void onEventItemSelected(int position) {

        // Load Pizza Detail Fragment
        CreatedDetailFragment secondFragment = new CreatedDetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, secondFragment) // replace flContainer
                .addToBackStack(null)
                .commit();

    }
}

