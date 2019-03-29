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

public class Adapter_Purchase_products extends ArrayAdapter<String> {

    private Context context;
    private String[] pur_detailsid;
    private String[] prd_id;
    private String[] prd_name;
    private String[] prd_desc;
    private String[] prd_company;
    private String[] prd_quantity;

    public static String lID;
    public static String lReqdate;
    public static String lDuedate;
    public static String lStatus;
    TextView pur_details_idT,prd_idT,prd_nameT,prd_descT,prd_companyT,prd_qtyT,prd_receviedqtyT;
    public Adapter_Purchase_products(Context context,String[] pur_detaisS, String[] prd_idS, String[] prd_nameS, String[] prd_descS, String[] prd_companyS, String[] prd_quantityS) {

        super(context, R.layout.activity_purchase_products, prd_idS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.pur_detailsid = pur_detaisS;
        this.prd_id = prd_idS;
        this.prd_name = prd_nameS;
        this.prd_desc = prd_descS;
        this.prd_company = prd_companyS;
        this.prd_quantity = prd_quantityS;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__purchase_products, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml

        pur_details_idT = (TextView) rowView.findViewById(R.id.purdetailsid);
        prd_idT = (TextView) rowView.findViewById(R.id.prdid);
        prd_nameT = (TextView) rowView.findViewById(R.id.prdname);
        prd_descT = (TextView) rowView.findViewById(R.id.prddesc);
        prd_companyT = (TextView) rowView.findViewById(R.id.prdcmpny);
        prd_qtyT = (TextView) rowView.findViewById(R.id.prdqty);

        try {

            //Assigning values from array to individual layouts in list view

            pur_details_idT.setText(pur_detailsid[position]);
            prd_idT.setText(prd_id[position]);
            prd_nameT.setText(prd_name[position]);
            prd_descT.setText(prd_desc[position]);
            prd_companyT.setText(prd_company[position]);
            prd_qtyT.setText(prd_quantity[position]);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), Purchase_products.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//                lID = purid[position];
//                lReqdate = purReqdate[position];
//                lDuedate = purDuedate[position];
//                lStatus = purStatus[position];
//
//                Log.d("Location" ," Id :" + lID);
//                Log.d("Location" ," Reqdate :" + lReqdate);
//                Log.d("Location" ," Duedate :" + lDuedate);
//                Log.d("Location" ," Status :" + lStatus);
//
//            }
//        });

        return rowView;
    }
}
