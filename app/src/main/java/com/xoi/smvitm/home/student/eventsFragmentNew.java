package com.xoi.smvitm.home.student;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class eventsFragmentNew extends Fragment {

    private String url = "http://smvitmapp.xtoinfinity.tech/php/home/events.php";
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> organizer = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> pdflink = new ArrayList<>();

    eventsFragmentRVAdapter adapter;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private View view;

    public eventsFragmentNew() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_events, container, false);
        loadAnim = (LottieAnimationView) view.findViewById(R.id.loadanim);
        loadTxt = (TextView) view.findViewById(R.id.loadtxt);

        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);

        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), eventsPostActivity.class);
                startActivity(i);
            }
        });
        getFeed();


        return view;
    }

    private void getFeed() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                    parseItems(response);

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
            JSONArray jarray = jobj.getJSONArray("result");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                title.add(jo.optString("event_title"));
                description.add(jo.optString("event_description"));
                imglink.add(jo.optString("event_imglink"));
                date.add(jo.optString("event_date"));
                organizer.add(jo.optString("event_organizers"));
                pdflink.add(jo.optString("event_pdflink"));
            }
            loadAnim.setVisibility(View.GONE);
            loadTxt.setVisibility(View.GONE);

            recyclerView = view.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new eventsFragmentRVAdapter(title, description, imglink,pdflink,date,organizer,getActivity());
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
