package com.xoi.smvitm.varnothsava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

import java.util.ArrayList;

public class vSpotAdapter extends RecyclerView.Adapter<vSpotAdapter.ViewHolder> {

    public vSpotAdapter(ArrayList<String> date, ArrayList<String> name, ArrayList<String> startTime, ArrayList<String> venue, ArrayList<String> rules, ArrayList<String> type, ArrayList<String> coordinator, ArrayList<String> id, ArrayList<String> photo, ArrayList<String> ruleBook, Context mContext) {
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.venue = venue;
        this.rules = rules;
        this.type = type;
        this.coordinator = coordinator;
        this.id = id;
        this.photo = photo;
        this.ruleBook = ruleBook;
        this.mContext = mContext;
    }

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> startTime = new ArrayList<>();
    private ArrayList<String> venue = new ArrayList<>();
    private ArrayList<String> rules = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> coordinator = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> photo = new ArrayList<>();
    private ArrayList<String> ruleBook = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public vSpotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spotlight_layout, viewGroup, false);
        vSpotAdapter.ViewHolder holder = new vSpotAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final vSpotAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.eTxt.setText(name.get(i).trim());
        //viewHolder.eDate.setText(date.get(i).trim());
        Glide.with(mContext).load(photo.get(i)).into(viewHolder.eImg);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext,vEventDetailActivity.class);
                in.putExtra("date", date.get(i));
                in.putExtra("name", name.get(i));
                in.putExtra("startTime", startTime.get(i));
                in.putExtra("venue", venue.get(i));
                in.putExtra("rules", rules.get(i));
                in.putExtra("type", type.get(i));
                in.putExtra("coordinator", coordinator.get(i));
                in.putExtra("id", id.get(i));
                in.putExtra("photo", photo.get(i));
                in.putExtra("rulebook", ruleBook.get(i));
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eTxt, eDate;
        private ImageView eImg;
        private ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eTxt = (TextView) itemView.findViewById(R.id.eTxt);
            eDate = (TextView) itemView.findViewById(R.id.eDate);
            eImg = (ImageView) itemView.findViewById(R.id.eImg);
            parent_layout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}


