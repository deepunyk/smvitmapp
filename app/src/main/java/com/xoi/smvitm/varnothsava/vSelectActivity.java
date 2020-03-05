package com.xoi.smvitm.varnothsava;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.xoi.smvitm.R;
import com.xoi.smvitm.auth.selectLogin;

public class vSelectActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    ConstraintLayout colLayout, varLayout;
    ImageView colLogo, varImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_select);

        colLayout = (ConstraintLayout)findViewById(R.id.colLayout);
        varLayout = (ConstraintLayout)findViewById(R.id.varLayout);
        colLogo = (ImageView)findViewById(R.id.colLogo);
        varImg = (ImageView)findViewById(R.id.varImg);

        colLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(colLogo,"colLogo");
                Intent intent = new Intent(vSelectActivity.this, selectLogin.class);
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(vSelectActivity.this,
                        pairs);
                startActivity(intent,option.toBundle());
                vSelectActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

        varLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(varImg,"varImg");
                Intent intent = new Intent(vSelectActivity.this, vLoginActivity.class);
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(vSelectActivity.this,
                        pairs);
                startActivity(intent,option.toBundle());
                vSelectActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });

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
