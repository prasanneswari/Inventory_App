package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PositionsAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] PosName;
    private String[] PosAddr;
    private String[] PosStatus;
    public static String poID;
    public static String poName;
    public static String poWH;
    public static String poStatus;
    TextView posIdT,posNameT,posWHT,posStatusT;
    CheckBox posCheckbox;

    public PositionsAdapter(Context context, String[] idS, String[] posNameS, String[] posAddrS, String[] posStatusS) {

        super(context, R.layout.activity_positions, posNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.id = idS;
        this.PosName = posNameS;
        this.PosAddr = posAddrS;
        this.PosStatus = posStatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_positions_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        posNameT = (TextView) rowView.findViewById(R.id.posName);
        posWHT = (TextView) rowView.findViewById(R.id.posAddr);
        posStatusT = (TextView) rowView.findViewById(R.id.posStatus);

        try {
            //Assigning values from array to individual layouts in list view
            posNameT.setText(PosName[position]);
            posWHT.setText(PosAddr[position]);
            posStatusT.setText(PosStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdatePosition.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                poID = id[position];
                poName = PosName[position];
                poWH = PosAddr[position];
                poStatus = PosStatus[position];
                Log.d("Position" ," Id :" + poID);
                Log.d("Position" ," Name :" + poName);
                Log.d("Position" ," Address :" + poWH);
                Log.d("Position" ," Status :" + poStatus);

            }
        });

        return rowView;
    }
}
