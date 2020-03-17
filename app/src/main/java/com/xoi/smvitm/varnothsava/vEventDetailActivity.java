package com.xoi.smvitm.varnothsava;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

public class vEventDetailActivity extends AppCompatActivity {

    String date, name, startTime, venue, rules, type, coordinator, id, photo, ruleBook;
    TextView datetxt, timetxt, nametxt, venuetxt, rulestxt, coordinatortxt, dupDesc, dupCoor;
    ImageView photoimg;
    Button rulebookBut;
    ConstraintLayout coorLayout, descLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_event_detail);

        datetxt = (TextView)findViewById(R.id.date);
        timetxt = (TextView)findViewById(R.id.time);
        venuetxt = (TextView)findViewById(R.id.venue);
        rulestxt = (TextView)findViewById(R.id.description);
        nametxt = (TextView)findViewById(R.id.name);
        coordinatortxt = (TextView)findViewById(R.id.coordinator);
        photoimg = (ImageView) findViewById(R.id.img);
        rulebookBut = (Button) findViewById(R.id.rulebook);
        dupCoor = (TextView)findViewById(R.id.dupCoor);
        dupDesc = (TextView)findViewById(R.id.dupDesc);
        descLayout = (ConstraintLayout) findViewById(R.id.descLayout);
        coorLayout = (ConstraintLayout)findViewById(R.id.coorLayout);

        date = getIntent().getStringExtra("date");
        name = getIntent().getStringExtra("name");
        startTime = getIntent().getStringExtra("startTime");
        venue = getIntent().getStringExtra("venue");
        rules = getIntent().getStringExtra("rules");
        type = getIntent().getStringExtra("type");
        coordinator = getIntent().getStringExtra("coordinator");
        id = getIntent().getStringExtra("id");
        photo = getIntent().getStringExtra("photo");
        ruleBook = getIntent().getStringExtra("rulebook");

        datetxt.setText(date);
        timetxt.setText(startTime);
        venuetxt.setText(venue);
        nametxt.setText(name);
        rulestxt.setText(rules);
        coordinatortxt.setText(coordinator);
        Glide.with(this).load(photo).into(photoimg);

        if(rules.equals("")){
            rulestxt.setVisibility(View.GONE);
            dupDesc.setVisibility(View.GONE);
            descLayout.setVisibility(View.GONE);
        }
        if(rules.equals("")){
            coordinatortxt.setVisibility(View.GONE);
            dupCoor.setVisibility(View.GONE);
            coorLayout.setVisibility(View.GONE);
        }
        if(ruleBook.equals("")){
            rulebookBut.setVisibility(View.GONE);
        }

        rulebookBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(vEventDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(vEventDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(vEventDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

                else{
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(ruleBook);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setVisibleInDownloadsUi(true);        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
                    downloadManager.enqueue(request);
                }
            }
        });


    }
}
