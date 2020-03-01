package com.xoi.smvitm.varnothsava;

import android.content.Context;
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

public class vEventAdapter extends RecyclerView.Adapter<vEventAdapter.ViewHolder> {

    public vEventAdapter(ArrayList<String> date, ArrayList<String> name, ArrayList<String> startTime, ArrayList<String> venue, ArrayList<String> rules, ArrayList<String> type, ArrayList<String> coordinator, ArrayList<String> id, ArrayList<String> photo, ArrayList<String> ruleBook, Context mContext) {
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
    public vEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_layout, viewGroup, false);
        vEventAdapter.ViewHolder holder = new vEventAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final vEventAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.eName.setText(name.get(i).trim());
        viewHolder.eTime.setText(startTime.get(i).trim());
        viewHolder.eLoc.setText(venue.get(i).trim());
        Glide.with(mContext).load(photo.get(i)).into(viewHolder.eventImage);
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eName, eTime, eLoc;
        private ImageView eventImage;
        private ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eName = (TextView) itemView.findViewById(R.id.eventName);
            eTime = (TextView) itemView.findViewById(R.id.eventTime);
            eLoc = (TextView) itemView.findViewById(R.id.eventLoc);
            eventImage = (ImageView) itemView.findViewById(R.id.eventImage);
            parent_layout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}

