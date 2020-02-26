package com.xoi.smvitm.main.student;



import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xoi.smvitm.R;
import com.xoi.smvitm.academics.MainActivitycal;
import com.xoi.smvitm.academics.study_meterialsActivity;
import com.xoi.smvitm.academics.subject_selectActivity;
import com.xoi.smvitm.facultyDetail.faculty_details_activity;
import com.xoi.smvitm.store.storeActivity;
import com.xoi.smvitm.timetable.timeMain;

public class studaccFragment extends Fragment {


    public studaccFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_studacc, container, false);

        LinearLayout brLayout = (LinearLayout)view.findViewById(R.id.timeLayout);
        LinearLayout smLayout = (LinearLayout)view.findViewById(R.id.smLayout);
        LinearLayout fLayout = (LinearLayout)view.findViewById(R.id.fLayout);
        LinearLayout acLayout = (LinearLayout)view.findViewById(R.id.acLayout);

        fLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), faculty_details_activity.class);
                startActivity(i);
            }
        });

        brLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), timeMain.class);
                startActivity(i);
            }
        });

        acLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivitycal.class);
                startActivity(i);
            }
        });

        smLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), subject_selectActivity.class);
                startActivity(i);
            }
        });
        return view;

    }

}
