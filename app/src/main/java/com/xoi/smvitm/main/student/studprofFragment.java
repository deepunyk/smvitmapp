package com.xoi.smvitm.main.student;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.xoi.smvitm.R;
import com.xoi.smvitm.profile.editStudProfActivity;
import com.xoi.smvitm.auth.loginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class studprofFragment extends Fragment {

    View view;
    TextView usnTxt, nameTxt, secTxt, semTxt, brTxt, emailTxt;
    CircularImageView proPic;
    String url = "http://smvitmapp.xtoinfinity.tech/php/studDetails.php?usn=";
    SharedPreferences sharedPreferences;
    String usn, name, sec, sem, br, email, pic;
    Button outBut, editBut;


    public studprofFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_studprof, container, false);
        sharedPreferences = getActivity().getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        usn= sharedPreferences.getString("usn","");

        outBut = (Button)view.findViewById(R.id.outBut);
        usnTxt = (TextView)view.findViewById(R.id.usnTxt);
        nameTxt = (TextView)view.findViewById(R.id.nameTxt);
        secTxt = (TextView)view.findViewById(R.id.secTxt);
        semTxt = (TextView)view.findViewById(R.id.semTxt);
        brTxt = (TextView)view.findViewById(R.id.brTxt);
        emailTxt = (TextView)view.findViewById(R.id.emailTxt);
        proPic = (CircularImageView)view.findViewById(R.id.profileImg);
        editBut = (Button)view.findViewById(R.id.editBut);

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), editStudProfActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        outBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        usnTxt.setText(usn);

        getDetails();
        return view;
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("student");
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
            setBranch();
            sharedPreferences.edit().putString("Username",name).apply();
            sharedPreferences.edit().putString("Usersem",sem).apply();
            sharedPreferences.edit().putString("Usersec",sec).apply();
            sharedPreferences.edit().putString("Useremail",email).apply();
            sharedPreferences.edit().putString("Userbranch",br).apply();
            sharedPreferences.edit().putString("Userprofilepic",pic).apply();
            Glide.with(this).load(pic).into(proPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBranch(){
        if(br.equals("1")){
            br = "Computer Science";
            brTxt.setText(br);
        }
        else if(br.equals("2")){
            br = "Electronics";
            brTxt.setText(br);
        }
        else if(br.equals("3")){
            br = "Mechanical";
            brTxt.setText(br);
        }
        else{
            br = "Civil";
            brTxt.setText(br);
        }
    }

}
