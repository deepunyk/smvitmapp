package com.xoi.smvitm.academics;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    LottieAnimationView emptyAnim;
    TextView emptyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        sp = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        emptyAnim = (LottieAnimationView)findViewById(R.id.emptyloadanim);
        emptyTxt = (TextView)findViewById(R.id.emptyTxt);

        if(sp.contains("usn")){
            id = sp.getString("usn","");
            url = studUrl;
        }else{
            id = sp.getString("fid","");
            url = facUrl;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getDetails();
    }
    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url+""+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            emptyAnim.setVisibility(View.VISIBLE);
                            emptyTxt.setVisibility(View.VISIBLE);
                        }else {
                            parseItems(response);
                        }
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
