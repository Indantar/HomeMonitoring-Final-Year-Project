package com.example.Project;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by g00284823 on 25/04/2016.
 */
public class fragmentViewData extends Fragment
{

    private activity1 act1;
    private Context con = null;
    private Button quitBtn;
    private DatabaseHelper dbh;
    private ArrayList<String> storeData;
    private Fragment fg = fragmentViewData.this;

    public static fragmentViewData newInstance() {
        fragmentViewData frag = new fragmentViewData();
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        RelativeLayout relaLay = (RelativeLayout) inflater.inflate(R.layout.fragment,null);
        ListView dataList = (ListView) relaLay.findViewById(R.id.listView);
        dbh = new DatabaseHelper(act1);
        storeData = dbh.returnData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(con,android.R.layout.simple_list_item_1,storeData);
        dataList.setAdapter(dataAdapter);
        quitBtn = (Button) relaLay.findViewById(R.id.closeBtn);
        quitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().getFragmentManager().beginTransaction().remove(fg).commit();
            }
        });
        return relaLay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try {
            con = getActivity();
            act1 = (activity1) getActivity();

        } catch (IllegalStateException e)
        {
            throw new IllegalStateException("ILLEGAL STATE EXCEPTION");
        }
    }
}