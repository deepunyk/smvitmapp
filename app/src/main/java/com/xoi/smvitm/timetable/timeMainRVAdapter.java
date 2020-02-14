package com.xoi.smvitm.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xoi.smvitm.R;
import com.xoi.smvitm.classroom.allotfccRVAdapter;

import java.util.ArrayList;

public class timeMainRVAdapter extends RecyclerView.Adapter<timeMainRVAdapter.ViewHolder> {


    public timeMainRVAdapter(ArrayList<String> timeArr, ArrayList<String> subArr, Context mContext) {
        this.timeArr = timeArr;
        this.subArr = subArr;
        this.mContext = mContext;
    }

    private ArrayList<String> timeArr = new ArrayList<>();
    private ArrayList<String> subArr = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public timeMainRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_main_layout, viewGroup, false);
        timeMainRVAdapter.ViewHolder holder = new timeMainRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final timeMainRVAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.timeTxt.setText(timeArr.get(i));
        viewHolder.subTxt.setText(subArr.get(i));
    }

    @Override
    public int getItemCount() {
        return timeArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeTxt,subTxt;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            subTxt = itemView.findViewById(R.id.subTxt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}