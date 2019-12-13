package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class storeActivity extends AppCompatActivity {
    storeAdapter pageAdapter;

    BottomNavigationViewEx studBN;
    ViewPager vp;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        studBN = (BottomNavigationViewEx)findViewById(R.id.studBN);
        vp = (ViewPager)findViewById(R.id.studVP);

        pageAdapter = new storeAdapter(getSupportFragmentManager(),studBN.getItemCount());
        vp.setAdapter(pageAdapter);

        studBN.setupWithViewPager(vp);

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
