package com.xoi.smvitm.classroom;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class studMainClassAdapter extends RecyclerView.Adapter<studMainClassAdapter.ViewHolder>{

    private ArrayList<String> arccode = new ArrayList<>();
    private ArrayList<String> cfid = new ArrayList<>();
    private ArrayList<String> link = new ArrayList<>();
    private ArrayList<String> arusn = new ArrayList<>();
    private ArrayList<String> datetime = new ArrayList<>();
    LottieAnimationView deleteAnim;

    public studMainClassAdapter(ArrayList<String> arccode, ArrayList<String> cfid, ArrayList<String> link, ArrayList<String> arusn, ArrayList<String> profpic, ArrayList<String> name, ArrayList<String> desc, ArrayList<String> datetime, String tname, String tphoto, Context mContext, LottieAnimationView deleteAnim) {
        this.arccode = arccode;
        this.cfid = cfid;
        this.link = link;
        this.arusn = arusn;
        this.profpic = profpic;
        this.name = name;
        this.desc = desc;
        this.datetime = datetime;
        this.tname = tname;
        this.tphoto = tphoto;
        this.mContext = mContext;
        this.deleteAnim = deleteAnim;
    }

    private ArrayList<String> profpic = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    String tname;
    String tphoto;
    private Context mContext;



    @NonNull
    @Override
    public studMainClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stud_main_class_layout, viewGroup, false);
        studMainClassAdapter.ViewHolder holder = new studMainClassAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final studMainClassAdapter.ViewHolder viewHolder, final int i) {

        if(name.get(i).equals("null")){
            viewHolder.nameTxt.setText(tname);
            Glide.with(mContext).load(tphoto).into(viewHolder.photoImg);
        }
        else{
            viewHolder.nameTxt.setText(name.get(i));
            Glide.with(mContext).load(profpic.get(i)).into(viewHolder.photoImg);
        }

        viewHolder.descTxt.setText(desc.get(i));
        viewHolder.dateTxt.setText(datetime.get(i));

        SharedPreferences sp = mContext.getSharedPreferences("com.xoi.smvitm",Context.MODE_PRIVATE);
        String userusn;
        if(sp.getString("login","").equals("1")) {
            userusn = sp.getString("usn", "");
        }
        else{
            userusn = sp.getString("fid", "");
        }
        if(userusn.equals(arusn.get(i)) || (sp.getString("login","").equals("2") &&arusn.get(i).equals("null"))){
            viewHolder.deleteBut.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.deleteBut.setVisibility(View.GONE);
        }

        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, classroomCommmentActivity.class);
                if(name.get(i).equals("null")){
                    intent.putExtra("name", tname);
                    intent.putExtra("photo", tphoto);
                }
                else{
                    intent.putExtra("name", name.get(i));
                    intent.putExtra("photo", profpic.get(i));
                }
                intent.putExtra("desc", desc.get(i));
                intent.putExtra("datetime", datetime.get(i));
                intent.putExtra("cfid", cfid.get(i));
                intent.putExtra("ccode", arccode.get(i));
                intent.putExtra("fname", tname);
                intent.putExtra("fphoto", tphoto);
                mContext.startActivity(intent);
            }
        });
        viewHolder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost(desc.get(i),datetime.get(i),cfid.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datetime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTxt, dateTxt, descTxt, commentTxt;
        CircularImageView photoImg;
        ConstraintLayout parent_layout;
        ImageView deleteBut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            commentTxt = itemView.findViewById(R.id.commentTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            photoImg = itemView.findViewById(R.id.photoImg);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            deleteBut = itemView.findViewById(R.id.deleteBut);

        }

    }

    private void deletePost(final String desc, final String datetime,final String cfid){
        String deleteUrl = "http://smvitmapp.xtoinfinity.tech/php/classroom/deleteClassroomFeed.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ((Activity) mContext).finish();
                        ((Activity) mContext).overridePendingTransition(0, 0);
                        ((Activity) mContext).startActivity(((Activity) mContext).getIntent());
                        ((Activity) mContext).overridePendingTransition(0, 0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("desc",desc);
                params.put("datetime",datetime);
                params.put("cfid",cfid);
                return params;
            };

        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }



}