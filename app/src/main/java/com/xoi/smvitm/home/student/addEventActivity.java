package com.xoi.smvitm.home.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xoi.smvitm.R;


public class addEventActivity extends AppCompatActivity {

    Button selectBut, uploadBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        selectBut = (Button) findViewById(R.id.selectBut);
        uploadBut = (Button) findViewById(R.id.uploadBut);

        selectBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        uploadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                }catch (Exception e){
                    Toast.makeText(addEventActivity.this, ""+e, Toast.LENGTH_LONG).show();
                }
            }});
    }
}

