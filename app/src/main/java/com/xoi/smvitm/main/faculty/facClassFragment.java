package com.xoi.smvitm.main.faculty;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xoi.smvitm.R;
import com.xoi.smvitm.classroom.allotClassActivity;
import com.xoi.smvitm.classroom.studAddElectiveActivity;
import com.xoi.smvitm.classroom.studClassDisplayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class facClassFragment extends Fragment {


    View view;
    String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/getFacultyClassroom.php?fid=";
    SharedPreferences sp;
    String logfid, permission;
    RecyclerView recyclerView;
    private ArrayList<String> code = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> ccode = new ArrayList<>();
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fphoto = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    studClassDisplayAdapter adapter;
    Toolbar toolbar;
    FloatingActionButton fab;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    public facClassFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_studclass, container, false);
        sp = getActivity().getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        permission = sp.getString("permfacclass","");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        loadAnim = (LottieAnimationView)view.findViewById(R.id.noClassAnim);
        loadTxt = (TextView)view.findViewById(R.id.loadTxt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), allotClassActivity.class);
                startActivity(i);
            }
        });

        if(permission.equals("1")){
            fab.show();
        }else{
            fab.hide();
        }
        logfid = sp.getString("fid","");
        if(logfid.equals("")){
            Toast.makeText(getContext(), "Please signout and sign in again", Toast.LENGTH_SHORT).show();
        }
        else{
            load();
            refreshList();
        }
        return view;

    }
    private void getClasses() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+logfid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            if(permission.equals("1")){
                                loadTxt.setText("Click on the add button below to allot classes.");
                            }else {
                                loadTxt.setText("No classrooms found.\nPlease wait for your HoD to allot you to your respective classrooms.");
                            }
                            }else {
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
                String name_json = jo.getString("code");
                code.add(name_json);
                String code_json = jo.getString("sname");
                sname.add(code_json);
                String link_json = jo.getString("fname");
                fname.add(link_json);
                String type_json = jo.getString("profilepic");
                fphoto.add(type_json);
                String year_json = jo.getString("ccode");
                ccode.add(year_json);
                fid.add(jo.getString("fid"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        doneLoad();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new studClassDisplayAdapter(code,sname,ccode,fname,fphoto,fid,getActivity());
        recyclerView.setAdapter(adapter);
    }
    private void load(){
        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);
        loadTxt.setText("Getting your classrooms");
    }
    private void doneLoad(){
        loadAnim.setVisibility(View.GONE);
        loadTxt.setVisibility(View.GONE);
    }

    private void refreshList(){
        code.clear();
        sname.clear();
        fname.clear();
        fphoto.clear();
        ccode.clear();
        fid.clear();
        getClasses();
    }
}
