package com.xoi.smvitm.auth;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;
import com.xoi.smvitm.main.faculty.facMainActivity;
import com.xoi.smvitm.main.student.studMainActivity;
import com.xoi.smvitm.varnothsava.vMainActivity;
import com.xoi.smvitm.varnothsava.vSelectActivity;

public class splashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView splashLogo;
    LinearLayout descLayout;
    String version;

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
        if(sharedPreferences.contains("secTime")){
            checkConnection();
        }
        else{
            sharedPreferences.edit().clear().apply();
            sharedPreferences.edit().putString("secTime","1").apply();
            checkConnection();
        }

    }

    private void checkLogin(){
        if(sharedPreferences.contains("login")){
            if(sharedPreferences.getString("login","").equals("1")) {
                Intent intent = new Intent(splashActivity.this, studMainActivity.class);
                startActivity(intent);
                finish();
            }
            else if(sharedPreferences.getString("login","").equals("3")) {
                Intent intent = new Intent(splashActivity.this, vMainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(splashActivity.this, facMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            Intent intent = new Intent(splashActivity.this, vSelectActivity.class);
            ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(splashActivity.this,
                    Pair.<View, String>create(splashLogo, "colLogo"));
            startActivity(intent, option.toBundle());
            splashActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
    public void checkConnection(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
                checkUpdate();
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();

            }
        }
        else
            Toast.makeText(this, "Please switch on your internet and restart the app", Toast.LENGTH_LONG).show();
    }
    private void checkUpdate(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://smvitmapp.xtoinfinity.tech/php/checkUpdate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(version.equals(response) || response.equals("ok") || response.equals("main")){
                            checkLogin();
                        }
                        else if(response.equals("main")){
                            Toast.makeText(splashActivity.this, "Server under maintainance. Please try again after sometime", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(splashActivity.this, "New update found. Go to playstore and update the app.", Toast.LENGTH_LONG).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(splashActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(splashActivity.this);
        queue.add(stringRequest);
    }
}
