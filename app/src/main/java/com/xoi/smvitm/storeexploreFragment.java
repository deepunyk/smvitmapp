package com.xoi.smvitm;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class storeexploreFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> shortdesc = new ArrayList<>();
    private ArrayList<String> rating = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> image = new ArrayList<>();

    public storeexploreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_storeexplore, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storeProductsAdapter storeProductsAdapter = new storeProductsAdapter(title,shortdesc,rating,price,image,getContext());
        recyclerView.setAdapter(storeProductsAdapter);
        recyclerView = view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseJSON();

        /*title = new ArrayList<>();
        shortdesc = new ArrayList<>();
        rating = new ArrayList<>();
        price = new ArrayList<>();
        image = new ArrayList<>();

        title.add("Arduino UNO 3 Development Board");
        shortdesc.add("Micro Controller");
        rating.add("4.5");
        price.add("450");
        image.add("https://troupertech.com/SMVITMAPP/arduino.png");

        title.add("Arduino UNO 3 Development Board");
        shortdesc.add("Micro Controller");
        rating.add("4.5");
        price.add("450");
        image.add("https://troupertech.com/SMVITMAPP/arduino.png");*/

   }


    private void parseJSON() {
        String url = "https://troupertech.com/Smartmeter/test.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseProduct(response);
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

    public void parseProduct(String jsonResposnce){
        try {

            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jsonArray = jobj.getJSONArray("product");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject product = jsonArray.getJSONObject(i);

                title.add(product.getString("title"));
                shortdesc.add(product.getString("shortdesc"));
                rating.add(product.getString("rating"));
                price.add(product.getString("price"));
                image.add(product.getString("image"));
            }
            if(title.get(0).equals("-")){
                noData();
            }else {
                initRecyclerView();
            }

            /*storeProductsAdapter storeProductsAdapter = new storeProductsAdapter(title,shortdesc,rating,price,image,getActivity());
            recyclerView.setAdapter(storeProductsAdapter);*/
            //storeProductsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void noData(){

    }
    void initRecyclerView()  {
        recyclerView = view.findViewById(R.id.rv);
        storeProductsAdapter adapter = new storeProductsAdapter(title,shortdesc,rating,price,image,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }
}