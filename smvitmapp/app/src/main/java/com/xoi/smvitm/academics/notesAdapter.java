package com.xoi.smvitm.academics;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class notesAdapter extends FragmentPagerAdapter {

    int numOfTabs=2;
    notesAdapter(FragmentManager fm,int numOfTabs)
    {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new notesFragment();
            case 1:
                return new qpaperFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
