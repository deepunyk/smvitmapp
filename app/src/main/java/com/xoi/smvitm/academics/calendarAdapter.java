package com.xoi.smvitm.academics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.ViewHolder> {
    String url = "http://smvitmapp.xtoinfinity.tech/php/deletecalendarevent.php?month=";
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> day = new ArrayList<String>();
//    private ArrayList<String> type = new ArrayList<String>();
    private Context mContext;
    private String dtitle,ddate;
    SharedPreferences sharedPreferences;

    public calendarAdapter(ArrayList<String> title, ArrayList<String> date, ArrayList<String> day, Context mContext) {
        this.title = title;
        this.date = date;
        this.day = day;
        //this.type = type;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_layout_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_txt.setText(title.get(i));
        viewHolder.day_txt.setText(day.get(i));
        viewHolder.date_txt.setText(date.get(i));

//        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(mContext, viewHolder.parent_layout);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.main_menu);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.call:
//                                //handle menu1 click
//                                break;
//                            case R.id.sms:
//                                //handle menu2 click
//                                break;
//
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//            }
//        });
        viewHolder.parent_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int i=viewHolder.getAdapterPosition();
                dtitle=title.get(i);
                ddate=date.get(i);
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, viewHolder.parent_layout);
                //inflating menu from xml resource
                //popup.inflate(R.menu.main_menu);
                //adding click listener
                /*popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:

//                                sharedPreferences = mContext.getSharedPreferences("com.example.iamdone",MODE_PRIVATE);
//                                sharedPreferences.edit().putString("dtitle",dtitle).apply();
//                                sharedPreferences.edit().putString("ddate",ddate).apply();
                                Intent myIntent = new Intent(mContext,editCalendarEvent.class);
                                myIntent.putExtra("dtitle1",dtitle);
                                myIntent.putExtra("ddate1",ddate);
                                mContext.startActivity(myIntent);
                                break;
                            case R.id.delete:

                                url = "http://smvitmapp.xtoinfinity.tech/php/deletecalendarevent.php?title="+dtitle+"&date="+ddate;
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("Do you want to delete ?");
                                builder.setTitle("Alert !");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which)
                                            {
                                                deleteEvent();
                                            }
                                        });
                                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog,
                                                                        int which)
                                                    {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;

                        }
                        return false;
                    }
                });*/
                //displaying the popup
                popup.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_txt, date_txt, day_txt;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name_txt = (TextView)itemView.findViewById(R.id.name);
            this.date_txt = (TextView)itemView.findViewById(R.id.date_txt);
            this.day_txt = (TextView)itemView.findViewById(R.id.day_txt);
            parent_layout = (ConstraintLayout)itemView.findViewById(R.id.parent_layout);
        }
    }
    public void deleteEvent()
    {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Success")){
                            Intent myIntent1 = new Intent(mContext,MainActivitycal.class);
                            mContext.startActivity(myIntent1);

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
