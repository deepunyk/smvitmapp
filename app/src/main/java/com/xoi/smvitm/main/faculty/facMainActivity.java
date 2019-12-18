package com.xoi.smvitm.main.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.xoi.smvitm.R;

public class facMainActivity extends AppCompatActivity {

    facMainPageAdapter pageAdapter;
    BottomNavigationViewEx facBN;
    ViewPager vp;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_main);

        facBN = (BottomNavigationViewEx)findViewById(R.id.facBN);
        vp = (ViewPager)findViewById(R.id.studVP);

        pageAdapter = new facMainPageAdapter(getSupportFragmentManager(),facBN.getItemCount());
        vp.setAdapter(pageAdapter);

        facBN.setupWithViewPager(vp);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
