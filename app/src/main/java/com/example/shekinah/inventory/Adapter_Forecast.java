package com.example.shekinah.inventory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter_Forecast extends ArrayAdapter<String> {

    private Context context;
    private String[] RId, qtyR, qtyI, qtyStatus, PName;
    public static String PNameS, QtyS;
    TextView PNameT, QtyRT, qtyIT,statusT;


    public Adapter_Forecast(Context context, String[] qtyRS, String[] qtyIS, String[] qtyStatusS, String[] PNameS) {

        super(context, R.layout.activity_forecasting, PNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;

        //this.RId = rIdS;
        this.qtyR = qtyRS;
        this.qtyI = qtyIS;
        this.qtyStatus = qtyStatusS;
        this.PName = PNameS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__forecast, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        PNameT = (TextView) rowView.findViewById(R.id.prdNameAdapter);
        QtyRT = (TextView) rowView.findViewById(R.id.qtyAdapter);
        qtyIT = (TextView) rowView.findViewById(R.id.qtyIssued);
        statusT = (TextView) rowView.findViewById(R.id.pStatus);

        try {


            //Assigning values from array to individual layouts in list view

            PNameT.setText(PName[position]);
            QtyRT.setText(qtyR[position]);
            qtyIT.setText(qtyI[position]);
            statusT.setText(qtyStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), AddUserReq.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                PNameS = PName[position];

                Log.d("Req" ," PNameS :" + PNameS);

            }
        });

        return rowView;
    }
}
