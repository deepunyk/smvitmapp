package com.xoi.smvitm.varnothsava;

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
import com.xoi.smvitm.auth.loginActivity;
import com.xoi.smvitm.home.student.MainActivity;
import com.xoi.smvitm.main.student.studMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class vLoginActivity extends AppCompatActivity {
    EditText usnTxt, passTxt;
    Button logBut;
    String pid, pid1, id, phonenumber;
    String url = "http://smvitmapp.xtoinfinity.tech/php/varnothsava/login.php";
    String url1 = "http://smvitmapp.xtoinfinity.tech/php/varnothsava/userdetails.php";
    SharedPreferences sharedPreferences;
    ImageView bckImg;
    Animation _translateAnimation;
    ConstraintLayout loadLayout;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    CardView logCard;
    TextView register;
    String fullname,usn,email,phno,college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_login);
        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        usnTxt = (EditText) findViewById(R.id.usnTxt);
        passTxt = (EditText) findViewById(R.id.passTxt);
        logBut = (Button) findViewById(R.id.logBut);
        bckImg = (ImageView) findViewById(R.id.bckImg);
        register = (TextView) findViewById(R.id.register);

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vLoginActivity.this, vRegisterActivity.class);
                startActivity(intent);
            }
        });


        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pid1 = usnTxt.getText().toString();
                    id = pid1.substring(6);
                    int num = Integer.parseInt(id);
                    int result = num - 1000;
                    pid = String.valueOf(result);
                    phonenumber = passTxt.getText().toString();
                   // Toast.makeText(vLoginActivity.this, "" + pid + phonenumber, Toast.LENGTH_SHORT).show();
                    if (pid1.equals("") || phonenumber.equals("")) {
                        Toast.makeText(vLoginActivity.this, "Please fill both the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        login();
                    }
                } catch (Exception e) {
                    Toast.makeText(vLoginActivity.this, "Enter valid pid and phonenumber", Toast.LENGTH_SHORT).show();
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
                            getDetails();
                            Toast.makeText(vLoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                        }
                        if (response.equals("error;")) {
                            Toast.makeText(vLoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(vLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid", pid);
                params.put("phonenumber", phonenumber);
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

    private void getDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1+"?id="+pid,
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
            JSONArray jarray = jobj.getJSONArray("result");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                fullname = jo.getString("fullname");
                usn = jo.getString("usn");
                email = jo.getString("email");
                phno = jo.getString("phonenumber");
                college = jo.getString("college");
            }
            sharedPreferences.edit().putString("pid",pid1).apply();
            sharedPreferences.edit().putString("fullname",fullname).apply();
            sharedPreferences.edit().putString("usn",usn).apply();
            sharedPreferences.edit().putString("email",email).apply();
            sharedPreferences.edit().putString("phonenumber",phno).apply();
            sharedPreferences.edit().putString("college",college).apply();
            Intent intent = new Intent(vLoginActivity.this, studMainActivity.class);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

