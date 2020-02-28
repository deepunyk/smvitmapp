package com.xoi.smvitm.home.student;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class feedFragment extends Fragment {

    private static final String URL_DATA = "http://smvitmapp.xtoinfinity.tech/php/home/feed_info.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FeedItems> feedItems;
    LottieAnimationView loadAnim;
    TextView loadTxt;
    FloatingActionButton fab;
    TextView moreTxt;
    int n = 0;

    public feedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        loadAnim = (LottieAnimationView) view.findViewById(R.id.loadanim);
        loadTxt = (TextView) view.findViewById(R.id.loadtxt);


        loadAnim.setVisibility(View.VISIBLE);
        loadTxt.setVisibility(View.VISIBLE);


        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), feedPostActivity.class);
                startActivity(i);
            }
        });
        feedItems = new ArrayList<>();

        getItems();
        return view;
    }

    public void getItems() {

        // final ProgressDialog loading =  ProgressDialog.show(getActivity(),"Loading","please wait",false,true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // loading.dismiss();
                    loadAnim.setVisibility(View.GONE);
                    loadTxt.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("feed");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);

                        FeedItems items = new FeedItems(
                                jo.getString("feed_id"),
                                jo.getString("name"),
                                jo.getString("description"),
                                jo.getString("imglink"),
                                jo.getString("photographer_name"),
                                jo.getString("blogger_name"),
                                jo.getString("date"),
                                jo.getString("user"),
                                jo.getString("profilepic")
                        );

                        feedItems.add(items);
                    }

                    adapter = new FeedAdapter(feedItems, getActivity().getApplicationContext());
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
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}

class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedItems> feedList;
    private Context context;

    public FeedAdapter(List<FeedItems> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedAdapter.ViewHolder holder, int position) {

        FeedItems items = feedList.get(position);

        holder.title.setText(items.getTitle());
        // holder.description.setText(items.getDescription());
        final String fid = items.getFid();
        final String imgurl = items.getImglink();
        final String title = items.getTitle();
        final String description = items.getDescription();
        final String photographer_name = items.getPhotographer_name();
        final String blogger_name = items.getBlogger_name();
        final String username = items.getUsername();
        final String date = items.getDate();
        final String profilepic = items.getProfilepic();

        Glide.with(context)
                .load(profilepic)
                .placeholder(R.drawable.college_logo)
                .into(holder.usrImg);
        holder.usrName.setText(username);
        holder.dateTxt.setText(date);

        //like status
        SharedPreferences prefs = context.getSharedPreferences("com.xoi.smvitm", MODE_PRIVATE);
        final String usn = prefs.getString("usn", "NULL");
        String url2 = "http://smvitmapp.xtoinfinity.tech/php/home/feed_like_status.php?fid=" + fid + "&usn=" + usn;
        StringRequest stringRequest2 = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String like_status = jo.getString("liked");

                        if (like_status.equals("liked")) {
                            holder.like.setVisibility(View.GONE);
                            holder.likefill.setVisibility(View.VISIBLE);
                        } else {
                            holder.like.setVisibility(View.VISIBLE);
                            holder.likefill.setVisibility(View.GONE);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(stringRequest2);

        holder.likes.setVisibility(View.VISIBLE);

        if(imgurl.equals("")){
            holder.imgview.setVisibility(View.GONE);
        }
        String url1 = "http://smvitmapp.xtoinfinity.tech/php/home/likes_count.php?fid=" + fid;
        StringRequest stringRequest1 = new StringRequest(url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String like_count = jo.getString("likes");
                        holder.likes.setText(like_count + " Likes");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest1);


        Glide.with(context)
                .load(imgurl)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


        holder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, FeedDetails.class);
                    intent.putExtra("fid", fid);
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("photographer_name", photographer_name);
                    intent.putExtra("blogger_name", blogger_name);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, feedCommmentActivity.class);
                    intent.putExtra("fid", fid);
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("photographer_name", photographer_name);
                    intent.putExtra("blogger_name", blogger_name);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.likeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                holder.like.setVisibility(View.GONE);
                holder.likefill.setVisibility(View.VISIBLE);


                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                String url = "http://smvitmapp.xtoinfinity.tech/php/home/feed_likes.php?fid=" + fid + "&usn=" + usn + "&like=liked";
                // Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

                int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue queue = Volley.newRequestQueue(context);

                queue.add(stringRequest);
            }
        });

    }


    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout likeLayout, comment;
        private TextView title, description, likes, usrName, dateTxt;
        private ImageView imgview;
        private ImageButton like, likefill;
        private CircularImageView usrImg;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            usrName = (TextView) itemView.findViewById(R.id.usrName);
            dateTxt = (TextView) itemView.findViewById(R.id.dateTxt);
            usrImg = (CircularImageView) itemView.findViewById(R.id.usrImg);
            title = (TextView) itemView.findViewById(R.id.titleTxt);
            //description = (TextView) itemView.findViewById(R.id.description);
            likes = (TextView) itemView.findViewById(R.id.likeNum);
            imgview = (ImageView) itemView.findViewById(R.id.postImg);
            like = (ImageButton) itemView.findViewById(R.id.like);
            comment = (LinearLayout) itemView.findViewById(R.id.commentLayout);
            likefill = (ImageButton) itemView.findViewById(R.id.likefill);
            likeLayout = (LinearLayout)itemView.findViewById(R.id.likeLayout);

        }
    }
}

class FeedItems {
    private String fid;
    private String title;
    private String description;
    private String imglink;
    private String photographer_name;
    private String blogger_name;
    private String username;
    private String date;
    private String profilepic;


    public FeedItems(String fid, String title, String description, String imglink, String photographer_name, String blogger_name, String date, String username, String profilepic) {
        this.fid = fid;
        this.title = title;
        this.description = description;
        this.imglink = imglink;
        this.photographer_name = photographer_name;
        this.blogger_name = blogger_name;
        this.username = username;
        this.date = date;
        this.profilepic = profilepic;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImglink() {
        return imglink;
    }

    public String getPhotographer_name() {
        return photographer_name;
    }

    public String getBlogger_name() {
        return blogger_name;
    }

    public String getFid() {
        return fid;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getProfilepic() {
        return profilepic;
    }
}

