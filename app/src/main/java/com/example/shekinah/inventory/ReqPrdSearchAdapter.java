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

public class ReqPrdSearchAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] NPName;
    public static String prID, prName;
    TextView NPNameT;

    public ReqPrdSearchAdapter(Context context, String[] NPIdS, String[] NPNameS) {

        super(context, R.layout.activity_add_user_req, NPNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.id = NPIdS;
        this.NPName = NPNameS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_req_prd_search_adapter, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        NPNameT = (TextView) rowView.findViewById(R.id.NPName);

        try {

            //Assigning values from array to individual layouts in list view
            NPNameT.setText(NPName[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}
