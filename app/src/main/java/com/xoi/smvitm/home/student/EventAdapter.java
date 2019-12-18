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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventItems> eventList;
    private Context context;

    public EventAdapter(List<EventItems> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {

        EventItems items = eventList.get(position);

        holder.event_title.setText(items.getEvent_title());
        holder.event_description.setText(items.getEvent_description());
        final String event_title = items.getEvent_title();
        final String imgurl = items.getEvent_imglink();
        final String event_description = items.getEvent_description();
        final String event_organizers = items.getEvent_organizers();
        final String event_date = items.getEvent_date();
        final String event_pdflink = items.getEvent_pdflink();

        Glide.with(context)
                .load(imgurl)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


       holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(context, EventDetails.class);
                    intent.putExtra("event_title", event_title);
                    intent.putExtra("event_description", event_description);
                    intent.putExtra("event_imgurl", imgurl);
                    intent.putExtra("event_date", event_date);
                    intent.putExtra("event_organizers", event_organizers);
                    intent.putExtra("event_pdflink", event_pdflink);
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
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView event_title, event_description;
        public ImageView imgview;
        public Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            event_title = (TextView) itemView.findViewById(R.id.event_title);
            event_description = (TextView) itemView.findViewById(R.id.event_description);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button = (Button) itemView.findViewById(R.id.view_button);


        }
    }
}
