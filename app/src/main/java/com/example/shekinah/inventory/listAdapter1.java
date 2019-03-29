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
import android.widget.TextView;

public class listAdapter1 extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] NSName;
    public static String sID, sName;
    TextView NSIdT,NSNameT;


    public listAdapter1(Context context, String[] NSIdS, String[] NSNameS) {

        super(context, R.layout.activity_status, NSNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.id = NSIdS;
        this.NSName = NSNameS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list_adapter1, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        NSNameT = (TextView) rowView.findViewById(R.id.NSName);

        try {

            //Assigning values from array to individual layouts in list view
            NSNameT.setText(NSName[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), updateStatus.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                sID = id[position];
                sName = NSName[position];
                Log.d("selected Status " ," Id :" + sID);
                Log.d("selected Status " ," Name :" + sName);
            }
        });
        return rowView;
    }
}
