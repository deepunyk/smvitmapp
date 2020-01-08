package com.xoi.smvitm.classroom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.xoi.smvitm.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class addElectiveAdapter extends RecyclerView.Adapter<addElectiveAdapter.ViewHolder>{

    public addElectiveAdapter(ArrayList<String> code, ArrayList<String> sname, ArrayList<String> ccode, ArrayList<String> fname, ArrayList<String> fphoto, ArrayList<String> fid, Button addBut, TextView addText, Context mContext) {
        this.code = code;
        this.sname = sname;
        this.ccode = ccode;
        this.fname = fname;
        this.fphoto = fphoto;
        this.fid = fid;
        this.addBut = addBut;
        this.addText = addText;
        this.mContext = mContext;
    }

    private ArrayList<String> code = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> ccode = new ArrayList<>();
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fphoto = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private Context mContext;
    Button addBut;
    TextView addText,subTxt;
    ArrayList<String> select = new ArrayList<String>(2);
    String sub1, sub2;


    @NonNull
    @Override
    public addElectiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_elective_rv_layout, viewGroup, false);
        addElectiveAdapter.ViewHolder holder = new addElectiveAdapter.ViewHolder(view);
        addBut.setVisibility(View.GONE);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final addElectiveAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.fnameTxt.setText(fname.get(i));
        viewHolder.ccodeTxt.setText(ccode.get(i));
        viewHolder.codeTxt.setText(code.get(i));
        viewHolder.snameTxt.setText(sname.get(i));
        Glide.with(mContext).load(fphoto.get(i)).into(viewHolder.fphotoImg);
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select.contains(sname.get(i))){
                    select.remove(sname.get(i));
                }else{
                    if(select.size()==2){
                        Toast.makeText(mContext, "You cannot add more electives", Toast.LENGTH_SHORT).show();
                    }else{
                        select.add(sname.get(i));
                    }
                };
                if(select.size()>0) {
                    String s = "Selected electives are";
                    for(int j = 0; j<select.size();j++){
                        s = s + "\n" +select.get(j);
                    }
                    addText.setText(s);
                }
                else{
                    addText.setText("Select two electives to continue");
                }
                if(select.size()==2){
                    addBut.setVisibility(View.VISIBLE);
                }else{
                    addBut.setVisibility(View.GONE);
                }
            }
        });
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub1 = ccode.get(sname.indexOf(select.get(0)));
                sub2 = ccode.get(sname.indexOf(select.get(1)));

                addText.setText(sub1+" "+sub2);
            }
        });
    }

    @Override
    public int getItemCount() {

        return code.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fnameTxt, ccodeTxt, snameTxt, codeTxt;
        CircularImageView fphotoImg;
        ConstraintLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fnameTxt = itemView.findViewById(R.id.fnameTxt);
            ccodeTxt = itemView.findViewById(R.id.ccodeTxt);
            snameTxt = itemView.findViewById(R.id.snameTxt);
            codeTxt = itemView.findViewById(R.id.codeTxt);
            fphotoImg = itemView.findViewById(R.id.fphoto);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}