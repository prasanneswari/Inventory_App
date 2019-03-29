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

public class WareHouseAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] whId;
    private String[] whName;
    private String[] whLocId;
    private String[] whStatus;
    public static String whID;
    public static String whNam;
    public static String whL;
    public static String whS;

    TextView WHIdT,WHNameT,WHLocIdT,WHStatusT;
    CheckBox WHCheckbox;

    public WareHouseAdapter(Context context, String[] WHIdS, String[] WHNameS, String[] WHLocIdS, String[] WHStatusS) {

        super(context, R.layout.activity_warehouses, WHIdS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.whId = WHIdS;
        this.whName = WHNameS;
        this.whLocId = WHLocIdS;
        this.whStatus = WHStatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_ware_house_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml

        WHNameT = (TextView) rowView.findViewById(R.id.WHName);
        WHLocIdT = (TextView) rowView.findViewById(R.id.WHLocId);
        WHStatusT = (TextView) rowView.findViewById(R.id.WHStatus);

        try {

            //Assigning values from array to individual layouts in list view

            WHNameT.setText(whName[position]);
            WHLocIdT.setText(whLocId[position]);
            WHStatusT.setText(whStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), updateWarehouse.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                    whID = whId[position];
                    whNam = whName[position];
                    whL = whLocId[position];
                    whS = whStatus[position];
                    Log.d("checked Warehouse " ," Id :" + whID);
                    Log.d("checked Warehouse " ," Name :" + whNam);
                    Log.d("checked Warehouse " ," Loc :" + whL);
                    Log.d("checked Warehouse " ," Status :" + whS);

            }
        });
        return rowView;
    }
}

