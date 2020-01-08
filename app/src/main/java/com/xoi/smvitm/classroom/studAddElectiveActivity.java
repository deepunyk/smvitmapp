package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class studAddElectiveActivity extends AppCompatActivity {

    String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/showElective.php?usn=";
    SharedPreferences sp;
    String usn;
    RecyclerView recyclerView;
    Toolbar toolbar;
    private ArrayList<String> code = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> ccode = new ArrayList<>();
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fphoto = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    addElectiveAdapter adapter;
    Button addBut;
    TextView addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_add_elective);
        sp = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addBut = (Button)findViewById(R.id.addBut);
        addText= (TextView) findViewById(R.id.addText);
        usn = sp.getString("usn","");
        if(usn.equals("")){
            Toast.makeText(this, "Please signout and sign in again", Toast.LENGTH_SHORT).show();
        }
        else{
            getClasses();
        }
    }

    private void getClasses() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+usn,
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
                String name_json = jo.getString("code");
                code.add(name_json);
                String code_json = jo.getString("sname");
                sname.add(code_json);
                String link_json = jo.getString("fname");
                fname.add(link_json);
                String type_json = jo.getString("profilepic");
                fphoto.add(type_json);
                String year_json = jo.getString("ccode");
                ccode.add(year_json);
                fid.add(jo.getString("fid"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new addElectiveAdapter(code,sname,ccode,fname,fphoto,fid,addBut,addText,this);
        recyclerView.setAdapter(adapter);
    }
}
