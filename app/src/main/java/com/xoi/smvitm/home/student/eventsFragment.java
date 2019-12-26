package com.xoi.smvitm.home.student;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class eventsFragment extends Fragment {

    private static final String URL_DATA = "http://smvitmapp.xtoinfinity.tech/php/home/events.php";
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<EventItems> eventItems;
    LottieAnimationView loadAnim;
    TextView loadTxt;

    public eventsFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_events, container, false);
        loadAnim = (LottieAnimationView)view.findViewById(R.id.loadanim);
        loadTxt = (TextView)view.findViewById(R.id.loadtxt);

        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventItems = new ArrayList<>();

        getItems();

        return view;
    }

    private void getItems(){

        // final ProgressDialog loading =  ProgressDialog.show(getActivity(),"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // loading.dismiss();
                    loadAnim.setVisibility(View.GONE);
                    loadTxt.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo = jsonArray.getJSONObject(i);

                        EventItems items = new EventItems(

                                jo.getString("event_title"),
                                jo.getString("event_description"),
                                jo.getString("event_organizers"),
                                jo.getString("event_date"),
                                jo.getString("event_imglink"),
                                jo.getString("event_pdflink")
                        );

                        eventItems.add(items);
                    }

                    adapter = new EventAdapter(eventItems,getActivity().getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // loading.dismiss();
                loadAnim.setVisibility(View.GONE);
                loadTxt.setVisibility(View.GONE);
                Toast.makeText(getActivity().getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}

class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventItems> eventList;
    private Context context;

    public EventAdapter(List<EventItems> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {

        EventItems items = eventList.get(position);

        holder.event_title.setText(items.getEvent_title());
        holder.event_description.setText(items.getEvent_description());
        final String event_title = items.getEvent_title();
        final String imgurl = items.getEvent_imglink();
        final String event_description = items.getEvent_description();
        final String event_organizers = items.getEvent_organizers();
        final String event_date = items.getEvent_date();
        final String event_pdflink = items.getEvent_pdflink();

        Glide.with(context)
                .load(imgurl)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


        holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(context, EventDetails.class);
                    intent.putExtra("event_title", event_title);
                    intent.putExtra("event_description", event_description);
                    intent.putExtra("event_imgurl", imgurl);
                    intent.putExtra("event_date", event_date);
                    intent.putExtra("event_organizers", event_organizers);
                    intent.putExtra("event_pdflink", event_pdflink);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView event_title, event_description;
        public ImageView imgview;
        public Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            event_title = (TextView) itemView.findViewById(R.id.event_title);
            event_description = (TextView) itemView.findViewById(R.id.event_description);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button = (Button) itemView.findViewById(R.id.view_button);


        }
    }
}
class EventItems {

    private String event_title;
    private String event_description;
    private String event_organizers;
    private String event_date;
    private String event_imglink;
    private String event_pdflink;


    public EventItems(String event_title, String event_description, String event_organizers, String event_date, String event_imglink, String event_pdflink) {
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_organizers = event_organizers;
        this.event_date = event_date;
        this.event_imglink = event_imglink;
        this.event_pdflink = event_pdflink;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_organizers() {
        return event_organizers;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_imglink() {
        return event_imglink;
    }

    public String getEvent_pdflink() {
        return event_pdflink;
    }
}
