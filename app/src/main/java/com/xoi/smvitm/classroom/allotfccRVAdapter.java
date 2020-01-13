package com.xoi.smvitm.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xoi.smvitm.R;

import java.util.ArrayList;

public class allotfccRVAdapter extends RecyclerView.Adapter<allotfccRVAdapter.ViewHolder> {

    public allotfccRVAdapter(ArrayList<String> fname, ArrayList<String> fid, ArrayList<String> sname, ArrayList<String> sid, Button doneBut, Button editBut, String selectSec, String selectBr, String selectSem, Context mContext) {
        this.fname = fname;
        this.fid = fid;
        this.sname = sname;
        this.sid = sid;
        this.doneBut = doneBut;
        this.editBut = editBut;
        this.selectSem = selectSem;
        this.selectSec = selectSec;
        this.selectBr = selectBr;
        this.mContext = mContext;

    }

    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> sid = new ArrayList<>();
    private Context mContext;
    Button doneBut, editBut;
    int subNum = 0;
    int selectNum = -1;
    String selectSem, selectSec, selectBr;
    ArrayList<String> selectFname = new ArrayList<>();
    private ArrayList<String> selectSname = new ArrayList<>();
    private ArrayList<String> selectFid = new ArrayList<>();
    private ArrayList<String> selectSid = new ArrayList<>();

    @NonNull
    @Override
    public allotfccRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allot_fcc_layout, viewGroup, false);
        allotfccRVAdapter.ViewHolder holder = new allotfccRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final allotfccRVAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.fnameTxt.setText(fname.get(i));
        viewHolder.snameTxt.setText(sname.get(i));
    }

    @Override
    public int getItemCount() {
        return fname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fnameTxt,snameTxt;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fnameTxt = itemView.findViewById(R.id.fnameTxt);
            snameTxt = itemView.findViewById(R.id.snameTxt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}