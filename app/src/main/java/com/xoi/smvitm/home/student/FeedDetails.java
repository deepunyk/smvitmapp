package com.xoi.smvitm.home.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoi.smvitm.R;

public class FeedDetails extends AppCompatActivity {
    ImageView feed_img;
    TextView feed_description, dupDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        feed_img=(ImageView)findViewById(R.id.imgview);
        feed_description=(TextView)findViewById(R.id.description);
        dupDesc=(TextView)findViewById(R.id.dupDesc);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String imgurl = getIntent().getStringExtra("imgurl");
        String photo_name = getIntent().getStringExtra("photographer_name");
        String blog_name = getIntent().getStringExtra("blogger_name");

        if(description.equals("")){
            dupDesc.setVisibility(View.GONE);
        }

        getSupportActionBar().setTitle(title);

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.college_logo);



        Glide.with(this).load(imgurl).apply(options).into(feed_img);

       feed_description.setText(description);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_pdf:
                String shareimgurl = getIntent().getStringExtra("imgurl");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share_body = shareimgurl;
                intent.putExtra(Intent.EXTRA_SUBJECT, share_body);
                intent.putExtra(Intent.EXTRA_TEXT, share_body);
                startActivity(Intent.createChooser(intent, "Share Using"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
