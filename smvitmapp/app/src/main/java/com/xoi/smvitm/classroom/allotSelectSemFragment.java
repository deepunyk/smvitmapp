package com.xoi.smvitm.classroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;
import com.xoi.smvitm.home.student.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class allotSelectSemFragment extends Fragment{
    public allotSelectSemFragment() {
    }

    private String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/showClasses.php?br=";
    private ArrayList<String> sem = new ArrayList<>();
    private ArrayList<String> sec = new ArrayList<>();
    private RecyclerView recyclerView;
    private View view;
    private allotSelectSemRVAdapter adapter;
    private Button nextBut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.allot_selectsem_fragment, container, false);
        selectClass();
        nextBut = (Button)view.findViewById(R.id.nextBut);

        return view;
    }

    private void selectClass(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Empty")) {
                            Toast.makeText(getActivity(), "No classes", Toast.LENGTH_SHORT).show();
                        }else{
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("class");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                sem.add(jo.optString("semester"));
                sec.add(jo.optString("section"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new allotSelectSemRVAdapter(sem,sec,nextBut,getActivity());
        recyclerView.setAdapter(adapter);
    }
}
