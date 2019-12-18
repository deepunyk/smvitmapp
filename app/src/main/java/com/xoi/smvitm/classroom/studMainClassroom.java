package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class studMainClassroom extends AppCompatActivity {

    SharedPreferences sp;
    String usn, ccode, tname, tphoto;
    String url = "http://smvitmapp.xtoinfinity.tech/php/getClassroomFeed.php?code=";
    String posturl = "http://smvitmapp.xtoinfinity.tech/php/postclassroomfeed.php";
    private ArrayList<String> arccode = new ArrayList<>();
    private ArrayList<String> cfid = new ArrayList<>();
    private ArrayList<String> link = new ArrayList<>();
    private ArrayList<String> arusn = new ArrayList<>();
    private ArrayList<String> profpic = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private ArrayList<String> datetime = new ArrayList<>();
    RecyclerView recyclerView;
    studMainClassAdapter adapter;
    TextView postTxt;
    Button postBut;
    String post;
    ConstraintLayout postLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_main_classroom);

        sp = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        postTxt = (TextView) findViewById(R.id.postTxt);
        postLayout = (ConstraintLayout) findViewById(R.id.postLayout);

        postTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studMainClassroom.this, classroomFeedPostActivity.class);
                intent.putExtra("usn", usn);
                intent.putExtra("ccode", ccode);
                startActivity(intent);
            }
        });

        usn = sp.getString("usn","");
        ccode = getIntent().getStringExtra("code");
        tname = getIntent().getStringExtra("tempname");
        tphoto = getIntent().getStringExtra("tempphoto");

        getClassroomChat();
    }

    public void getClassroomChat(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+ccode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("class");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                arccode.add(jo.optString("classcode"));
                cfid.add(jo.optString("cfId"));
                link.add(jo.optString("link"));
                desc.add(jo.optString("description"));
                arusn.add(jo.optString("usn"));
                profpic.add(jo.optString("profilepic"));
                name.add(jo.optString("name"));
                datetime.add(jo.optString("datetime"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new studMainClassAdapter(arccode,cfid,link,arusn,profpic,name,desc,datetime,tname, tphoto,this);
        recyclerView.setAdapter(adapter);
    }


}
