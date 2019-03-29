package com.example.shekinah.inventory;

/**
 * Created by Shekinah.. on 06/03/2018.
 */

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

public class Adapter_purchase_Order extends ArrayAdapter<String> {

    private Context context;
    private String[] purid;
    private String[] purReqdate;
    private String[] purDuedate;
    private String[] purStatus;
    public static String lID;
    public static String lReqdate;
    public static String lDuedate;
    public static String lStatus;
    TextView Purchase_idT,Req_dateT,Due_dateT,StatusT;

    public Adapter_purchase_Order(Context context, String[] puridS, String[] purReqdateS, String[] purDuedateS, String[] purStatusS) {

        super(context, R.layout.activity_purchase_order, puridS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.purid = puridS;
        this.purReqdate = purReqdateS;
        this.purDuedate = purDuedateS;
        this.purStatus = purStatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_purchase__order, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        Purchase_idT = (TextView) rowView.findViewById(R.id.purchase_id);
        Req_dateT = (TextView) rowView.findViewById(R.id.req_date_id);
        Due_dateT = (TextView) rowView.findViewById(R.id.due_date_id);
        StatusT = (TextView) rowView.findViewById(R.id.status_id);

        try {

            //Assigning values from array to individual layouts in list view
            Purchase_idT.setText(purid[position]);
            Req_dateT.setText(purReqdate[position]);
            Due_dateT.setText(purDuedate[position]);
            StatusT.setText(purStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Purchase_products.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                lID = purid[position];
                lReqdate = purReqdate[position];
                lDuedate = purDuedate[position];
                lStatus = purStatus[position];

                Log.d("Location" ," Id1111 :" + lID);
                Log.d("Location" ," Reqdate2222 :" + lReqdate);
                Log.d("Location" ," Duedate33333 :" + lDuedate);
                Log.d("Location" ," Status44444 :" + lStatus);

            }
        });

        return rowView;
    }
}