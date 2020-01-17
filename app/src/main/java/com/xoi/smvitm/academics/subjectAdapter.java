package com.xoi.smvitm.academics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xoi.smvitm.R;

import java.util.ArrayList;

public class subjectAdapter extends RecyclerView.Adapter<subjectAdapter.ViewHolder> {
    private ArrayList<String> subname = new ArrayList<String>();
    private ArrayList<String> subcode = new ArrayList<String>();
    //    private ArrayList<String> type = new ArrayList<String>();
    private Context mContext;
    private String dtitle,ddate;
    SharedPreferences sharedPreferences;

    public subjectAdapter(ArrayList<String> subname, ArrayList<String> subcode, Context mContext) {
        this.subname = subname;
        this.subcode = subcode;
        //this.type = type;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_module_list, viewGroup, false);
      ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_txt.setText(subname.get(i));
        viewHolder.code_txt.setText(subcode.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scode=subcode.get(i);
                String sname=subname.get(i);
                Intent myIntent = new Intent(mContext,study_meterialsActivity.class);
                myIntent.putExtra("subname",sname);
                myIntent.putExtra("subcode",scode);
                mContext.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subcode.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_txt, code_txt;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name_txt = (TextView)itemView.findViewById(R.id.tvsubjectname);
            this.code_txt = (TextView)itemView.findViewById(R.id.tvsubcode);
            parent_layout = (ConstraintLayout)itemView.findViewById(R.id.parent_layout1);
        }
    }
}

