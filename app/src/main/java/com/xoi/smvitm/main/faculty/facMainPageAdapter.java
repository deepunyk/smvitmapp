package com.xoi.smvitm.main.faculty;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xoi.smvitm.main.student.studaccFragment;
import com.xoi.smvitm.main.student.studhomeFragment;

public class facMainPageAdapter extends FragmentPagerAdapter {

    public facMainPageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    private int numOfTabs;


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new studhomeFragment();
            case 1:
                return new studaccFragment();
            case 2:
                return new facClassFragment();
            case 3:
                return new facProfFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
