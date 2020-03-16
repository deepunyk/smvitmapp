package com.xoi.smvitm.academics;


import android.os.Bundle;

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
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class qpaperFragment extends Fragment {

    ArrayList<String> title_temp = new ArrayList<>();
    ArrayList<String> author_temp = new ArrayList<>();
    ArrayList<String> pdflink_temp = new ArrayList<>();
    LottieAnimationView loadAnim;
    TextView loadTxt;

    public qpaperFragment() {
        // Required empty public constructor
    }
    View view;
    RecyclerView recyclerView;
    String url = "http://smvitmapp.xtoinfinity.tech/php/getQpapers.php?scode=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_qpaper, container, false);
        String scode;
        scode =getActivity().getIntent().getExtras().getString("subcode");
        loadAnim = view.findViewById(R.id.loadanim);
        loadTxt = view.findViewById(R.id.loadTxt);
        url = "http://smvitmapp.xtoinfinity.tech/php/getstudymaterials.php?scode="+scode;
        title_temp.clear();
        author_temp.clear();
        getDetails();
        return view;
    }

    private void getDetails() {
        //progressdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            loadTxt.setVisibility(View.VISIBLE);
                            loadAnim.setVisibility(View.VISIBLE);
                        }
                        else {
                            loadTxt.setVisibility(View.GONE);
                            loadAnim.setVisibility(View.GONE);
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
            JSONArray jarray = jobj.getJSONArray("student");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String title = jo.getString("type");
                String author = jo.getString("year");
                String pdflink = jo.getString("pdflink");
                title_temp.add(title);
                author_temp.add(author);
                pdflink_temp.add(pdflink);
            }
            recyclerView =view.findViewById(R.id.recyclerView);
            noteslistAdapter adapter = new noteslistAdapter(title_temp,author_temp,pdflink_temp,getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
