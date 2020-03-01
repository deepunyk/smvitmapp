package com.xoi.smvitm.varnothsava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class vMainActivity extends AppCompatActivity {

    SharedPreferences sp;
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> startTime = new ArrayList<>();
    private ArrayList<String> venue = new ArrayList<>();
    private ArrayList<String> rules = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> coordinator = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();
    private ArrayList<String> ruleBook = new ArrayList<>();
    String url = "http://smvitmapp.xtoinfinity.tech/php/varnothsava/event.php";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_main);

        sp = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);
        getEvents();

    }

    private void getEvents() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("no")){
                                    Toast.makeText(vMainActivity.this, "No events found", Toast.LENGTH_SHORT).show();
                                }else{
                                    parseItems(response);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(vMainActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                );
                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(vMainActivity.this);
                queue.add(stringRequest);
            }
        });
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("events");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                date.add(jo.optString("date"));
                name.add(jo.optString("name"));
                startTime.add(jo.optString("startTime"));
                venue.add(jo.optString("venue"));
                rules.add(jo.optString("rules"));
                type.add(jo.optString("type"));
                coordinator.add(jo.optString("coodinator"));
                id.add(jo.optString("id"));
                photo.add(jo.optString("photo"));
                ruleBook.add(jo.optString("ruleBook"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vEventAdapter adapter = new vEventAdapter(date, name, startTime, venue, rules, type,coordinator, id, photo, ruleBook, this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        if(sp.contains("login")){
            finish();
        }else {
            Intent intent = new Intent(vMainActivity.this, vSelectActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
