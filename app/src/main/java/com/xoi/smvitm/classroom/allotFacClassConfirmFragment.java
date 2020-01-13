package com.xoi.smvitm.classroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xoi.smvitm.R;

import java.util.ArrayList;

public class allotFacClassConfirmFragment extends Fragment {
    public allotFacClassConfirmFragment(ArrayList<String> sid,ArrayList<String> sname,ArrayList<String> fid,ArrayList<String> fname,String selectSec,String selectBr,String selectSem) {
        this.fname = fname;
        this.fid = fid;
        this.sname = sname;
        this.sid = sid;
        this.selectSem = selectSem;
        this.selectSec = selectSec;
        this.selectBr = selectBr;
    }

    private String url = "http://smvitmapp.xtoinfinity.tech/php/classroom/showFaculties.php?br=";
    private ArrayList<String> fname = new ArrayList<>();
    private ArrayList<String> fid = new ArrayList<>();
    private ArrayList<String> sname = new ArrayList<>();
    private ArrayList<String> sid = new ArrayList<>();
    private View view;
    String selectSem, selectSec, selectBr;
    RecyclerView recyclerView;
    allotfccRVAdapter adapter;
    Button doneBut, editBut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.allot_facclass_fragment, container, false);
        doneBut = (Button)view.findViewById(R.id.doneBut);
        editBut = (Button)view.findViewById(R.id.editBut);
        initRecyclerView();
        return view;
    }
    private void initRecyclerView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new allotfccRVAdapter(fname,fid,sname,sid,doneBut,editBut,selectSec,selectBr,selectSem,getActivity());
        recyclerView.setAdapter(adapter);
    }
}