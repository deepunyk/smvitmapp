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

public class facProfileActivity extends AppCompatActivity {

    TextView fidTxt, nameTxt, mobTxt, brTxt, emailTxt;
    CircularImageView proPic;
    String url = "http://smvitmapp.xtoinfinity.tech/php/facDetails.php?fid=";
    SharedPreferences sharedPreferences;
    String fid, name, mobile, br, email, pic;
    Button outBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_profile);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        fid= sharedPreferences.getString("fid","");

        outBut = (Button)findViewById(R.id.outBut);
        fidTxt = (TextView)findViewById(R.id.fidTxt);
        nameTxt = (TextView)findViewById(R.id.nameTxt);
        mobTxt = (TextView)findViewById(R.id.mobTxt);
        brTxt = (TextView)findViewById(R.id.brTxt);
        emailTxt = (TextView)findViewById(R.id.emailTxt);
        proPic = (CircularImageView)findViewById(R.id.profileImg);

        outBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(facProfileActivity.this, facultyLogin.class);
                startActivity(intent);
                finish();
            }
        });
        fidTxt.setText(fid);

        getDetails();


    }

    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+fid,
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
            JSONArray jarray = jobj.getJSONArray("faculty");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                name = jo.getString("name");
                mobile = jo.getString("mobile");
                email = jo.getString("email");
                pic = jo.getString("profilepic");
                br = jo.getString("branchId");
            }
            nameTxt.setText(name);
            mobTxt.setText(mobile);
            emailTxt.setText(email);
            setBranch();
            Glide.with(this).load(pic).into(proPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBranch(){
        if(br.equals("1")){
            brTxt.setText("Computer Science");
        }
        else if(br.equals("2")){
            brTxt.setText("Electronics");
        }
        else if(br.equals("3")){
            brTxt.setText("Mechanical");
        }
        else if(br.equals("4")){
            brTxt.setText("Civil");
        }
        else{
            brTxt.setText("Basic Science");
        }
    }
}
