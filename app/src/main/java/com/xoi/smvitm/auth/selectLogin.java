package com.xoi.smvitm.auth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.xoi.smvitm.R;
import com.xoi.smvitm.varnothsava.vSelectActivity;

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
                finish();

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
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(selectLogin.this, vSelectActivity.class);
        startActivity(intent);
        finish();
    }
}
