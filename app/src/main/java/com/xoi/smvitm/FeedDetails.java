package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FeedDetails extends AppCompatActivity {
    ImageView feed_img;
    TextView feed_title,feed_description,photographer_name,blogger_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SMVITM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        feed_img=(ImageView)findViewById(R.id.imgview);
        feed_title=(TextView)findViewById(R.id.title);
        feed_description=(TextView)findViewById(R.id.description);
        photographer_name=(TextView)findViewById(R.id.phototgrapher_name);
        blogger_name=(TextView)findViewById(R.id.blogger_name);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String imgurl = getIntent().getStringExtra("imgurl");
        String photo_name = getIntent().getStringExtra("photographer_name");
        String blog_name = getIntent().getStringExtra("blogger_name");

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.college_logo);



        Glide.with(this).load(imgurl).apply(options).into(feed_img);

       feed_title.setText(title);
       feed_description.setText(description);
       photographer_name.setText("Photographer : "+photo_name);
       blogger_name.setText("Blogger : "+blog_name);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.download_menu_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_pdf:
                String pdfurl = getIntent().getStringExtra("pdfurl");
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfurl));
                request.setTitle("PDF");
                request.setDescription("Downloading");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Document.pdf");
                downloadmanager.enqueue(request);
                return true;

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
