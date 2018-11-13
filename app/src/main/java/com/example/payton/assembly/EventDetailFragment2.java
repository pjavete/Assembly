package com.example.payton.assembly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventDetailFragment2 extends Fragment {
    int position = 0;
    TextView tvTitle2;
    TextView tvDetails2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.activity_event_detail_fragment2, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        tvTitle2 = (TextView) view.findViewById(R.id.tvTitle2);
        tvDetails2 = (TextView) view.findViewById(R.id.tvDetails2);

        // update view
        tvTitle2.setText(Event.events2[position]);
        tvDetails2.setText(Event.eventDetails2[position]);
    }

    // Activity is calling this to update view on Fragment
    public void updateView(int position){
        tvTitle2.setText(Event.events2[position]);
        tvDetails2.setText(Event.eventDetails2[position]);
    }
}

