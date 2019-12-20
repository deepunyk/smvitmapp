package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;

import java.util.HashMap;
import java.util.Map;

public class classroomFeedPostActivity extends AppCompatActivity {

    String posturl = "http://smvitmapp.xtoinfinity.tech/php/classroom/postclassroomfeed.php";
    String usn, ccode,post;
    EditText postTxt;
    ImageButton postBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_feed_post);

        usn = getIntent().getStringExtra("usn");
        ccode = getIntent().getStringExtra("ccode");

        postTxt = (EditText)findViewById(R.id.postTxt);
        postBut = (ImageButton)findViewById(R.id.postBut);

        postBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBut.animate().alpha(0).setDuration(100);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        postBut.animate().alpha(1).setDuration(100);
                    }
                }, 100);
                post = postTxt.getText().toString();
                if(post.equals("")){
                    Toast.makeText(classroomFeedPostActivity.this, "Please enter the description", Toast.LENGTH_SHORT).show();
                }
                else{
                    post();
                }
            }
        });
    }

    public void post(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, posturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(classroomFeedPostActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id",usn);
                params.put("description",post);
                params.put("classcode",ccode);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }






}
