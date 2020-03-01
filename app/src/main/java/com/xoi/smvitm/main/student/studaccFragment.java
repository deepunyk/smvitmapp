package com.xoi.smvitm.main.student;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.xoi.smvitm.R;
import com.xoi.smvitm.academics.MainActivitycal;
import com.xoi.smvitm.academics.subject_selectActivity;
import com.xoi.smvitm.facultyDetail.faculty_details_activity;
import com.xoi.smvitm.timetable.timeMain;
import com.xoi.smvitm.updatesActivity;
import com.xoi.smvitm.varnothsava.vMainActivity;

public class studaccFragment extends Fragment {


    public studaccFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_studacc, container, false);

        final LinearLayout brLayout = (LinearLayout)view.findViewById(R.id.timeLayout);
        final LinearLayout smLayout = (LinearLayout)view.findViewById(R.id.smLayout);
        final LinearLayout fLayout = (LinearLayout)view.findViewById(R.id.fLayout);
        final LinearLayout acLayout = (LinearLayout)view.findViewById(R.id.acLayout);
        final LinearLayout upLayout = (LinearLayout)view.findViewById(R.id.upLayout);
        final LinearLayout vLayout = (LinearLayout)view.findViewById(R.id.vLayout);

        fLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                fLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));

                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), faculty_details_activity.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });

        brLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                brLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        brLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));
                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        brLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), timeMain.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });

        acLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                acLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        acLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));
                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        acLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), MainActivitycal.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });

        smLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                smLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));
                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), subject_selectActivity.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });

        upLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                upLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));
                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), updatesActivity.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });

        vLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                vLayout.setBackgroundColor(getResources().getColor(R.color.white));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vLayout.setBackgroundColor(getResources().getColor(R.color.feedComment));
                    }
                }, 50);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        Intent i = new Intent(getActivity(), vMainActivity.class);
                        startActivity(i);
                    }
                }, 100);

            }
        });
        return view;

    }

}
