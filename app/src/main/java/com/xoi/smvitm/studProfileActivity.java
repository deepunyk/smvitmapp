package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class studProfileActivity extends AppCompatActivity {

    TextView usnTxt, nameTxt, secTxt, semTxt, brTxt, emailTxt;
    CircularImageView proPic;
    String url = "http://smvitmapp.xtoinfinity.tech/php/studDetails.php?usn=";
    SharedPreferences sharedPreferences;
    String usn, name, sec, sem, br, email, pic;
    Button outBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_profile);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        usn= sharedPreferences.getString("usn","");

        outBut = (Button)findViewById(R.id.outBut);
        usnTxt = (TextView)findViewById(R.id.usnTxt);
        nameTxt = (TextView)findViewById(R.id.nameTxt);
        secTxt = (TextView)findViewById(R.id.secTxt);
        semTxt = (TextView)findViewById(R.id.semTxt);
        brTxt = (TextView)findViewById(R.id.brTxt);
        emailTxt = (TextView)findViewById(R.id.emailTxt);
        proPic = (CircularImageView)findViewById(R.id.profileImg);

        outBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(studProfileActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        usnTxt.setText(usn);

        getDetails();


    }

    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+usn,
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
            JSONArray jarray = jobj.getJSONArray("student");
            Toast.makeText(studProfileActivity.this, ""+jarray, Toast.LENGTH_LONG).show();
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                name = jo.getString("name");
                sem = jo.getString("sem");
                sec = jo.getString("section");
                email = jo.getString("email");
                pic = jo.getString("profilepic");
                br = jo.getString("branchId");
            }
            nameTxt.setText(name);
            semTxt.setText(sem);
            secTxt.setText(sec);
            emailTxt.setText(email);
            brTxt.setText(br);
            Glide.with(this).load(pic).into(proPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
