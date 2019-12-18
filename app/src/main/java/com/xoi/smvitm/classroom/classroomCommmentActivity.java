package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class classroomCommmentActivity extends AppCompatActivity {

    TextView nameTxt, dateTxt, descTxt;
    CircularImageView photoImg;
    SharedPreferences sp;
    String usn;
    String name, photo, desc,  datetime, cfid, ccode;
    String url = "http://smvitmapp.xtoinfinity.tech/php/getClassroomComments.php?code=";
    private ArrayList<String> descAr = new ArrayList<>();
    private ArrayList<String> profilepicAr = new ArrayList<>();
    private ArrayList<String> usnAr = new ArrayList<>();
    private ArrayList<String> nameAr = new ArrayList<>();
    private ArrayList<String> datetimeAr = new ArrayList<>();
    String fname, fphoto;
    classroomCommentAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_commment);

        sp = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        usn = sp.getString("usn","");
        ccode = getIntent().getStringExtra("ccode");
        name = getIntent().getStringExtra("name");
        photo = getIntent().getStringExtra("photo");
        desc = getIntent().getStringExtra("desc");
        datetime = getIntent().getStringExtra("datetime");
        cfid = getIntent().getStringExtra("cfid");
        fname = getIntent().getStringExtra("fname");
        fphoto = getIntent().getStringExtra("fphoto");


        nameTxt = (TextView)findViewById(R.id.nameTxt);
        dateTxt = (TextView)findViewById(R.id.dateTxt);
        descTxt = (TextView)findViewById(R.id.descTxt);

        nameTxt.setText(name);
        dateTxt.setText(datetime);
        descTxt.setText(desc);

        getClassroomFeed();
    }

    public void getClassroomFeed(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+cfid,
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
                descAr.add(jo.optString("description"));
                usnAr.add(jo.optString("usn"));
                nameAr.add(jo.optString("name"));
                profilepicAr.add(jo.optString("profilepic"));
                datetimeAr.add(jo.optString("datetime"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new classroomCommentAdapter(descAr,usnAr,nameAr,profilepicAr,datetimeAr,fname,fphoto,this);
        recyclerView.setAdapter(adapter);
    }
}
