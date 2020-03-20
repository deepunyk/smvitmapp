package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.profile.facProfileActivity;
import com.xoi.smvitm.profile.studProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  feedFragmentRVAdapter extends RecyclerView.Adapter<feedFragmentRVAdapter.ViewHolder> {


    public feedFragmentRVAdapter(ArrayList<String> feedId, ArrayList<String> title, ArrayList<String> description, ArrayList<String> imglink, ArrayList<String> date, ArrayList<String> sId, ArrayList<String> sPic, ArrayList<String> sName, ArrayList<String> fId, ArrayList<String> fPic, ArrayList<String> fName, TextView moreTxt, Context mContext) {
        this.feedId = feedId;
        this.title = title;
        this.description = description;
        this.imglink = imglink;
        this.date = date;
        this.sId = sId;
        this.sPic = sPic;
        this.sName = sName;
        this.fId = fId;
        this.fPic = fPic;
        this.fName = fName;
        this.moreTxt = moreTxt;
        this.mContext = mContext;
    }

    private ArrayList<String> feedId = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> sId = new ArrayList<>();
    private ArrayList<String> sPic = new ArrayList<>();
    private ArrayList<String> sName = new ArrayList<>();
    private ArrayList<String> fId = new ArrayList<>();
    private ArrayList<String> fPic = new ArrayList<>();
    private ArrayList<String> fName = new ArrayList<>();
    private TextView moreTxt;
    private Context mContext;

    @NonNull
    @Override
    public feedFragmentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_card, viewGroup, false);
        feedFragmentRVAdapter.ViewHolder holder = new feedFragmentRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final feedFragmentRVAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.descTxt.setText(description.get(i));
        viewHolder.title.setText(title.get(i));

        if(description.get(i).equals("")){
            viewHolder.descTxt.setVisibility(View.GONE);
        }



        final SharedPreferences sp = mContext.getSharedPreferences("com.xoi.smvitm",Context.MODE_PRIVATE);
        viewHolder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, FeedDetails.class);
                    intent.putExtra("fid", feedId.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(mContext, FeedDetails.class);
                    intent.putExtra("fid", feedId.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.descTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(mContext, FeedDetails.class);
                    intent.putExtra("fid", feedId.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(i == date.size()-1){
            moreTxt.performClick();
        }else{
            moreTxt.setVisibility(View.GONE);
        }

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Handler handler = new Handler();
                    viewHolder.comment.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.comment.setBackgroundColor(mContext.getResources().getColor(R.color.feedComment));
                        }
                    }, 50);
                    Intent intent = new Intent(mContext, feedCommmentActivity.class);
                    intent.putExtra("fid", feedId.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        viewHolder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fId.get(i).equals("null")) {
                    sp.edit().putString("tusn", sId.get(i)).apply();
                    Intent go = new Intent(mContext, studProfileActivity.class);
                    mContext.startActivity(go);
                }
                else{
                    sp.edit().putString("tfid", fId.get(i)).apply();
                    Intent go = new Intent(mContext, facProfileActivity.class);
                    mContext.startActivity(go);
                }
            }
        });


        String aid = sp.getString("usn","");
        if(aid.equals("")){
            aid = sp.getString("fid","");
        }

        if(!aid.equals(sId.get(i)) && !aid.equals(fId.get(i))){
            viewHolder.moreImg.setVisibility(View.GONE);
        }
        viewHolder.moreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,viewHolder.moreImg);
                popupMenu.inflate(R.menu.feedmore);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Intent intent = new Intent(mContext, feedEditActivity.class);
                                intent.putExtra("fid", feedId.get(i));
                                intent.putExtra("title", title.get(i));
                                intent.putExtra("description", description.get(i));
                                intent.putExtra("imgurl", imglink.get(i));
                                mContext.startActivity(intent);
                                return true;
                            case R.id.delete:
                                Toast.makeText(mContext, "Deleting post, please wait.", Toast.LENGTH_SHORT).show();
                                delete(feedId.get(i),i);
                                return true;
                        }
                        return false;
                    }
                });
            }
        });


        if(fId.get(i).equals("null")) {
            viewHolder.usrName.setText(sName.get(i));
            Glide.with(mContext)
                    .load(sPic.get(i))
                    .fitCenter()
                    .placeholder(mContext.getResources().getDrawable(R.drawable.college_logo))
                    .signature(new ObjectKey(sPic.get(i)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.usrImg);
        }else{
            viewHolder.usrName.setText(fName.get(i));
            Glide.with(mContext)
                    .load(fPic.get(i))
                    .fitCenter()
                    .placeholder(mContext.getResources().getDrawable(R.drawable.college_logo))
                    .signature(new ObjectKey(fPic.get(i)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.usrImg);
        }
        viewHolder.dateTxt.setText(date.get(i));

        if(imglink.get(i).equals("")){
            viewHolder.imgview.setVisibility(View.GONE);
        }
        else {
            Glide.with(mContext)
                    .load(imglink.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .optionalCenterInside()
                    .into(viewHolder.imgview);
        }
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description, likes, usrName, dateTxt, descTxt;
        private ImageView imgview;
        private CircularImageView usrImg;
        private ConstraintLayout parent_layout, user_layout, comment;
        private ImageButton moreImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usrName = (TextView) itemView.findViewById(R.id.usrName);
            dateTxt = (TextView) itemView.findViewById(R.id.dateTxt);
            usrImg = (CircularImageView) itemView.findViewById(R.id.usrImg);
            title = (TextView) itemView.findViewById(R.id.titleTxt);
            descTxt = (TextView) itemView.findViewById(R.id.descTxt);
            imgview = (ImageView) itemView.findViewById(R.id.postImg);
            comment = (ConstraintLayout) itemView.findViewById(R.id.comLayout);
            user_layout = (ConstraintLayout) itemView.findViewById(R.id.userLayout);
            parent_layout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
            moreImg = (ImageButton) itemView.findViewById(R.id.moreImg);
        }
    }

    private void delete(final String delId,final int i){
        String url = "http://smvitmapp.xtoinfinity.tech/php/home/feedDelete.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        feedId.remove(i);
                        title.remove(i);
                        description.remove(i);
                        imglink.remove(i);
                        date.remove(i);
                        sPic.remove(i);
                        sName.remove(i);
                        sId.remove(i);
                        fPic.remove(i);
                        fName.remove(i);
                        fId.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, date.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", delId);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    private void edit(){

    }
}