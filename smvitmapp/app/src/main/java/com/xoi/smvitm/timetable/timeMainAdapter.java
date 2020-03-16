package com.xoi.smvitm.timetable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xoi.smvitm.main.student.studaccFragment;
import com.xoi.smvitm.main.student.studclassFragment;
import com.xoi.smvitm.main.student.studhomeFragment;
import com.xoi.smvitm.main.student.studprofFragment;

public class timeMainAdapter extends FragmentPagerAdapter {

    public timeMainAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    private int numOfTabs;


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new timeFragment(1);
            case 1:
                return new timeFragment(2);
            case 2:
                return new timeFragment(3);
            case 3:
                return new timeFragment(4);
            case 4:
                return new timeFragment(5);
            case 5:
                return new timeFragment(6);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}