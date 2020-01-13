package com.xoi.smvitm.classroom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;

public class allotSelectSemRVAdapter extends RecyclerView.Adapter<allotSelectSemRVAdapter.ViewHolder> {

    public allotSelectSemRVAdapter(ArrayList<String> sem, ArrayList<String> sec, Button nextBut, Context mContext) {
        this.sem = sem;
        this.sec = sec;
        this.nextBut = nextBut;
        this.mContext = mContext;
    }

    private ArrayList<String> sem = new ArrayList<>();
    private ArrayList<String> sec = new ArrayList<>();
    private Context mContext;
    private Button nextBut;
    private int selectNum = -1;

    @NonNull
    @Override
    public allotSelectSemRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allot_selectsem_layout, viewGroup, false);
        allotSelectSemRVAdapter.ViewHolder holder = new allotSelectSemRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final allotSelectSemRVAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.semTxt.setText(sem.get(i));
        viewHolder.secTxt.setText(sec.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBut.setVisibility(View.VISIBLE);
                selectNum = i;
                notifyDataSetChanged();
            }
        });
        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((allotClassActivity)mContext).selectSec = sec.get(selectNum);
                ((allotClassActivity)mContext).selectSem = sem.get(selectNum);
                ((allotClassActivity)mContext).loadFragment(new allotFacClassFragment());
            }
        });
        if(selectNum==i){
            viewHolder.parent_layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
        }
        else
        {
            viewHolder.parent_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return sem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView semTxt, secTxt;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semTxt = itemView.findViewById(R.id.semTxt);
            secTxt = itemView.findViewById(R.id.sectionTxt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}


