package com.xoi.smvitm.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
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
import com.arasthel.asyncjob.AsyncJob;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class classroomCommmentActivity extends AppCompatActivity {

    TextView nameTxt, dateTxt, descTxt, loadTxt;
    CircularImageView photoImg;
    SharedPreferences sp;
    String usn;
    String name, photo, desc, datetime, cfid, ccode, post;
    String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/getClassroomComments.php?code=";
    String posturl = "http://smvitmapp.xtoinfinity.tech/php/classroom/postClassroomComment.php";
    private ArrayList<String> descAr = new ArrayList<>();
    private ArrayList<String> profilepicAr = new ArrayList<>();
    private ArrayList<String> usnAr = new ArrayList<>();
    private ArrayList<String> nameAr = new ArrayList<>();
    private ArrayList<String> datetimeAr = new ArrayList<>();
    String fname, fphoto;
    classroomCommentAdapter adapter;
    RecyclerView recyclerView;
    EditText postTxt;
    ImageButton postBut;
    LottieAnimationView loadAnim, emptyloadanim;
    ConstraintLayout loadLayout;
    TextView emptyloadtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_commment);

        sp = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        loadAnim = (LottieAnimationView) findViewById(R.id.loadanim);
        emptyloadanim = (LottieAnimationView) findViewById(R.id.emptyloadanim);
        emptyloadtxt = (TextView) findViewById(R.id.emptyloadtxt);
        loadLayout = (ConstraintLayout) findViewById(R.id.loadLayout);
        loadTxt = (TextView) findViewById(R.id.loadtxt);


        if(sp.getString("login","").equals("1")) {
            usn = sp.getString("usn", "");
        }else{
            usn = sp.getString("fid", "");
        }
        ccode = getIntent().getStringExtra("ccode");
        name = getIntent().getStringExtra("name");
        photo = getIntent().getStringExtra("photo");
        desc = getIntent().getStringExtra("desc");
        datetime = getIntent().getStringExtra("datetime");
        cfid = getIntent().getStringExtra("cfid");
        fname = getIntent().getStringExtra("fname");
        fphoto = getIntent().getStringExtra("fphoto");

        postBut = (ImageButton) findViewById(R.id.postBut);
        postTxt = (EditText) findViewById(R.id.postTxt);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        descTxt = (TextView) findViewById(R.id.descTxt);
        photoImg = (CircularImageView)findViewById(R.id.photoImg);

        Glide.with(this).load(photo).into(photoImg);


        nameTxt.setText(name);
        dateTxt.setText(datetime);
        descTxt.setText(desc);

        load();

        postBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBut.animate().alpha(0).setDuration(100);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        postBut.animate().alpha(1).setDuration(100);
                    }
                }, 100);
                post = postTxt.getText().toString().trim();
                if (post.equals("")) {
                    Toast.makeText(classroomCommmentActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                } else {
                    postTxt.setText("");
                    hideKeyboard(classroomCommmentActivity.this);
                    load();
                    comment();
                    descAr.clear();
                    datetimeAr.clear();
                    nameAr.clear();
                    profilepicAr.clear();
                    usnAr.clear();
                }
            }
        });
        getClassroomFeed();
    }

    public void getClassroomFeed() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "" + cfid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Empty")) {
                            doneLoad();
                            emptyloadanim.setVisibility(View.VISIBLE);
                            emptyloadtxt.setText("No comments");
                        } else {
                            emptyloadanim.setVisibility(View.GONE);
                            emptyloadtxt.setText("Comments");
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
                descAr.add(jo.optString("description"));
                usnAr.add(jo.optString("usn"));
                nameAr.add(jo.optString("name"));
                profilepicAr.add(jo.optString("profilepic"));
                datetimeAr.add(jo.optString("datetime"));
            }
            initRecyclerView();
            doneLoad();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new classroomCommentAdapter(descAr, usnAr, nameAr, profilepicAr, datetimeAr, fname, fphoto, this);
        recyclerView.setAdapter(adapter);
    }

    private void comment() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, posturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getClassroomFeed();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(classroomCommmentActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", usn);
                params.put("description", post);
                params.put("cfid", cfid);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void load() {
        loadTxt.setVisibility(View.VISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
        loadAnim.setVisibility(View.VISIBLE);
    }

    public void doneLoad() {
        loadTxt.setVisibility(View.GONE);
        loadLayout.setVisibility(View.GONE);
        loadAnim.setVisibility(View.GONE);
    }
}
