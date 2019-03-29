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
import android.widget.EditText;
import android.widget.TextView;

public class ProdReqAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] PName, Qty;
    public static String PNameS, QtyS;
    TextView PNameT;
    EditText QtyE;

    public ProdReqAdapter(Context context, String[] PNameS, String[] QtyS) {

        super(context, R.layout.activity_prod_req_adapter, PNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;

        this.PName = PNameS;
        this.Qty = QtyS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_prod_req_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        PNameT = (TextView) rowView.findViewById(R.id.prdNameAdapter);
        QtyE = (EditText) rowView.findViewById(R.id.qtyAdapter);

        try {

            //Assigning values from array to individual layouts in list view
            PNameT.setText(PName[position]);
            QtyE.setText(Qty[position]);

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
                QtyS = Qty[position];

                Log.d("Req" ," PNameS :" + PNameS);
                Log.d("Req" ," QtyS :" + QtyS);

            }
        });

        return rowView;
    }
}
