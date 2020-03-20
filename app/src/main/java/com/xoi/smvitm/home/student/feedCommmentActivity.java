package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class    feedCommmentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> usn = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();
    feedCommentRvAdapter adapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_commment);

        sp = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);

        final String fid = getIntent().getStringExtra("fid");
        getFeed();
        ImageView submit = (ImageView) findViewById(R.id.submit);
        SharedPreferences prefs = feedCommmentActivity.this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        final String usn = prefs.getString("usn", "NULL");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment_text = (EditText) findViewById(R.id.comment_text);
                String comment = comment_text.getText().toString();
                closeKeyBoard();
                if(comment.equals("")){
                    Toast.makeText(feedCommmentActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(feedCommmentActivity.this, "Adding comment, please wait", Toast.LENGTH_SHORT).show();
                    comment_text.setText("");
                    String url = "http://smvitmapp.xtoinfinity.tech/php/home/add_feed_comment.php?fid=" + fid + "&usn=" + usn + "&comment=" + comment;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(feedCommmentActivity.this, "Comment added", Toast.LENGTH_SHORT).show();
                                    getFeed();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(feedCommmentActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            }
                    );
                    int socketTimeOut = 50000;
                    RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(retryPolicy);
                    RequestQueue queue = Volley.newRequestQueue(feedCommmentActivity.this);
                    queue.add(stringRequest);
                }
            }

        });
    }

    private void getFeed() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final String usnStr = sp.getString("usn", "NULL");
                final String fidStr = getIntent().getStringExtra("fid");
                String url = "http://smvitmapp.xtoinfinity.tech/php/home/feed_comment.php?fid=" + fidStr;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("no")){
                                    Toast.makeText(feedCommmentActivity.this, "No comments", Toast.LENGTH_SHORT).show();
                                }else{
                                    fid.clear();
                                    usn.clear();
                                    comment.clear();
                                    parseItems(response);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(feedCommmentActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                );
                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(feedCommmentActivity.this);
                queue.add(stringRequest);
            }
        });
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("result");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                fid.add(jo.optString("fid"));
                usn.add(jo.optString("usn"));
                comment.add(jo.optString("comment"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new feedCommentRvAdapter(fid,usn,comment,this);
        recyclerView.setAdapter(adapter);
    }
}
