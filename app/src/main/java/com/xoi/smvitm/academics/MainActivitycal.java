package com.xoi.smvitm.academics;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.xoi.smvitm.R;

public class MainActivitycal extends AppCompatActivity {
     TabLayout tabLayout;
     TabItem tabitem2,tabitem3,tabitem4,tabitem5,tabitem6,tabitem7;
     ViewPager viewPager;
     public int loadbit=0;
     SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincal);
        sp = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);
        tabLayout = findViewById(R.id.tablayout);
        tabitem2 = findViewById(R.id.tabitem2);
        tabitem3 = findViewById(R.id.tabitem3);
        tabitem4 = findViewById(R.id.tabitem4);
        tabitem5 = findViewById(R.id.tabitem5);
        tabitem6 = findViewById(R.id.tabitem6);
        tabitem7 = findViewById(R.id.tabitem7);
        viewPager = findViewById(R.id.viewPager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivitycal.this,
//                                R.color.colorAccent));
//                    }
//                } else if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivitycal.this,
//                                R.color.colorAccent));
//                    }
//                } else {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivitycal.this,
//                            R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivitycal.this,
//                                R.color.colorAccent));
//                    }
//                }
            }




            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

}
