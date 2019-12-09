package com.xoi.smvitm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class studMainPageAdapter extends FragmentPagerAdapter {

    public studMainPageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
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
                return new studclassFragment();
            case 3:
                return new studprofFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
