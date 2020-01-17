package com.xoi.smvitm.academics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class subject_selectActivity extends AppCompatActivity {
    SharedPreferences sp;
    ArrayList<String> title_temp = new ArrayList<>();
    ArrayList<String> code_temp = new ArrayList<>();
    public int loadbit1=0;
    public ProgressDialog progressdialog;
    View view;
    RecyclerView recyclerView;
    String studUrl = "http://smvitmapp.xtoinfinity.tech/php/getsubjects.php?usn=";
    String facUrl = "http://smvitmapp.xtoinfinity.tech/php/getsubjectsfac.php?fid=";
    String url;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        sp = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        if(sp.contains("usn")){
            id = sp.getString("usn","");
            url = studUrl;
            Toast.makeText(subject_selectActivity.this,""+id,Toast.LENGTH_LONG).show();
        }else{
            id = sp.getString("fid","");
            url = facUrl;
            Toast.makeText(subject_selectActivity.this,""+id,Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        getDetails();
    }
    private void getDetails() {
        //progressdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url+""+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(subject_selectActivity.this,""+response,Toast.LENGTH_LONG).show();

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
                String subcode = jo.getString("code");
                String subname = jo.getString("sname");
                title_temp.add(subname);
                code_temp.add(subcode);
            }
            recyclerView =(RecyclerView)findViewById(R.id.recycleView1);
            subjectAdapter adapter = new subjectAdapter(title_temp,code_temp,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
