package com.xoi.smvitm.varnothsava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.ydcool.lib.qrmodule.encoding.QrGenerator;


public class vMainActivity extends AppCompatActivity {

    SharedPreferences sp;
    private ConstraintLayout layout;
    private ConstraintSet main = new ConstraintSet();
    private ConstraintSet user = new ConstraintSet();
    private ConstraintSet detail = new ConstraintSet();
    private ConstraintLayout detailLayout;
    private ImageView qrCode;
    private String nameStr, pidStr;
    ConstraintLayout conLayout;
    boolean doubleBackToExitPressedOnce = false;
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> startTime = new ArrayList<>();
    private ArrayList<String> venue = new ArrayList<>();
    private ArrayList<String> rules = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> coordinator = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();
    private ArrayList<String> ruleBook = new ArrayList<>();
    String url = "http://smvitmapp.xtoinfinity.tech/php/varnothsava/event.php?type=Spotlight";
    RecyclerView recyclerView;

    FloatingActionButton fab;
    TextView nameTxt, idTxt;
    Button culOne, culTwo, techOne, techTwo, genOne, genTwo;

    ScrollView scrollLayout;

    private boolean altLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_main);

        layout = findViewById(R.id.layout);

        sp = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);

        nameStr = sp.getString("fullname","");
        pidStr = sp.getString("pid","");
        pidStr = pidStr.toUpperCase();

        main.clone(layout);
        user.clone(this, R.layout.main_user_altr);
        detail.clone(this, R.layout.main_user_scroll);

        culOne = (Button)findViewById(R.id.ocBut);
        culTwo = (Button)findViewById(R.id.tcBut);
        techOne = (Button)findViewById(R.id.otBut);
        techTwo = (Button)findViewById(R.id.ttBut);
        conLayout = (ConstraintLayout) findViewById(R.id.conLayout);

        conLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(vMainActivity.this,contactActivity.class);
                startActivity(i);
            }
        });

        culOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(vMainActivity.this,vEventActivity.class);
                i.putExtra("type", "Cultural");
                i.putExtra("date", "2020-03-20");
                startActivity(i);
            }
        });

        culTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(vMainActivity.this,vEventActivity.class);
                i.putExtra("type", "Cultural");
                i.putExtra("date", "2020-03-21");
                startActivity(i);
            }
        });

        techOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(vMainActivity.this,vEventActivity.class);
                i.putExtra("type", "Technical");
                i.putExtra("date", "2020-03-20");
                startActivity(i);
            }
        });

        techTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(vMainActivity.this,vEventActivity.class);
                i.putExtra("type", "Technical");
                i.putExtra("date", "2020-03-21");
                startActivity(i);
            }
        });


        fab = (FloatingActionButton)findViewById(R.id.fab);

        detailLayout = (ConstraintLayout)findViewById(R.id.detailLayout);
        qrCode = (ImageView)findViewById(R.id.qrImg);
        scrollLayout = (ScrollView)findViewById(R.id.scrollLayout);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        idTxt = (TextView) findViewById(R.id.idTxt);

        nameTxt.setText(nameStr);
        idTxt.setText(pidStr);

        fab.hide();

        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_fab));
                fab.show();
                Transition changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(layout, changeBounds);
                user.applyTo(layout);
            }
        });

        detailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_fab));
                fab.show();
                Transition changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(layout, changeBounds);
                detail.applyTo(layout);
            }
        });

        scrollLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_fab));
                fab.show();
                Transition changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(layout, changeBounds);
                detail.applyTo(layout);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.hide();
                Transition changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(layout, changeBounds);
                main.applyTo(layout);
            }
        });


        Bitmap qrcode = null;
        try {
            qrcode = new QrGenerator.Builder()
                    .content(pidStr)
                    .qrSize(300)
                    .margin(2)
                    .color(Color.BLACK)
                    .bgColor(Color.WHITE)
                    .ecc(ErrorCorrectionLevel.H)
                    .overlay(this,R.drawable.vlog)
                    .overlaySize(100)
                    .overlayAlpha(255)
                    .overlayXfermode(PorterDuff.Mode.SRC_ATOP)
                    .encode();
        } catch (WriterException e) {
            e.printStackTrace();
        }

        qrCode.setImageBitmap(qrcode);

        getEvents();
    }


        private void getEvents() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("no")){
                                    Toast.makeText(vMainActivity.this, "No events found", Toast.LENGTH_SHORT).show();
                                }else{
                                    parseItems(response);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(vMainActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                );
                int socketTimeOut = 50000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(vMainActivity.this);
                queue.add(stringRequest);
            }
        });
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("events");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                date.add(jo.optString("date"));
                name.add(jo.optString("name"));
                startTime.add(jo.optString("startTime"));
                venue.add(jo.optString("venue"));
                rules.add(jo.optString("rules"));
                type.add(jo.optString("type"));
                coordinator.add(jo.optString("coordinator"));
                id.add(jo.optString("id"));
                photo.add(jo.optString("photo"));
                ruleBook.add(jo.optString("ruleBook"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.spotlightRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
        vSpotAdapter adapter = new vSpotAdapter(date, name, startTime, venue, rules, type,coordinator, id, photo, ruleBook, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if(sp.getString("login","").equals("1")||sp.getString("login","").equals("2")){
            finish();
        }else {
            if (doubleBackToExitPressedOnce) {
                finish();
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
}
