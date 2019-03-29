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

public class LocationAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] locName;
    private String[] locAddr;
    private String[] locStatus;
    public static String lID;
    public static String lName;
    public static String lAddr;
    public static String lStatus;
    TextView locIdT,locNameT,locAddrT,locStatusT;
    CheckBox LocCheckbox;

    public LocationAdapter(Context context, String[] idS, String[] locNameS, String[] locAddrS, String[] locStatusS) {

        super(context, R.layout.activity_locations, locNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.locName = locNameS;
        this.locAddr = locAddrS;
        this.locStatus = locStatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_location_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        locNameT = (TextView) rowView.findViewById(R.id.locName);
        locAddrT = (TextView) rowView.findViewById(R.id.locAddr);
        locStatusT = (TextView) rowView.findViewById(R.id.locStatus);

        try {

            //Assigning values from array to individual layouts in list view
            locNameT.setText(locName[position]);
            locAddrT.setText(locAddr[position]);
            locStatusT.setText(locStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), updateLoc.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                    lID = id[position];
                    lName = locName[position];
                    lAddr = locAddr[position];
                    lStatus = locStatus[position];

                    Log.d("Location" ," Id :" + lID);
                    Log.d("Location" ," Name :" + lName);
                    Log.d("Location" ," Address :" + lAddr);
                    Log.d("Location" ," Status :" + lStatus);

            }
        });

        return rowView;
    }
}
