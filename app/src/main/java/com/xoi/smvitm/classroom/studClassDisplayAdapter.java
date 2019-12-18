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

public class studClassDisplayAdapter extends RecyclerView.Adapter<studClassDisplayAdapter.ViewHolder>{

    public studClassDisplayAdapter(ArrayList<String> code, ArrayList<String> sname, ArrayList<String> ccode, ArrayList<String> fname, ArrayList<String> fphoto, ArrayList<String> fid, Context mContext) {
        this.code = code;
        this.sname = sname;
        this.ccode = ccode;
        this.fname = fname;
        this.fphoto = fphoto;
        this.fid = fid;
        this.mContext = mContext;
    }

    private ArrayList<String> code = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> ccode = new ArrayList<>();
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fphoto = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private Context mContext;



    @NonNull
    @Override
    public studClassDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stud_class_display_layout, viewGroup, false);
        studClassDisplayAdapter.ViewHolder holder = new studClassDisplayAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final studClassDisplayAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.fnameTxt.setText(fname.get(i));
        viewHolder.ccodeTxt.setText(ccode.get(i));
        viewHolder.codeTxt.setText(code.get(i));
        viewHolder.snameTxt.setText(sname.get(i));
        Glide.with(mContext).load(fphoto.get(i)).into(viewHolder.fphotoImg);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, studMainClassroom.class);
                intent.putExtra("code", ccode.get(i));
                intent.putExtra("tempname", fname.get(i));
                intent.putExtra("tempphoto", fphoto.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return code.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fnameTxt, ccodeTxt, snameTxt, codeTxt;
        CircularImageView fphotoImg;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fnameTxt = itemView.findViewById(R.id.fnameTxt);
            ccodeTxt = itemView.findViewById(R.id.ccodeTxt);
            snameTxt = itemView.findViewById(R.id.snameTxt);
            codeTxt = itemView.findViewById(R.id.codeTxt);
            fphotoImg = itemView.findViewById(R.id.fphoto);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}