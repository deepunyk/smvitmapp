package com.xoi.smvitm.classroom;

import android.content.Context;
import android.content.Intent;
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

public class studMainClassAdapter extends RecyclerView.Adapter<studMainClassAdapter.ViewHolder>{

    private ArrayList<String> arccode = new ArrayList<>();
    private ArrayList<String> cfid = new ArrayList<>();
    private ArrayList<String> link = new ArrayList<>();
    private ArrayList<String> arusn = new ArrayList<>();
    private ArrayList<String> datetime = new ArrayList<>();

    public studMainClassAdapter(ArrayList<String> arccode, ArrayList<String> cfid, ArrayList<String> link, ArrayList<String> arusn, ArrayList<String> profpic, ArrayList<String> name, ArrayList<String> desc, ArrayList<String> datetime, String tname, String tphoto, Context mContext) {
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
        viewHolder.descTxt.setText(desc.get(i));
        viewHolder.dateTxt.setText(datetime.get(i));

        viewHolder.commentTxt.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTxt, dateTxt, descTxt, commentTxt;
        CircularImageView photoImg;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            commentTxt = itemView.findViewById(R.id.commentTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            photoImg = itemView.findViewById(R.id.photoImg);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}