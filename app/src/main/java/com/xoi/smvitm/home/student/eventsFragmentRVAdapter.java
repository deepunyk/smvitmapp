package com.xoi.smvitm.home.student;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import java.util.ArrayList;

public class eventsFragmentRVAdapter extends RecyclerView.Adapter<eventsFragmentRVAdapter.ViewHolder>  {
    public eventsFragmentRVAdapter(ArrayList<String> title, ArrayList<String> description, ArrayList<String> imglink, ArrayList<String> pdflink, ArrayList<String> date, ArrayList<String> organizer,Context mContext) {
        this.title = title;
        this.description = description;
        this.imglink = imglink;
        this.pdflink = pdflink;
        this.date = date;
        this.organizer = organizer;

        this.mContext = mContext;
    }
    private ArrayList<String> organizer = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> imglink = new ArrayList<>();
    private ArrayList<String> pdflink = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();

    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events_card, viewGroup, false);
        eventsFragmentRVAdapter.ViewHolder holder = new eventsFragmentRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(title.get(i));
        viewHolder.description.setText(description.get(i));
        Glide.with(mContext).load(imglink.get(i)).into(viewHolder.imgview);
        if(imglink.get(i).equals("")){
            viewHolder.imgview.setVisibility(View.GONE);
        }
        else {
            Glide.with(mContext).load(imglink.get(i)).into(viewHolder.imgview);
        }

        viewHolder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(mContext, EventDetails.class);
                    intent.putExtra("event_title", title.get(i));
                    intent.putExtra("event_description", description.get(i));
                    intent.putExtra("event_imgurl", imglink.get(i));
                    intent.putExtra("event_date", date.get(i));
                    intent.putExtra("event_organizers", organizer.get(i));
                    intent.putExtra("event_pdflink", pdflink.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    mContext.startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private ImageView imgview;
        Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_title);
            description = (TextView) itemView.findViewById(R.id.event_description);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button=(Button)itemView.findViewById(R.id.view_button);
        }
    }
}
