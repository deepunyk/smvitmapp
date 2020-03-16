package com.xoi.smvitm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xoi.smvitm.home.student.feedFragmentRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class updatesActivity extends AppCompatActivity {
    LottieAnimationView loadAnim;
    TextView loadTxt;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    updatesRVAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Updates");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadAnim = (LottieAnimationView)findViewById(R.id.loadanim);
        loadTxt = (TextView)findViewById(R.id.loadtxt);
        getFeed();
    }
    private void getFeed() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://smvitmapp.xtoinfinity.tech/php/updates.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                    parseItems(response);

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadAnim.setVisibility(View.GONE);
                                loadTxt.setVisibility(View.GONE);
                                Toast.makeText(updatesActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                );
                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(updatesActivity.this);
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
                title.add(jo.optString("title"));
                description.add(jo.optString("description"));
            }
            loadAnim.setVisibility(View.GONE);
            loadTxt.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new updatesRVAdapter(title, description,this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
