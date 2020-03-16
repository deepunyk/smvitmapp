package com.xoi.smvitm.store;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class storeAdapter extends FragmentPagerAdapter {

    public storeAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    private int numOfTabs;


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new storeexploreFragment();
            case 1:
                return new storechatFragment();
            case 2:
                return new storesellFragment();
            case 3:
                return new storeadsFragment();
            case 4:
                return new storetranFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
