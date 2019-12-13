package com.xoi.smvitm;


<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> 9423487... ganpat
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.Button;
>>>>>>> 9423487... ganpat

public class studaccFragment extends Fragment {


    public studaccFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        return inflater.inflate(R.layout.fragment_studacc, container, false);
=======
        View view = inflater.inflate(R.layout.fragment_studacc, container, false);

        Button btnStore = (Button) view.findViewById(R.id.btnstore);
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),storeActivity.class);
                startActivity(intent);
            }
        });
        return view;
>>>>>>> 9423487... ganpat
    }

}
