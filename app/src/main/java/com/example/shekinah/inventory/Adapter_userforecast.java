package com.example.shekinah.inventory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter_userforecast extends ArrayAdapter<String> {

    private Context context;
    private String[] Forecastid;
    private String[] Name;
    private String[] Qtygiven;
    private String[] Qtyrequired;
    private String[] Reqdate;
    private String[] DueDate;
    private String[] Status;
    public static String lforecastid;
    public static String lname;
    public static String lduedate;
    public static String lstatus;
    public static String lqtygiven;
    public static String lqtyreq;
    public static String lreqdate;

    TextView forecastT,nameT,due_dateT,qtygivenT,qtyrequiredT,reqdateT,statusT;

    public Adapter_userforecast(Context context, String[] forecastS, String[] nameS, String[] duedateS,String[] qtygivenS,String[] qtyrequiredS,String[] reqdateS, String[] statusS) {

        super(context, R.layout.activity_user__forecast, forecastS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.Forecastid = forecastS;
        this.Name = nameS;
        this.DueDate = duedateS;
        this.Qtygiven = qtygivenS;
        this.Qtyrequired = qtyrequiredS;
        this.Reqdate = reqdateS;
        this.Status = statusS;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_userforecast, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        forecastT = (TextView) rowView.findViewById(R.id.forecastid);
        nameT = (TextView) rowView.findViewById(R.id.nameid);
        due_dateT = (TextView) rowView.findViewById(R.id.duedateid);
        statusT = (TextView) rowView.findViewById(R.id.statusid);
        qtygivenT= (TextView) rowView.findViewById(R.id.qtygivenid);
        qtyrequiredT= (TextView) rowView.findViewById(R.id.qtyrequiredid);
        reqdateT = (TextView) rowView.findViewById(R.id.reqdateid);

        try {

            //Assigning values from array to individual layouts in list view
            forecastT.setText(Forecastid[position]);
            nameT.setText(Name[position]);
            due_dateT.setText(DueDate[position]);
            qtygivenT.setText(Qtygiven[position]);
            qtyrequiredT.setText(Qtyrequired[position]);
            reqdateT.setText(Reqdate[position]);
            statusT.setText(Status[position]);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), Purchase_products.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);*/
                lforecastid = Forecastid[position];
                lname = Name[position];
                lduedate = DueDate[position];
                lqtygiven = Qtygiven[position];
                lqtyreq = Qtyrequired[position];
                lreqdate = Reqdate[position];
                lstatus = Status[position];


                Log.d("Location" ," lforecastid1111 :" + lforecastid);
                Log.d("Location" ," lname2222 :" + lname);
                Log.d("Location" ," lduedate33333 :" + lduedate);
                Log.d("Location" ," lstatus44444 :" + lstatus);
                Log.d("Location" ," llqtygiven44444 :" + lqtygiven);
                Log.d("Location" ," lreqdate44444 :" + lreqdate);
                Log.d("Location" ," lqtyreq44444 :" + lqtyreq);

            }
        });

        return rowView;
    }
}
