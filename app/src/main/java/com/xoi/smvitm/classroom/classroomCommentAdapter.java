package com.xoi.smvitm.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;

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

    }

    @Override
    public int getItemCount() {
        return descAr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateTxt, nameTxt, descTxt;
        CircularImageView photoImg;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            photoImg = itemView.findViewById(R.id.photoImg);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
