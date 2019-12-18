package com.xoi.smvitm.auth;

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
import com.xoi.smvitm.R;
import com.xoi.smvitm.main.student.studMainActivity;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    EditText usnTxt, passTxt;
    Button logBut;
    TextView regTxt;
    String usn, pass;
    String url ="http://smvitmapp.xtoinfinity.tech/php/login.php";
    SharedPreferences sharedPreferences;
    ImageView bckImg;
    Animation _translateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);
        usnTxt = (EditText)findViewById(R.id.usnTxt);
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



        regTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, studRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usn = usnTxt.getText().toString().toUpperCase();
                pass = passTxt.getText().toString();
                if(usn.equals("")||pass.equals("")){
                    Toast.makeText(loginActivity.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                }else {
                    login();
                }
            }
        });
    }

    public void login(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(loginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.equals("success;")){
                            sharedPreferences.edit().putString("usn",usn).apply();
                            sharedPreferences.edit().putString("login","1").apply();
                            Intent intent = new Intent(loginActivity.this, studMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(loginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("usn",usn);
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
        Intent intent = new Intent(loginActivity.this, selectLogin.class);
        startActivity(intent);
        finish();
    }
}
