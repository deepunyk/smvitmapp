package com.xoi.smvitm.main.student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.home.student.circularsFragmentNew;
import com.xoi.smvitm.home.student.eventsFragmentNew;
import com.xoi.smvitm.home.student.feedFragmentNew;

public class studhomeFragment extends Fragment {


    public studhomeFragment() {
        // Required empty public constructor
    }
    BottomNavigationView bottomNav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studhome, container, false);
         bottomNav = (BottomNavigationView) view.findViewById(R.id.homeTN);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new feedFragmentNew()).commit();
        }
        bottomNav.setSelectedItemId(R.id.feed);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.feed:
                        selectedFragment = new feedFragmentNew();
                        break;
                    case R.id.events:
                        selectedFragment = new eventsFragmentNew();
                        break;
                    case R.id.circulars:
                        selectedFragment = new circularsFragmentNew();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        bottomNav.setSelectedItemId(R.id.feed);
        super.onResume();
    }
}
