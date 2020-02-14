package com.xoi.smvitm.classroom;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class allotFacClassFragment extends Fragment{
    public allotFacClassFragment() {
    }
    private String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/showFaculties.php?br=";
    private String Suburl = "http://smvitmapp.xtoinfinity.tech/php/classroom/showSubjects.php?br=";
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> sid = new ArrayList<>();
    private RecyclerView recyclerView;
    private allotFacClassRVAdapter adapter;
    private View view;
    String selectSem, selectSec, selectBr = "1";
    Button allotBut, remSubBut, skipBut;
    TextView snameTxt, sidTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.allot_facclass_fragment, container, false);
        selectSem = ((allotClassActivity)getActivity()).selectSem;
        selectSec = ((allotClassActivity)getActivity()).selectSec;

        allotBut = (Button) view.findViewById(R.id.allotBut);
        remSubBut = (Button) view.findViewById(R.id.remSubBut);
        skipBut = (Button) view.findViewById(R.id.skipBut);
        snameTxt = (TextView) view.findViewById(R.id.snameTxt);
        sidTxt = (TextView) view.findViewById(R.id.sidTxt);
        getSubjects();
        return view;
    }
    private void allotClass(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+selectBr,
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
            JSONArray jarray = jobj.getJSONArray("faculties");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                fname.add(jo.optString("name"));
                fid.add(jo.optString("fid"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getSubjects(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Suburl+""+selectBr+"&sem="+selectSem,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Empty")) {
                            Toast.makeText(getActivity(), "No subjects", Toast.LENGTH_SHORT).show();
                        }else{
                            parseItems1(response);
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

    private void parseItems1(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("subjects");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                sid.add(jo.optString("code"));
                sname.add(jo.optString("name"));
            }
            allotClass();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new allotFacClassRVAdapter(fname,fid,sname,sid,snameTxt,sidTxt,allotBut,remSubBut, skipBut, selectSec,selectBr,selectSem,getActivity());
        recyclerView.setAdapter(adapter);
    }
}
