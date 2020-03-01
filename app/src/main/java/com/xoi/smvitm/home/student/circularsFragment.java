package com.xoi.smvitm.home.student;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class circularsFragment extends Fragment {
    private static final String URL_DATA = "http://smvitmapp.xtoinfinity.tech/php/home/circulars.php";
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<CircularItems> circularItems;
    LottieAnimationView loadAnim;
    TextView loadTxt;


    public circularsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_circulars, container, false);

        loadAnim = (LottieAnimationView)view.findViewById(R.id.loadanim);
        loadTxt = (TextView)view.findViewById(R.id.loadtxt);

        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        circularItems = new ArrayList<>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
        getItems();
            }
        });

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

                        CircularItems items = new CircularItems(

                                jo.getString("circular_title"),
                                jo.getString("circular_date"),
                                jo.getString("circular_pdflink")
                        );

                        circularItems.add(items);
                    }

                    adapter = new CircularAdapter(circularItems,getActivity().getApplicationContext());
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
class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.ViewHolder> {

    private List<CircularItems> circularList;
    private Context context;

    public CircularAdapter(List<CircularItems> circularList, Context context) {
        this.circularList = circularList;
        this.context = context;
    }

    @NonNull
    @Override
    public CircularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circulars_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CircularAdapter.ViewHolder holder, int position) {

        CircularItems items = circularList.get(position);

        holder.circular_title.setText(items.getCircular_title());
        holder.circular_date.setText(items.getCircular_date());
        final String circular_pdflink = items.getCircular_pdflink();

        Glide.with(context)
                .load(R.drawable.college_logo)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


        holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, PDFReader.class);
                    intent.putExtra("pdfurl", circular_pdflink);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return circularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView circular_title, circular_date;
        public ImageView imgview;
        public Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            circular_title = (TextView) itemView.findViewById(R.id.circular_title);
            circular_date = (TextView) itemView.findViewById(R.id.circular_date);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button = (Button) itemView.findViewById(R.id.viewpdf_button);


        }
    }
}
class CircularItems {

    private String circular_title;
    private String circular_date;
    private String circular_pdflink;



    public CircularItems(String circular_title, String circular_date, String circular_pdflink) {
        this.circular_title = circular_title;
        this.circular_date = circular_date;
        this.circular_pdflink = circular_pdflink;
    }

    public String getCircular_title() {
        return circular_title;
    }

    public String getCircular_date() {
        return circular_date;
    }

    public String getCircular_pdflink() {
        return circular_pdflink;
    }
}