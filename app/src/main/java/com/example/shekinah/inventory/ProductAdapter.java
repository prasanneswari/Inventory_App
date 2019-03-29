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

public class ProductAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] prdName;
    private String[] prdDesc;
    private String[] prdStatus;
    public static String pID;
    public static String pName;
    public static String pDesc;
    public static String pStatus;
    TextView prdIdT,prdNameT,prdDescT,prdStatusT;
    CheckBox LocCheckbox;

    public ProductAdapter(Context context, String[] idS, String[] prdNameS, String[] prdDescS, String[] prdStatusS) {

        super(context, R.layout.activity_products, prdNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.prdName = prdNameS;
        this.prdDesc = prdDescS;
        this.prdStatus = prdStatusS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_product_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        prdNameT = (TextView) rowView.findViewById(R.id.pName);
        prdDescT = (TextView) rowView.findViewById(R.id.pDesc);
        prdStatusT = (TextView) rowView.findViewById(R.id.pStatus);

        try {
            //Assigning values from array to individual layouts in list view
            prdNameT.setText(prdName[position]);
            prdDescT.setText(prdDesc[position]);
            prdStatusT.setText(prdStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProdUpdate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                pID = id[position];
                pName = prdName[position];
                pDesc = prdDesc[position];
                pStatus = prdStatus[position];
                Log.d("Product" ," Id :" + pID);
                Log.d("Product" ," Name :" + pName);
                Log.d("Product" ," Description :" + pDesc);
                Log.d("Product" ," Status :" + pStatus);

            }
        });
        return rowView;
    }
}
