package com.xoi.smvitm.home.student;

import android.content.Intent;
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

    private String url = "http://smvitmapp.xtoinfinity.tech/php/home/feedInfoNew.php";
    private ArrayList<String> feedId = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> sId = new ArrayList<>();
    private ArrayList<String> sPic = new ArrayList<>();
    private ArrayList<String> sName = new ArrayList<>();
    private ArrayList<String> fId = new ArrayList<>();
    private ArrayList<String> fPic = new ArrayList<>();
    private ArrayList<String> fName = new ArrayList<>();
    feedFragmentRVAdapter adapter;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    FloatingActionButton fab;
    TextView moreTxt;
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

        try {
            moreTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    n += 5;
                    getFeed();

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void getFeed() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "?num="+n,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            Toast.makeText(getActivity(), "You have come to the end of the feed", Toast.LENGTH_SHORT).show();
                            n -= 5;
                        }else{
                            moreTxt.setVisibility(View.GONE);
                            /*title.clear();
                            description.clear();
                            imglink.clear();
                            photographer_name.clear();
                            blogger_name.clear();
                            username.clear();
                            date.clear();
                            profilepic.clear();
                            fid.clear();
                            user_id.clear();*/
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

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("feed");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                feedId.add(jo.optString("feedId"));
                title.add(jo.optString("title"));
                description.add(jo.optString("description"));
                imglink.add(jo.optString("imgLink"));
                date.add(jo.optString("date"));
                sName.add(jo.optString("sName"));
                sId.add(jo.optString("sId"));
                sPic.add(jo.optString("sPic"));
                fName.add(jo.optString("fName"));
                fId.add(jo.optString("fId"));
                fPic.add(jo.optString("fPic"));
            }
            loadAnim.setVisibility(View.GONE);
            loadTxt.setVisibility(View.GONE);
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        moreTxt.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager lm  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        if(n!=0) {
            lm.scrollToPositionWithOffset(n - 1, 0);
        }
        adapter = new feedFragmentRVAdapter(feedId,title, description,imglink, date, sId, sPic,sName,fId, fPic, fName,moreTxt,getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        getFeed();
        super.onResume();
    }
}
