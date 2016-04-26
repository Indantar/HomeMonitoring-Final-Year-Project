package com.example.Project;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by g00284823 on 26/04/2016.
 */
public class fragmentViewGraph extends Fragment {

    private activity1 act1;
    private Context con = null;
    private Button quitBtn;
    private DatabaseHelper dbh;
    private ArrayList<Double> storeData;
    private Fragment fg = fragmentViewGraph.this;

    public static fragmentViewGraph newInstance() {
        fragmentViewGraph frag = new fragmentViewGraph();
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout relaLay = (RelativeLayout) inflater.inflate(R.layout.showgraph,null);
        dbh = new DatabaseHelper(act1);
        LineChart graph = (LineChart) relaLay.findViewById(R.id.graphView);
        storeData = dbh.returnTemp();
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for(int i=0;i < storeData.size();i++){
            entries.add(new Entry(storeData.get(i).floatValue(),i));
            labels.add("label" + i);
        }
        LineDataSet dataS = new LineDataSet(entries,"Temperature");
        dataS.setColor(Color.RED);
        dataS.setDrawFilled(true);
        LineData d = new LineData(labels,dataS);
        graph.setData(d);
        graph.setDescription("Temperature Data");
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