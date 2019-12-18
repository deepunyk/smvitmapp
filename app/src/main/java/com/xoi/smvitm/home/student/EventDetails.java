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
import com.xoi.smvitm.PDFReader;
import com.xoi.smvitm.R;

public class EventDetails extends AppCompatActivity {
    TextView event_title,event_description,event_organizers,event_date;
    ImageView event_img;
    Button view_button,register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SMVITM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        event_title=(TextView)findViewById(R.id.event_title);
        event_description=(TextView)findViewById(R.id.event_description);
        event_organizers=(TextView)findViewById(R.id.event_organizers);
        event_date=(TextView)findViewById(R.id.event_date);

        event_img=(ImageView)findViewById(R.id.imgview);

        view_button=(Button)findViewById(R.id.view_button);
        register_button=(Button)findViewById(R.id.register_button);

        String e_title = getIntent().getStringExtra("event_title");
        String e_description = getIntent().getStringExtra("event_description");
        String e_imgurl = getIntent().getStringExtra("event_imgurl");
        String e_organizers = getIntent().getStringExtra("event_organizers");
        String e_date = getIntent().getStringExtra("event_date");
        final String e_pdflink = getIntent().getStringExtra("event_pdflink");

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.college_logo);



        Glide.with(this).load(e_imgurl).apply(options).into(event_img);

        event_title.setText(e_title);
        event_description.setText(e_description);
        event_organizers.setText("Event Organizers : "+e_organizers);
        event_date.setText("Event Date : "+e_date);

        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(EventDetails.this, PDFReader.class);
                    intent.putExtra("pdfurl", e_pdflink);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    EventDetails.this.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(EventDetails.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventDetails.this, "Register button clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
