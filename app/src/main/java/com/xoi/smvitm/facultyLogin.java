package com.xoi.smvitm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class facultyLogin extends AppCompatActivity {

    EditText fidTxt, passTxt;
    Button logBut;
    TextView regTxt;
    String fid, pass;
    String url ="http://smvitmapp.xtoinfinity.tech/php/facLogin.php";
    SharedPreferences sharedPreferences;
    ImageView bckImg;
    Animation _translateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);
        fidTxt = (EditText)findViewById(R.id.fidTxt);
        passTxt = (EditText)findViewById(R.id.passTxt);
        logBut = (Button)findViewById(R.id.logBut);
        regTxt = (TextView) findViewById(R.id.regTxt);
        bckImg = (ImageView)findViewById(R.id.bckImg);

        _translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -100f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
        _translateAnimation.setDuration(10000);
        _translateAnimation.setRepeatCount(-1);
        _translateAnimation.setRepeatMode(Animation.REVERSE);
        _translateAnimation.setInterpolator(new LinearInterpolator());
        bckImg.setAnimation(_translateAnimation);

        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fid = fidTxt.getText().toString().toUpperCase();
                pass = passTxt.getText().toString();
                if(fid.equals("")||pass.equals("")){
                    Toast.makeText(facultyLogin.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                }else {
                    login();
                }
            }
        });
        regTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facultyLogin.this, facultyRegister.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(facultyLogin.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success;")){
                            sharedPreferences.edit().putString("fid",fid).apply();
                            sharedPreferences.edit().putString("login","2").apply();
                            Intent intent = new Intent(facultyLogin.this, facProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(facultyLogin.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("fid",fid);
                params.put("pass",pass);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(facultyLogin.this, selectLogin.class);
        startActivity(intent);
        finish();
    }
}
