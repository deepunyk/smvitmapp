package com.xoi.smvitm.home.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class feedCommmentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FeedCommentItems> feedItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_commment);


        final String fid = getIntent().getStringExtra("fid");



        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(feedCommmentActivity.this));

        feedItems = new ArrayList<>();

        getItems();


        ImageView submit = (ImageView) findViewById(R.id.submit);
        SharedPreferences prefs = feedCommmentActivity.this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        final String usn = prefs.getString("usn", "NULL");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment_text = (EditText) findViewById(R.id.comment_text);
                String comment = comment_text.getText().toString();
                comment_text.setText("");
                String url = "http://smvitmapp.xtoinfinity.tech/php/home/add_feed_comment.php?fid=" + fid + "&usn=" + usn + "&comment=" + comment;
                // Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(feedCommmentActivity.this, "Comment added", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(feedCommmentActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

                int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue queue = Volley.newRequestQueue(feedCommmentActivity.this);

                queue.add(stringRequest);
            }

        });
    }

    public void getItems() {
        SharedPreferences prefs = this.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        final String usn = prefs.getString("usn", "NULL");
        final String fid = getIntent().getStringExtra("fid");
        String url = "http://smvitmapp.xtoinfinity.tech/php/home/feed_comment.php?fid=" + fid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);

                        FeedCommentItems items = new FeedCommentItems(
                                jo.getString("fid"),
                                jo.getString("usn"),
                                jo.getString("comment")
                        );

                        feedItems.add(items);
                    }

                    adapter = new CommentAdapter(feedItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // loading.dismiss();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        private List<FeedCommentItems> eventList;
        private Context context;

        public CommentAdapter(List<FeedCommentItems> eventList, Context context) {
            this.eventList = eventList;
            this.context = context;
        }

        @NonNull
        @Override
        public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_comment_layout, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {

            FeedCommentItems items = eventList.get(position);

            holder.usn.setText(items.getUsn());
            holder.feed_comment.setText(items.getComment());


        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView usn, feed_comment;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                usn = (TextView) itemView.findViewById(R.id.usn);
                feed_comment = (TextView) itemView.findViewById(R.id.feed_comment);


            }
        }
    }

    class FeedCommentItems {

        private String fid;
        private String usn;
        private String comment;


        public FeedCommentItems(String fid, String usn, String comment) {
            this.fid = fid;
            this.usn = usn;
            this.comment = comment;
        }

        public String getFid() {
            return fid;
        }

        public String getUsn() {
            return usn;
        }

        public String getComment() {
            return comment;
        }
    }
}
