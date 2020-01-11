package com.xoi.smvitm.academics;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new janFragment();
            case 1:
                return new febFragment();
            case 2:
                return new marFragment();
            case 3:
                return new aprFragment();
            case 4:
                return new mayFragment();
            case 5:
                return new junFragment();
            case 6:
                return new julFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}