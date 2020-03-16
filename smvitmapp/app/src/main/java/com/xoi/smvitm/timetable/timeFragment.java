package com.xoi.smvitm.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.xoi.smvitm.classroom.allotSelectSemRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class timeFragment extends Fragment {

    View view;
    int selectDay;
    private String url = "http://smvitmapp.xtoinfinity.tech/php/timetable/getTimetable.php?br=";
    private ArrayList<String> sub = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private RecyclerView recyclerView;
    timeMainRVAdapter adapter;
    SharedPreferences sp;
    private String branch, semester, section, day;
    private TextView day_txt;

    public timeFragment(int selectDay){
        this.selectDay = selectDay;
        convertDay();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timetable_fragment,container,false);

        sp = getActivity().getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);
        branch = sp.getString("Userbranch","");
        semester = sp.getString("Usersem","");
        section = sp.getString("Usersec","");

        day_txt = view.findViewById(R.id.day_txt);
        day_txt.setText(day);
        getTimetable();
        return view;
    }

    private void getTimetable(){
        sub.clear();
        time.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+branch+"&sem="+semester+"&sec="+section+"&day="+day,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")) {
                            Toast.makeText(getActivity(), "No classes", Toast.LENGTH_SHORT).show();
                        }else{
                            parseItems(response);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
            JSONArray jarray = jobj.getJSONArray("timetable");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                sub.add(jo.optString("name"));
                time.add(jo.optString("time"));
            }
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new timeMainRVAdapter(time,sub,getActivity());
        recyclerView.setAdapter(adapter);
    }


    private void convertDay(){
        switch (selectDay){
            case 1 :
                day = "MONDAY";
                break;
            case 2 :
                day = "TUESDAY";
                break;
            case 3 :
                day = "WEDNESDAY";
                break;
            case 4 :
                day = "THURSDAY";
                break;
            case 5 :
                day = "FRIDAY";
                break;
            case 6 :
                day = "SATURDAY";
                break;
            default:
                day = "MONDAY";
        }
    }
}
