package com.xoi.smvitm.home.student;

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
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.xoi.smvitm.R;

public class PDFReader extends AppCompatActivity {
    WebView webView;
    LottieAnimationView loadAnim;
    TextView loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Circulars");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAnim = (LottieAnimationView)findViewById(R.id.loadanim);
        loadTxt = (TextView)findViewById(R.id.loadtxt);


        String pdfurl = getIntent().getStringExtra("circular_pdflink");

        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfurl;

        webView = (WebView) findViewById(R.id.webView);
        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    loadAnim.setVisibility(View.GONE);
                    loadTxt.setVisibility(View.GONE);
                }
            }
        });

        webView.loadUrl(url);

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
                String pdfurl = getIntent().getStringExtra("circular_pdflink");
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfurl));
                request.setTitle("PDF");
                request.setDescription("Downloading");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Document.pdf");
                downloadmanager.enqueue(request);
                return true;

            case R.id.share_pdf:
                String sharepdfurl = getIntent().getStringExtra("circular_pdflink");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share_body = sharepdfurl;
                intent.putExtra(Intent.EXTRA_SUBJECT, share_body);
                intent.putExtra(Intent.EXTRA_TEXT, share_body);
                startActivity(Intent.createChooser(intent, "Share Using"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
