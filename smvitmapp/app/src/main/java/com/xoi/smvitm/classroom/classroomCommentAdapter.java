package com.xoi.smvitm.classroom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class classroomCommentAdapter extends RecyclerView.Adapter<classroomCommentAdapter.ViewHolder>{


    public classroomCommentAdapter(ArrayList<String> descAr, ArrayList<String> usnAr, ArrayList<String> nameAr, ArrayList<String> profilepicAr, ArrayList<String> datetimeAr, String fname, String fphoto, Context mContext) {
        this.descAr = descAr;
        this.usnAr = usnAr;
        this.nameAr = nameAr;
        this.profilepicAr = profilepicAr;
        this.datetimeAr = datetimeAr;
        this.fname = fname;
        this.fphoto = fphoto;
        this.mContext = mContext;
    }

    private ArrayList<String> descAr = new ArrayList<>();
    private ArrayList<String> usnAr = new ArrayList<>();
    private ArrayList<String> nameAr = new ArrayList<>();
    private ArrayList<String> profilepicAr = new ArrayList<>();
    private ArrayList<String> datetimeAr = new ArrayList<>();
    String fname, fphoto;
    private Context mContext;



    @NonNull
    @Override
    public classroomCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.classroom_comment_layout, viewGroup, false);
        classroomCommentAdapter.ViewHolder holder = new classroomCommentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final classroomCommentAdapter.ViewHolder viewHolder, final int i) {

        SharedPreferences sp = mContext.getSharedPreferences("com.xoi.smvitm",Context.MODE_PRIVATE);
        String userusn = sp.getString("usn","");
        if(nameAr.get(i).equals("null")){
            viewHolder.nameTxt.setText(fname);
            Glide.with(mContext).load(fphoto).into(viewHolder.photoImg);
        }
        else{
            viewHolder.nameTxt.setText(nameAr.get(i));
            Glide.with(mContext).load(profilepicAr.get(i)).into(viewHolder.photoImg);
        }
        viewHolder.descTxt.setText(descAr.get(i));
        viewHolder.dateTxt.setText(datetimeAr.get(i));
        if(userusn.equals(usnAr.get(i)) || (sp.getString("login","").equals("2") &&usnAr.get(i).equals("null"))){
            viewHolder.deleteBut.setVisibility(View.VISIBLE);
            viewHolder.editBut.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.deleteBut.setVisibility(View.GONE);
            viewHolder.editBut.setVisibility(View.GONE);
        }
        viewHolder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deleting your comment. Please wait.", Toast.LENGTH_SHORT).show();
                deletePost(descAr.get(i),datetimeAr.get(i), viewHolder.parent_layout);
            }
        });

    }

    @Override
    public int getItemCount() {
        return descAr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateTxt, nameTxt, descTxt;
        CircularImageView photoImg;
        ConstraintLayout parent_layout;
        ImageView editBut, deleteBut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            photoImg = itemView.findViewById(R.id.photoImg);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            editBut = itemView.findViewById(R.id.editBut);
            deleteBut = itemView.findViewById(R.id.deleteBut);

        }
    }
     private void deletePost(final String desc, final String datetime, final ConstraintLayout layoutView){
         String deleteUrl = "http://smvitmapp.xtoinfinity.tech/php/classroom/deleteClassroomComment.php";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteUrl,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         ((Activity)mContext).finish();
                         ((Activity)mContext).overridePendingTransition(0, 0);
                         ((Activity)mContext).startActivity(((Activity)mContext).getIntent());
                         ((Activity)mContext).overridePendingTransition(0, 0);
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
