package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

public class UserMainAdapter extends ArrayAdapter<String> {

        private Context context;
        private String[] id;private String[] Name;
        private String[] date, dateI, status ;
        public static String urID, urCat, urDueDate, reqDt, reqStat;
        TextView NameT,idT,dateT,StatusT;

    public UserMainAdapter(Context context, String[] idS, String[] NameS, String[] DateS, String[] DateIS, String[] statusS) {

        super(context, R.layout.activity_user_main, NameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.Name = NameS;
        this.date = DateS;
        this.dateI = DateIS;
        this.status = statusS;
        }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_user_main_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        NameT = (TextView) rowView.findViewById(R.id.reqUser);
        idT = (TextView) rowView.findViewById(R.id.reqId);
        dateT = (TextView) rowView.findViewById(R.id.reqDate);
        StatusT = (TextView) rowView.findViewById(R.id.stat);
        TableRow Row = (TableRow)rowView.findViewById(R.id.row);

        try {
        //Assigning values from array to individual layouts in list view
            NameT.setText(Name[position]);
            idT.setText(id[position]);
            dateT.setText(date[position]);
            StatusT.setText(status[position]);
            if(status[position]=="Not Issued"){ //6 = not issued
                Row.setBackgroundColor(Color.RED);
            }
        } catch (NullPointerException e) {
        e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(getContext(), MyRequestDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        urID = id[position];
        urCat = Name[position];
        urDueDate = date[position];
        reqDt = dateI[position];
        reqStat = status[position];

        Log.d("Req" ," Id :" + urID);
        Log.d("Req" ," Name :" + urCat);
        Log.d("Req" ," DueDate :" + urDueDate);
        }
        });
        return rowView;
    }
}
