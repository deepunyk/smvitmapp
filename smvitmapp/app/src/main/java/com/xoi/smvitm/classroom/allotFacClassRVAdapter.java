package com.xoi.smvitm.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xoi.smvitm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class allotFacClassRVAdapter extends RecyclerView.Adapter<allotFacClassRVAdapter.ViewHolder> {

    public allotFacClassRVAdapter(ArrayList<String> fname,ArrayList<String> fid,ArrayList<String> sname,ArrayList<String> sid,TextView snameTxt, TextView sidTxt, Button allotBut, Button remBut, Button skipBut,String selectSec,String selectBr,String selectSem, Context mContext) {
        this.fname = fname;
        this.fid = fid;
        this.sname = sname;
        this.sid = sid;
        this.snameTxt = snameTxt;
        this.sidTxt = sidTxt;
        this.allotBut = allotBut;
        this.remBut = remBut;
        this.skipBut = skipBut;
        this.selectSem = selectSem;
        this.selectSec = selectSec;
        this.selectBr = selectBr;
        this.mContext = mContext;
        snameTxt.setText(sname.get(0));
        sidTxt.setText(sid.get(0));
    }

    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> sid = new ArrayList<>();
    private Context mContext;
    Button allotBut,remBut, skipBut;
    TextView snameTxt, sidTxt;
    int subNum = 0;
    int selectNum = -1;
    String selectSem, selectSec, selectBr;
    ArrayList<String> selectFname = new ArrayList<>();
    private ArrayList<String> selectSname = new ArrayList<>();
    private ArrayList<String> selectFid = new ArrayList<>();
    private ArrayList<String> selectSid = new ArrayList<>();

    @NonNull
    @Override
    public allotFacClassRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allot_facclass_layout, viewGroup, false);
        allotFacClassRVAdapter.ViewHolder holder = new allotFacClassRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final allotFacClassRVAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.fnameTxt.setText(fname.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allotBut.setVisibility(View.VISIBLE);
                viewHolder.parent_layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.fnameTxt.setTextColor(mContext.getResources().getColor(R.color.white));
                selectNum = i;
                notifyDataSetChanged();
            }
        });
        if(selectNum==i){
            viewHolder.parent_layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.fnameTxt.setTextColor(mContext.getResources().getColor(R.color.white));        }
        else
        {
            viewHolder.parent_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            viewHolder.fnameTxt.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        skipBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allotBut.setVisibility(View.GONE);
                subNum++;
                selectNum=-1;
                if(subNum<sid.size()) {
                    snameTxt.setText(sname.get(subNum));
                    sidTxt.setText(sid.get(subNum));

                }
                else{
                    ((allotClassActivity)mContext).loadFragment(new allotFacClassConfirmFragment(selectSid,selectSname,selectFid,selectFname,selectSec,selectBr,selectSem));
                }
                notifyDataSetChanged();
            }
        });

        remBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSubject(sid.get(subNum),selectBr);
                allotBut.setVisibility(View.GONE);
                subNum++;
                selectNum=-1;
                if(subNum<sid.size()) {
                    snameTxt.setText(sname.get(subNum));
                    sidTxt.setText(sid.get(subNum));

                }
                else{
                    ((allotClassActivity)mContext).loadFragment(new allotFacClassConfirmFragment(selectSid,selectSname,selectFid,selectFname,selectSec,selectBr,selectSem));
                }
                notifyDataSetChanged();
            }
        });


        allotBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subNum<sid.size()) {
                    allotBut.setVisibility(View.GONE);
                    selectSid.add(sid.get(subNum));
                    selectSname.add(sname.get(subNum));
                    selectFid.add(fid.get(selectNum));
                    selectFname.add(fname.get(selectNum));
                    subNum++;
                    selectNum=-1;
                    if(subNum<sid.size()) {
                        snameTxt.setText(sname.get(subNum));
                        sidTxt.setText(sid.get(subNum));

                    }
                    else{
                        ((allotClassActivity)mContext).loadFragment(new allotFacClassConfirmFragment(selectSid,selectSname,selectFid,selectFname,selectSec,selectBr,selectSem));
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fnameTxt;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fnameTxt = itemView.findViewById(R.id.fnameTxt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }

    private void deleteSubject(String subcode, String branch){
        String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/removeSubject.php?code=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+""+subcode+"&br="+branch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("no")){
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext, "Subject Removed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
