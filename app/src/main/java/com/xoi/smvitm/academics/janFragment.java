package com.xoi.smvitm.academics;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class janFragment extends Fragment {


    public janFragment() {
        // Required empty public constructor
    }
    ArrayList<String> title_temp = new ArrayList<>();
    ArrayList<String> date_temp = new ArrayList<>();
    ArrayList<String> day_temp = new ArrayList<>();
    //    ArrayList<String> type_temp = new ArrayList<>();
    public int loadbit1=0;
    public  ProgressDialog progressdialog;
    View view;
    RecyclerView recyclerView;
    String url = "http://smvitmapp.xtoinfinity.tech/php/getcalendarevent.php?month=01";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_jan, container, false);
        // Inflate the layout for this fragment
//         String refreshbit = getActivity().getIntent().getExtras().getString("refreshbit");
//        if(!refreshbit.isEmpty())
//        {
//            getDetails();
//        }

        //Toast.makeText(getActivity(),""loadbit,Toast.LENGTH_LONG).show();
        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        progressdialog.show();
        title_temp.clear();
        date_temp.clear();
        day_temp.clear();
        getDetails();
        progressdialog.dismiss();

        return view;
    }

    private void getDetails() {
        //progressdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                String title = jo.getString("event_title");
                String date = jo.getString("event_date");
                String day = jo.getString("event_day");
                title_temp.add(title);
                date_temp.add(date);
                day_temp.add(day);
            }
            recyclerView =view.findViewById(R.id.recyclerView);
            calendarAdapter adapter = new calendarAdapter(title_temp,date_temp,day_temp,getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
