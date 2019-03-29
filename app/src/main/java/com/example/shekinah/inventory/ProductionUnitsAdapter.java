package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Shekinah.. on 17/03/2018.
 */

public class ProductionUnitsAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] NSName;
    public static String uID, uName;
    TextView NSIdT,NSNameT;
    String menu;
    Button Units;

    public ProductionUnitsAdapter(Context context, String[] idS, String[] NSNameS) {

        super(context, R.layout.activity_job_roles_menu, NSNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.id = idS;
        this.NSName = NSNameS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_my_job_role_adapter, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        //NSNameT = (TextView) rowView.findViewById(R.id.NSName);
        Units = (Button) rowView.findViewById(R.id.jobRole);

        try {

            //Assigning values from array to individual layouts in list view
            //NSNameT.setText(NSName[position]);
            Units.setText(NSName[position]);

            Log.d("Unit " ," Name :" + NSName[position]);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("selected Status " ," Name :" + sName);
            }
        });*/

        Units.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                uID = id[position];
                uName = NSName[position];
                Log.d("Unit " ," id :" + id[position]);
                Log.d("Unit " ," Name :" + NSName[position]);
                Intent intent = new Intent(getContext(), AddProductionProduct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
