package com.xoi.smvitm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedItems> feedList;
    private Context context;

    public FeedAdapter(List<FeedItems> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }
    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        FeedItems items = feedList.get(position);

        holder.title.setText(items.getTitle());
        holder.description.setText(items.getDescription());
        holder.imglink.setText(items.getImglink());
        String imgurl=items.getImglink();

        Glide.with(context)
                .load(imgurl)
                .into(holder.imgview);


    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title,description,imglink;
        public ImageView imgview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            imglink = (TextView)itemView.findViewById(R.id.imglink);
            imgview=(ImageView)itemView.findViewById(R.id.imgview);
        }
    }
}
