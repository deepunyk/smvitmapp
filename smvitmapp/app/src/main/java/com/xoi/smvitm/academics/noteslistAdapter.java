package com.xoi.smvitm.academics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xoi.smvitm.R;
import com.xoi.smvitm.home.student.PDFReader;

import java.util.ArrayList;

public class noteslistAdapter extends RecyclerView.Adapter<noteslistAdapter.ViewHolder> {
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> author = new ArrayList<String>();
    private ArrayList<String> pdflink = new ArrayList<String>();

    private Context mContext;
    private String dtitle,ddate;
    SharedPreferences sharedPreferences;
    public noteslistAdapter(ArrayList<String> title, ArrayList<String> author,ArrayList<String> pdflink, Context mContext) {
        this.title = title;
        this.author = author;
        this.pdflink=pdflink;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_select_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_txt.setText(title.get(i));
        String nauthor=" - "+author.get(i);
        viewHolder.author_txt.setText(nauthor);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "" + pdflink.get(i), Toast.LENGTH_SHORT).show();
                String pdfurl=pdflink.get(i);
                Intent myIntent = new Intent(mContext, PDFReader.class);
                myIntent.putExtra("pdfurl",pdfurl);
                myIntent.putExtra("toolString","Study Materials");
                mContext.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_txt, author_txt;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name_txt = (TextView)itemView.findViewById(R.id.tvnotestitle);
            this.author_txt = (TextView)itemView.findViewById(R.id.tvauthor);
            parent_layout = (ConstraintLayout)itemView.findViewById(R.id.parent_layout2);
        }
    }
}