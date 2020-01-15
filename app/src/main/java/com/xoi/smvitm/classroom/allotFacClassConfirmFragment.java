package com.xoi.smvitm.classroom;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class allotFacClassConfirmFragment extends Fragment {
    public allotFacClassConfirmFragment(ArrayList<String> sid,ArrayList<String> sname,ArrayList<String> fid,ArrayList<String> fname,String selectSec,String selectBr,String selectSem) {
        this.fname = fname;
        this.fid = fid;
        this.sname = sname;
        this.sid = sid;
        this.selectSem = selectSem;
        this.selectSec = selectSec;
        this.selectBr = selectBr;
    }

    private String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/showFaculties.php?br=";
    String addUrl = "http://smvitmapp.xtoinfinity.tech/php/classroom/addClassroom.php";
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> sid = new ArrayList<>();
    private View view;
    String selectSem, selectSec, selectBr;
    RecyclerView recyclerView;
    allotfccRVAdapter adapter;
    Button doneBut, editBut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.allot_facclass_confirm_fragment, container, false);
        doneBut = (Button)view.findViewById(R.id.doneBut);
        editBut = (Button)view.findViewById(R.id.editBut);
        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassroom();
            }
        });
        initRecyclerView();
        return view;
    }

    private void addClassroom(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                    params.put("fname", fname.toString());
                    params.put("fid", fid.toString());
                    params.put("sname", sname.toString());
                    params.put("sid", sid.toString());
                    params.put("ssem", selectSem);
                    params.put("ssec", selectSec);
                    params.put("sbr", selectBr);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new allotfccRVAdapter(fname,fid,sname,sid,doneBut,editBut,selectSec,selectBr,selectSem,getActivity());
        recyclerView.setAdapter(adapter);
    }
}