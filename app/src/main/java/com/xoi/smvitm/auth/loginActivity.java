package com.xoi.smvitm.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.xoi.smvitm.main.faculty.facMainActivity;
import com.xoi.smvitm.main.student.studMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    EditText usnTxt, passTxt;
    Button logBut;
    TextView regTxt;
    String usn, pass;
    String url = "http://smvitmapp.xtoinfinity.tech/php/login.php";
    String permUrl = "http://smvitmapp.xtoinfinity.tech/php/permission.php?id=";
    SharedPreferences sharedPreferences;
    String permCircular, permEvent, permFacClass;
    ImageView bckImg;
    Animation _translateAnimation;
    ConstraintLayout loadLayout;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    CardView logCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        usnTxt = (EditText) findViewById(R.id.usnTxt);
        passTxt = (EditText) findViewById(R.id.passTxt);
        logBut = (Button) findViewById(R.id.logBut);
        regTxt = (TextView) findViewById(R.id.regTxt);
        bckImg = (ImageView) findViewById(R.id.bckImg);

        _translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -100f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
        _translateAnimation.setDuration(10000);
        _translateAnimation.setRepeatCount(-1);
        _translateAnimation.setRepeatMode(Animation.REVERSE);
        _translateAnimation.setInterpolator(new LinearInterpolator());
        bckImg.setAnimation(_translateAnimation);

        loadAnim = (LottieAnimationView) findViewById(R.id.loadanim);
        loadLayout = (ConstraintLayout) findViewById(R.id.loadLayout);
        loadTxt = (TextView) findViewById(R.id.loadtxt);
        logCard = (CardView) findViewById(R.id.logCard);

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
                if (usn.equals("") || pass.equals("")) {
                    Toast.makeText(loginActivity.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }

    public void login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success;")) {
                            getPermission();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(loginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usn", usn);
                params.put("pass", pass);
                return params;
            }

            ;

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void getPermission() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, permUrl + usn,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("no")) {
                            sharedPreferences.edit().putString("usn", usn).apply();
                            sharedPreferences.edit().putString("login", "1").apply();
                            sharedPreferences.edit().putString("permcircular", "0").apply();
                            sharedPreferences.edit().putString("permevent", "0").apply();
                            sharedPreferences.edit().putString("permfacclass", "0").apply();
                            Intent intent = new Intent(loginActivity.this, studMainActivity.class);
                            startActivity(intent);
                            finish();
                            doneLoad();
                        } else {
                            parseItems1(response);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        doneLoad();

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems1(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("permission");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                permCircular = jo.optString("circular");
                permEvent = jo.optString("event");
                permFacClass = jo.optString("facClass");

            }
            sharedPreferences.edit().putString("usn", usn).apply();
            sharedPreferences.edit().putString("login", "1").apply();
            sharedPreferences.edit().putString("permcircular", permCircular).apply();
            sharedPreferences.edit().putString("permevent", permEvent).apply();
            sharedPreferences.edit().putString("permfacclass", permFacClass).apply();
            Intent intent = new Intent(loginActivity.this, facMainActivity.class);
            startActivity(intent);
            finish();
            doneLoad();
        } catch (JSONException e) {
            Intent intent = new Intent(loginActivity.this, facMainActivity.class);
            startActivity(intent);
            finish();
            doneLoad();
            e.printStackTrace();
        }
    }

    public void load() {
        loadTxt.setVisibility(View.VISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
        loadAnim.setVisibility(View.VISIBLE);
        logCard.setVisibility(View.GONE);
        regTxt.setVisibility(View.GONE);
    }

    public void doneLoad() {
        loadTxt.setVisibility(View.GONE);
        loadLayout.setVisibility(View.GONE);
        loadAnim.setVisibility(View.GONE);
        logCard.setVisibility(View.VISIBLE);
        regTxt.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(loginActivity.this, selectLogin.class);
        startActivity(intent);
        finish();
    }
}
