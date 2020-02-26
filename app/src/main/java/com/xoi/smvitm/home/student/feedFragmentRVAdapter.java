package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;
import com.xoi.smvitm.classroom.allotClassActivity;
import com.xoi.smvitm.classroom.allotFacClassFragment;
import com.xoi.smvitm.classroom.allotSelectSemRVAdapter;


import java.util.ArrayList;

public class feedFragmentRVAdapter extends RecyclerView.Adapter<feedFragmentRVAdapter.ViewHolder> {


    public feedFragmentRVAdapter(ArrayList<String> title, ArrayList<String> description, ArrayList<String> imglink, ArrayList<String> photographer_name, ArrayList<String> blogger_name, ArrayList<String> username, ArrayList<String> date, ArrayList<String> profilepic, ArrayList<String> id, TextView moreTxt, Context mContext) {
        this.title = title;
        this.description = description;
        this.imglink = imglink;
        this.photographer_name = photographer_name;
        this.blogger_name = blogger_name;
        this.username = username;
        this.date = date;
        this.profilepic = profilepic;
        this.id = id;
        this.moreTxt = moreTxt;
        this.mContext = mContext;
    }

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> photographer_name = new ArrayList<>();
    private ArrayList<String> blogger_name = new ArrayList<>();
    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> profilepic = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
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
        viewHolder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, FeedDetails.class);
                    intent.putExtra("fid", id.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    intent.putExtra("photographer_name", photographer_name.get(i));
                    intent.putExtra("blogger_name", blogger_name.get(i));
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
            moreTxt.setVisibility(View.VISIBLE);
        }else{
            moreTxt.setVisibility(View.GONE);
        }

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(mContext, feedCommmentActivity.class);
                    intent.putExtra("fid", id.get(i));
                    intent.putExtra("title", title.get(i));
                    intent.putExtra("description", description.get(i));
                    intent.putExtra("imgurl", imglink.get(i));
                    intent.putExtra("photographer_name", photographer_name.get(i));
                    intent.putExtra("blogger_name", blogger_name.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewHolder.title.setText(title.get(i));
        viewHolder.usrName.setText(username.get(i));
        viewHolder.dateTxt.setText(date.get(i));
        Glide.with(mContext).load(profilepic.get(i)).into(viewHolder.usrImg);
        if(imglink.get(i).equals("")){
            viewHolder.imgview.setVisibility(View.GONE);
        }
        else {
            Glide.with(mContext).load(imglink.get(i)).into(viewHolder.imgview);
        }
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout likeLayout, comment;
        private TextView title, description, likes, usrName, dateTxt;
        private ImageView imgview;
        private ImageButton like, likefill;
        private CircularImageView usrImg;
        private ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usrName = (TextView) itemView.findViewById(R.id.usrName);
            dateTxt = (TextView) itemView.findViewById(R.id.dateTxt);
            usrImg = (CircularImageView) itemView.findViewById(R.id.usrImg);
            title = (TextView) itemView.findViewById(R.id.titleTxt);
            likes = (TextView) itemView.findViewById(R.id.likeNum);
            imgview = (ImageView) itemView.findViewById(R.id.postImg);
            like = (ImageButton) itemView.findViewById(R.id.like);
            comment = (LinearLayout) itemView.findViewById(R.id.commentLayout);
            likefill = (ImageButton) itemView.findViewById(R.id.likefill);
            likeLayout = (LinearLayout)itemView.findViewById(R.id.likeLayout);
        }
    }
}