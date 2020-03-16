package com.xoi.smvitm.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.xoi.smvitm.R;
import com.xoi.smvitm.main.student.studMainPageAdapter;

public class timeMain extends AppCompatActivity {

    timeMainAdapter pageAdapter;
    ViewPager vp,hvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_main);

        vp = (ViewPager)findViewById(R.id.timeVp);

        pageAdapter = new timeMainAdapter(getSupportFragmentManager(),6);
        vp.setAdapter(pageAdapter);
    }
}
