package com.xoi.smvitm.main.student;



import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import com.xoi.smvitm.R;
import com.xoi.smvitm.academics.MainActivitycal;
import com.xoi.smvitm.academics.subject_selectActivity;
import com.xoi.smvitm.store.storeActivity;

public class studaccFragment extends Fragment {


    public studaccFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_studacc, container, false);

        Button btnStore = (Button) view.findViewById(R.id.btnstore);
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), storeActivity.class);
                startActivity(intent);
            }
        });

        ConstraintLayout smLayout, acLayout;
        smLayout = (ConstraintLayout)view.findViewById(R.id.smLayout);
        acLayout = (ConstraintLayout)view.findViewById(R.id.acLayout);

        smLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), subject_selectActivity.class);
                startActivity(intent);
            }
        });

        acLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivitycal.class);
                startActivity(intent);
            }
        });

        return view;

    }

}
