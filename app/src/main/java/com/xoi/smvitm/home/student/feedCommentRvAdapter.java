package com.xoi.smvitm.home.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;

public class feedCommentRvAdapter extends RecyclerView.Adapter<feedCommentRvAdapter.ViewHolder> {

    public feedCommentRvAdapter(ArrayList<String> fid, ArrayList<String> usn, ArrayList<String> comment, Context mContext) {
        this.fid = fid;
        this.usn = usn;
        this.comment = comment;
        this.mContext = mContext;
    }

    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> usn = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public feedCommentRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_comment_layout, viewGroup, false);
        feedCommentRvAdapter.ViewHolder holder = new feedCommentRvAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final feedCommentRvAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.usnTxt.setText(usn.get(i));
        viewHolder.commentTxt.setText(comment.get(i));
    }

    @Override
    public int getItemCount() {
        return fid.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentTxt, usnTxt;
        private CircularImageView photoImg;
        private ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usnTxt = (TextView) itemView.findViewById(R.id.usn);
            commentTxt = (TextView) itemView.findViewById(R.id.feed_comment);
            photoImg = (CircularImageView) itemView.findViewById(R.id.photoImg);
            parent_layout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
