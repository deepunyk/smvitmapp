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

import java.util.ArrayList;

public class circularsFragmentRVAdapter extends RecyclerView.Adapter<circularsFragmentRVAdapter.ViewHolder> {

    public circularsFragmentRVAdapter(ArrayList<String> title, ArrayList<String> date, ArrayList<String> pdflink, Context mContext) {
        this.title = title;
        this.pdflink = pdflink;
        this.date = date;
        this.mContext = mContext;
    }

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> pdflink = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();

    private Context mContext;

    @NonNull
    @Override
    public circularsFragmentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.circulars_card, viewGroup, false);
        circularsFragmentRVAdapter.ViewHolder holder = new circularsFragmentRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull circularsFragmentRVAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(title.get(i));
        viewHolder.date.setText(date.get(i));

        Glide.with(mContext)
                .load(R.drawable.college_logo)
                .placeholder(R.drawable.college_logo)
                .into(viewHolder.imgview);

        viewHolder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(mContext, PDFReader.class);
                    intent.putExtra("circular_title", title.get(i));
                    intent.putExtra("circular_date", date.get(i));
                    intent.putExtra("circular_pdflink", pdflink.get(i));
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

        private TextView title, date;
        private ImageView imgview;
        Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.circular_title);
            date = (TextView) itemView.findViewById(R.id.circular_date);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button=(Button)itemView.findViewById(R.id.viewpdf_button);
        }
    }
}