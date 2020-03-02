package com.xoi.smvitm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.home.student.feedFragmentRVAdapter;

import java.util.ArrayList;

public class updatesRVAdapter extends RecyclerView.Adapter<updatesRVAdapter.ViewHolder> {

    public updatesRVAdapter(ArrayList<String> title, ArrayList<String> description, Context mContext) {
        this.title = title;
        this.description = description;
        this.mContext = mContext;
    }
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public updatesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.updates_card, viewGroup, false);
        updatesRVAdapter.ViewHolder holder = new updatesRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull updatesRVAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(title.get(i));
        viewHolder.description.setText(description.get(i));

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
