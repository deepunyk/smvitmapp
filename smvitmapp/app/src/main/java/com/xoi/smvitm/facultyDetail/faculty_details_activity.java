package com.xoi.smvitm.facultyDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.xoi.smvitm.R;
import com.xoi.smvitm.classroom.allotFacClassRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class faculty_details_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private faculty_detail_rv_adapter adapter;
    private String url = "http://smvitmapp.xtoinfinity.tech/php/faculty/details.php?br=";
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> mobile = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> profImg = new ArrayList<>();
    MaterialSpinner brSpin;
    String brAr[] = {"Computer Science","Electronics","Mechanical","Civil","Basic Science"};
    String curItem = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details_activity);

        brSpin = (MaterialSpinner)findViewById(R.id.brSpin);
        brSpin.setItems(brAr);

        getFaculties();

        brSpin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                curItem = Integer.toString(position+1);
                getFaculties();
            }
        });
    }

    private void getFaculties(){
        name.clear();
        fid.clear();
        mobile.clear();
        email.clear();
        profImg.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+curItem,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")) {
                            Toast.makeText(faculty_details_activity.this, "No faculties", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems1(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("faculties");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                fid.add(jo.optString("fid"));
                name.add(jo.optString("name"));
                mobile.add(jo.optString("mobile"));
                email.add(jo.optString("email"));
                profImg.add(jo.optString("profilepic"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new faculty_detail_rv_adapter(name,fid,mobile,email,profImg,this);
        recyclerView.setAdapter(adapter);
    }

}
