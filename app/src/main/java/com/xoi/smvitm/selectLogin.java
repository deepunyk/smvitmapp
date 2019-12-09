package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class selectLogin extends AppCompatActivity {

    ConstraintLayout studConLayout, facConLayout;
    ImageView colLogo;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        studConLayout = (ConstraintLayout)findViewById(R.id.studConLayout);
        colLogo = (ImageView)findViewById(R.id.colLogo);
        facConLayout = (ConstraintLayout)findViewById(R.id.facConLayout);

        final Handler handler = new Handler();

        studConLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(colLogo,"colLogo");
                Intent intent = new Intent(selectLogin.this, loginActivity.class);
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(selectLogin.this,
                        pairs);
                startActivity(intent,option.toBundle());
                selectLogin.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        facConLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(colLogo,"colLogo");
                Intent intent = new Intent(selectLogin.this, facultyLogin.class);
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(selectLogin.this,
                        pairs);
                startActivity(intent,option.toBundle());
                selectLogin.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

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
