package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConsumableAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] Name;
    private String[] date, status;
    public static String urID, urStatus;
    public static String urName;
    public static String urDueDate;
    TextView NameT,idT,dateT, StatT;

    public ConsumableAdapter(Context context, String[] idS, String[] NameS, String[] DateS, String[] StatusS) {

        super(context, R.layout.activity_consumable_issues, NameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.Name = NameS;
        this.date = DateS;
        this.status = StatusS;
    }


    public ConsumableAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_consumable_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        NameT = (TextView) rowView.findViewById(R.id.reqUser);
        idT = (TextView) rowView.findViewById(R.id.reqId);
        dateT = (TextView) rowView.findViewById(R.id.reqDate);
        StatT = (TextView) rowView.findViewById(R.id.reqStat);

        try {

            //Assigning values from array to individual layouts in list view
            NameT.setText(Name[position]);
            idT.setText(id[position]);
            dateT.setText(date[position]);
            StatT.setText(status[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReqDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                urID = id[position];
                urName = Name[position];
                urDueDate = date[position];
                urStatus = status[position];

                Log.d("Req" ," Id :" + urID);
                Log.d("Req" ," Name :" + urName);
                Log.d("Req" ," DueDate :" + urDueDate);
                Log.d("Req" ," Status :" + urStatus);

            }
        });

        return rowView;
    }
}
