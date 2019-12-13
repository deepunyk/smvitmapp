package com.xoi.smvitm;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.ViewHolder>{


    private List<CircularItems> list;
    private Context context;

    public CircularAdapter(List<CircularItems> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CircularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circulars_card,parent,false);

        return new CircularAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CircularAdapter.ViewHolder holder, int position) {

        CircularItems items = list.get(position);

        holder.circular_title.setText(items.getCircular_title());
        holder.circular_date.setText(items.getCircular_date());
        holder.circular_pdflink.setText(items.getCircular_pdflink());
        final String pdfurl = items.getCircular_pdflink();

        holder.viewpdf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    holder.circular_pdflink.setText("clicked");
                    Intent intent = new Intent(context, PDFReader.class);
                    intent.putExtra("pdfurl", pdfurl);
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView circular_title,circular_date,circular_pdflink;
        Button viewpdf_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circular_title = (TextView)itemView.findViewById(R.id.circular_title);
            circular_date = (TextView)itemView.findViewById(R.id.circular_date);
            circular_pdflink = (TextView)itemView.findViewById(R.id.circular_pdflink);
            viewpdf_button=(Button)itemView.findViewById(R.id.viewpdf_button);

        }
    }
}
