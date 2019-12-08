package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

public class selectLogin extends AppCompatActivity {

    ConstraintLayout studConLayout, facConLayout;
    ImageView colLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        studConLayout = (ConstraintLayout)findViewById(R.id.studConLayout);
        colLogo = (ImageView)findViewById(R.id.colLogo);
        facConLayout = (ConstraintLayout)findViewById(R.id.facConLayout);


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
}
