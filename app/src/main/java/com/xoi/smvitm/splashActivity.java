package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class splashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView splashLogo;
    LinearLayout descLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);
        splashLogo = (ImageView)findViewById(R.id.splashLogo);
        descLayout = (LinearLayout)findViewById(R.id.descLayout);

        descLayout.animate().translationYBy(-100).setDuration(1000);
        descLayout.animate().scaleYBy(0.2f).scaleXBy(0.2f).setDuration(1000);
        descLayout.animate().alpha(1).setDuration(300);
        splashLogo.animate().alpha(1).setDuration(500);

        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.contains("login")){
                    if(sharedPreferences.getString("login","").equals("1")) {
                        Intent intent = new Intent(splashActivity.this, studMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(splashActivity.this, facProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Intent intent = new Intent(splashActivity.this, selectLogin.class);
                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(splashActivity.this,
                            Pair.<View, String>create(splashLogo,"colLogo"));
                    startActivity(intent,option.toBundle());
                    splashActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }
        },2000);

    }
}
