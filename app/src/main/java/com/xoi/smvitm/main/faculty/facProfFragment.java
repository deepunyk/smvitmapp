package com.xoi.smvitm.main.faculty;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.xoi.smvitm.auth.facultyLogin;
import com.xoi.smvitm.profile.editFacProfActivity;
import com.xoi.smvitm.profile.editStudProfActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.Context.MODE_PRIVATE;

public class facProfFragment extends Fragment {

    TextView fidTxt, nameTxt, mobTxt, brTxt, emailTxt, chckTxt;
    CircularImageView proPic;
    String url = "http://smvitmapp.xtoinfinity.tech/php/facDetails.php?fid=";
    String permUrl ="http://smvitmapp.xtoinfinity.tech/php/permission.php?id=";

    SharedPreferences sharedPreferences;
    String fid, name, mobile, br, email, pic, pass;
    Button outBut, editBut;
    String permCircular, permEvent, permFacClass;

    ConstraintLayout loadLayout;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    ScrollView profScroll;

    public facProfFragment() {
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fac_prof, container, false);
        sharedPreferences = getActivity().getSharedPreferences("com.xoi.smvitm",MODE_PRIVATE);

        fid= sharedPreferences.getString("fid","");

        outBut = (Button)view.findViewById(R.id.outBut);
        editBut = (Button)view.findViewById(R.id.editBut);
        fidTxt = (TextView)view.findViewById(R.id.fidTxt);
        nameTxt = (TextView)view.findViewById(R.id.nameTxt);
        mobTxt = (TextView)view.findViewById(R.id.mobTxt);
        brTxt = (TextView)view.findViewById(R.id.brTxt);
        emailTxt = (TextView)view.findViewById(R.id.emailTxt);
        proPic = (CircularImageView)view.findViewById(R.id.profileImg);
        chckTxt = (TextView)view.findViewById(R.id.chckTxt);

        loadAnim = (LottieAnimationView)view.findViewById(R.id.loadanim);
        loadLayout = (ConstraintLayout)view.findViewById(R.id.loadLayout);
        loadTxt = (TextView)view.findViewById(R.id.loadtxt);
        profScroll = (ScrollView)view.findViewById(R.id.profScroll);


        load();

        outBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(getActivity(), facultyLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), editFacProfActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        fidTxt.setText(fid);


        AsyncJob.OnBackgroundJob usrDetails = new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    getDetails();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                    }
                });
            }
        };

        AsyncJob.OnBackgroundJob permDetails = new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    getPermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                        doneLoad();
                    }
                });
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AsyncJob.doInBackground(usrDetails, executorService);
        AsyncJob.doInBackground(permDetails, executorService);

        return view;
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                pass = jo.getString("password");
            }
            nameTxt.setText(name);
            mobTxt.setText(mobile);
            emailTxt.setText(email);
            setBranch();
            Glide.with(this).load(pic).into(proPic);
            sharedPreferences.edit().putString("fid",fid).apply();
            sharedPreferences.edit().putString("name",name).apply();
            sharedPreferences.edit().putString("mobile",mobile).apply();
            sharedPreferences.edit().putString("branch",br).apply();
            sharedPreferences.edit().putString("email",email).apply();
            sharedPreferences.edit().putString("profilePic",pic).apply();
            sharedPreferences.edit().putString("pass",pass).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getPermission(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, permUrl+fid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems1(response);
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void parseItems1(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("permission");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                permCircular = jo.getString("circular");
                permEvent = jo.getString("event");
                permFacClass = jo.getString("facClass");
            }
            sharedPreferences.edit().putString("permcircular",permCircular).apply();
            sharedPreferences.edit().putString("permevent",permEvent).apply();
            sharedPreferences.edit().putString("permfacclass",permFacClass).apply();
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

    public void load(){
        loadTxt.setVisibility(View.VISIBLE);
        loadLayout.setVisibility(View.VISIBLE);
        loadAnim.setVisibility(View.VISIBLE);
        profScroll.setVisibility(View.GONE);
    }

    public void doneLoad(){
        loadTxt.setVisibility(View.GONE);
        loadLayout.setVisibility(View.GONE);
        loadAnim.setVisibility(View.GONE);
        profScroll.setVisibility(View.VISIBLE);
    }


}
