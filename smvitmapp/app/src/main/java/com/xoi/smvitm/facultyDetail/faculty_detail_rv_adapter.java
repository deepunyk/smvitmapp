package com.xoi.smvitm.facultyDetail;

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

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;

public class faculty_detail_rv_adapter extends RecyclerView.Adapter<faculty_detail_rv_adapter.ViewHolder> {

    public faculty_detail_rv_adapter(ArrayList<String> name, ArrayList<String> fid, ArrayList<String> mobile, ArrayList<String> email,ArrayList<String> profImg, Context mContext) {
        this.name = name;
        this.fid = fid;
        this.mobile = mobile;
        this.email = email;
        this.profImg = profImg;
        this.context = mContext;
    }

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> mobile = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> profImg = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public faculty_detail_rv_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faculty_detail_layout, viewGroup, false);
        faculty_detail_rv_adapter.ViewHolder holder = new faculty_detail_rv_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final faculty_detail_rv_adapter.ViewHolder viewHolder, final int i) {
        viewHolder.nameTxt.setText(name.get(i));
        viewHolder.mobTxt.setText(mobile.get(i));
        viewHolder.emailTxt.setText(email.get(i));
        Glide.with(context).load(profImg.get(i)).into(viewHolder.profileImg);
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, mobTxt, emailTxt;
        CircularImageView profileImg;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            mobTxt = itemView.findViewById(R.id.mobTxt);
            emailTxt = itemView.findViewById(R.id.emailTxt);
            profileImg = itemView.findViewById(R.id.profileImg);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
