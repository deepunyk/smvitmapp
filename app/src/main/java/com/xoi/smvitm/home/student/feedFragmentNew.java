package com.xoi.smvitm.home.student;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class feedFragmentNew extends Fragment {

    private String url = "http://smvitmapp.xtoinfinity.tech/php/home/feed_info.php";
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> photographer_name = new ArrayList<>();
    private ArrayList<String> blogger_name = new ArrayList<>();
    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> profilepic = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> user_id = new ArrayList<>();
    feedFragmentRVAdapter adapter;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    FloatingActionButton fab;
    TextView moreTxt, bckTxt;
    int n = 0;

    public feedFragmentNew() {
    }

    private RecyclerView recyclerView;
    private View view;
    private Button nextBut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_feed, container, false);
        moreTxt = (TextView)view.findViewById(R.id.moreTxt);
        bckTxt = (TextView)view.findViewById(R.id.bckTxt);
        loadAnim = (LottieAnimationView) view.findViewById(R.id.loadanim);
        loadTxt = (TextView) view.findViewById(R.id.loadtxt);

        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);

        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), feedPostActivity.class);
                startActivity(i);
            }
        });
        bckTxt.setVisibility(View.GONE);

        bckTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n-=5;
                getFeed();
            }
        });

        moreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n += 5;
                bckTxt.setVisibility(View.VISIBLE);
                getFeed();

            }
        });

        return view;
    }

    private void getFeed() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "?num="+n,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            Toast.makeText(getActivity(), "You have come to the end of the feed", Toast.LENGTH_SHORT).show();
                            n -= 5;
                        }else{
                            moreTxt.setVisibility(View.GONE);
                            bckTxt.setVisibility(View.GONE);
                            title.clear();
                            description.clear();
                            imglink.clear();
                            photographer_name.clear();
                            blogger_name.clear();
                            username.clear();
                            date.clear();
                            profilepic.clear();
                            fid.clear();
                            user_id.clear();
                            parseItems(response);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadAnim.setVisibility(View.GONE);
                        loadTxt.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
            }
        });
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("feed");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                fid.add(jo.optString("feed_id"));
                title.add(jo.optString("name"));
                description.add(jo.optString("description"));
                imglink.add(jo.optString("imglink"));
                photographer_name.add(jo.optString("photographer_name"));
                blogger_name.add(jo.optString("blogger_name"));
                username.add(jo.optString("user"));
                date.add(jo.optString("date"));
                profilepic.add(jo.optString("profilepic"));
                user_id.add(jo.optString("user_id"));
            }
            loadAnim.setVisibility(View.GONE);
            loadTxt.setVisibility(View.GONE);
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        if(n==0) {
            bckTxt.setVisibility(View.GONE);
        }
        else{
            bckTxt.setVisibility(View.VISIBLE);
        }
        moreTxt.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new feedFragmentRVAdapter(title, description, imglink,photographer_name,blogger_name,username,date,profilepic,fid,moreTxt,user_id,getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        getFeed();
        super.onResume();
    }
}
