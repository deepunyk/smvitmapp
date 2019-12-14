package com.xoi.smvitm;

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

import java.util.List;

public class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.ViewHolder> {

    private List<CircularItems> circularList;
    private Context context;

    public CircularAdapter(List<CircularItems> circularList, Context context) {
        this.circularList = circularList;
        this.context = context;
    }

    @NonNull
    @Override
    public CircularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circulars_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CircularAdapter.ViewHolder holder, int position) {

        CircularItems items = circularList.get(position);

        holder.circular_title.setText(items.getCircular_title());
        holder.circular_date.setText(items.getCircular_date());
        final String circular_pdflink = items.getCircular_pdflink();

        Glide.with(context)
                .load(R.drawable.college_logo)
                .placeholder(R.drawable.college_logo)
                .into(holder.imgview);


        holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, PDFReader.class);
                    intent.putExtra("pdfurl", circular_pdflink);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return circularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView circular_title, circular_date;
        public ImageView imgview;
        public Button view_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            circular_title = (TextView) itemView.findViewById(R.id.circular_title);
            circular_date = (TextView) itemView.findViewById(R.id.circular_date);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);
            view_button = (Button) itemView.findViewById(R.id.viewpdf_button);


        }
    }
}
