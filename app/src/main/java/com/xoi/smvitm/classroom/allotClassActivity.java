package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xoi.smvitm.R;
import com.xoi.smvitm.main.student.studMainPageAdapter;

public class allotClassActivity extends AppCompatActivity{

    public String selectSem, selectSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_class);

        loadFragment(new allotSelectSemFragment());

    }

    public boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.allotFrame,fragment)
                    .commit();
            return true;
        }
        else
            return false;
    }

}
