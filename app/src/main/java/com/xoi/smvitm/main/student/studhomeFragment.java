package com.xoi.smvitm.main.student;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.home.student.circularsFragment;
import com.xoi.smvitm.home.student.eventsFragment;
import com.xoi.smvitm.home.student.feedFragment;
import com.xoi.smvitm.home.student.feedFragmentNew;

public class studhomeFragment extends Fragment {


    public studhomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studhome, container, false);
        BottomNavigationView bottomNav = (BottomNavigationView) view.findViewById(R.id.homeTN);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new feedFragmentNew()).commit();
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.feed:
                        selectedFragment = new feedFragmentNew();
                        break;
                    case R.id.events:
                        selectedFragment = new eventsFragment();
                        break;
                    case R.id.circulars:
                        selectedFragment = new circularsFragment();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });



        return view;
    }


}
