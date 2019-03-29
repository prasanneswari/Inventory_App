package com.example.shekinah.inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProductPositionAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] pos, posId;
    private String[] prd;
    private String[] Qty,Status, s={"Active","Inactive"};
    public static String pID, pStatus;
    public static String pName;
    public static String pPos;
    public static String pQty;
    TextView prdIdT,posIdT,prdPT,pQtyT, tx;
    CheckBox LocCheckbox;

    public ProductPositionAdapter(Context context, String[] idS, String[]posIdS, String[] posS, String[] prdS, String[] QtyS, String[] StatusS) {

        super(context, R.layout.activity_product_position_pg, prdS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.posId = posIdS;
        this.pos = posS;
        this.prd = prdS;
        this.Qty = QtyS;
        this.Status = StatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_product_position_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        prdIdT = (TextView) rowView.findViewById(R.id.prdId);
        posIdT = (TextView) rowView.findViewById(R.id.posId);
        prdPT = (TextView) rowView.findViewById(R.id.prdP);
        pQtyT = (TextView) rowView.findViewById(R.id.pQty);

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, s);

        final Spinner sp = new Spinner(context);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        try {
            //Assigning values from array to individual layouts in list view
            prdIdT.setText(posId[position]);
            posIdT.setText(pos[position]);
            prdPT.setText(prd[position]);
            pQtyT.setText(Qty[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pID = posId[position];
                pPos = pos[position];
                pName = prd[position];
                pQty = Qty[position];
                pStatus = Status[position];
                Log.d("Product" ," Id :" + pID);
                Log.d("Product" ," pPos :" + pPos);
                Log.d("Product" ," pName :" + pName);
                Log.d("Product" ," pQty :" + pQty);

                Intent intent = new Intent(getContext(),PositionQuantityDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });
        return rowView;
    }
}
