package com.xoi.smvitm.varnothsava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

public class vEventDetailActivity extends AppCompatActivity {

    String date, name, startTime, venue, rules, type, coordinator, id, photo, ruleBook;
    TextView datetxt, timetxt, nametxt, venuetxt, rulestxt, coordinatortxt;
    ImageView photoimg;
    Button rulebookBut;

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

        date = getIntent().getStringExtra("date");
        name = getIntent().getStringExtra("name");
        startTime = getIntent().getStringExtra("startTime");
        venue = getIntent().getStringExtra("venue");
        rules = getIntent().getStringExtra("rules");
        type = getIntent().getStringExtra("type");
        coordinator = getIntent().getStringExtra("coordinator");
        id = getIntent().getStringExtra("id");
        photo = getIntent().getStringExtra("photo");
        ruleBook = getIntent().getStringExtra("ruleBook");

        datetxt.setText(date);
        timetxt.setText(startTime);
        venuetxt.setText(venue);
        nametxt.setText(name);
        rulestxt.setText(rules);
        coordinatortxt.setText(coordinator);
        Glide.with(this).load(photo).into(photoimg);


    }
}
