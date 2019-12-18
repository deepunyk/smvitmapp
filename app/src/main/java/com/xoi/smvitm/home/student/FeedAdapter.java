package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

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
                .inflate(R.layout.feed_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        FeedItems items = feedList.get(position);

        holder.title.setText(items.getTitle());
        holder.description.setText(items.getDescription());
        final String imgurl = items.getImglink();
        final String title = items.getTitle();
        final String description = items.getDescription();
        final String photographer_name = items.getPhotographer_name();
        final String blogger_name = items.getBlogger_name();

        Glide.with(context)
                .load(imgurl)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


       holder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(context, FeedDetails.class);
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("photographer_name", photographer_name);
                    intent.putExtra("blogger_name", blogger_name);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description;
        public ImageView imgview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);



        }
    }
}
