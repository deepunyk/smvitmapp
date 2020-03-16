package com.xoi.smvitm.home.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoi.smvitm.R;

public class EventDetails extends AppCompatActivity {
    TextView event_description,event_organizers,event_date;
    ImageView event_img;
    Button view_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        event_description=(TextView)findViewById(R.id.event_description);
        event_organizers=(TextView)findViewById(R.id.event_organizers);
        event_date=(TextView)findViewById(R.id.event_date);

        event_img=(ImageView)findViewById(R.id.imgview);

        view_button=(Button)findViewById(R.id.view_button);

        String e_title = getIntent().getStringExtra("event_title");
        String e_description = getIntent().getStringExtra("event_description");
        String e_imgurl = getIntent().getStringExtra("event_imgurl");
        String e_organizers = getIntent().getStringExtra("event_organizers");
        String e_date = getIntent().getStringExtra("event_date");
        final String e_pdflink = getIntent().getStringExtra("event_pdflink");

        getSupportActionBar().setTitle(e_title);

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.college_logo);



        Glide.with(this).load(e_imgurl).apply(options).into(event_img);

        event_description.setText(e_description);
        event_organizers.setText("Event Organizers : "+e_organizers);
        event_date.setText("Event Date : "+e_date);

        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sharepdfurl = getIntent().getStringExtra("event_imgurl");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share_body = sharepdfurl;
                intent.putExtra(Intent.EXTRA_SUBJECT, share_body);
                intent.putExtra(Intent.EXTRA_TEXT, share_body);
                startActivity(Intent.createChooser(intent, "Share Using"));

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
