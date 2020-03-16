package com.xoi.smvitm.academics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.xoi.smvitm.R;

public class study_meterialsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    TabItem tabitem1,tabitem2;
    ViewPager viewPager;
    public String sname,scode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_meterials);
     sname =getIntent().getExtras().getString("subname");
         scode =getIntent().getExtras().getString("subcode");

        toolbar = findViewById(R.id.toolbarnotes);
        toolbar.setTitle(sname);
        tabLayout = findViewById(R.id.tablayout1);
        tabitem1 = findViewById(R.id.ti1);
        tabitem2 = findViewById(R.id.ti2);
        viewPager = findViewById(R.id.viewPager1);
        notesAdapter pageAdapter = new notesAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setSupportActionBar(toolbar);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }


}
