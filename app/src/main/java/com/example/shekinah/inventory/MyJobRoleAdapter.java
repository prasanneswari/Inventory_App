package com.example.shekinah.inventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyJobRoleAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] NSName,NSForecast,NSStoreforecast;
    public static String sID, sName,sForecast,sStoreforecast;
    TextView NSIdT,NSNameT;
    String menu;
    Button roles,forecastB,storeforecastB;

    public MyJobRoleAdapter(Context context, String[] NSNameS,String[] NSForecastS,String[] NSStoreforecastS) {

        super(context, R.layout.activity_job_roles_menu, NSNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.NSName = NSNameS;
        this.NSForecast = NSForecastS;
        this.NSStoreforecast = NSStoreforecastS;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_my_job_role_adapter, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        //NSNameT = (TextView) rowView.findViewById(R.id.NSName);
        roles = (Button) rowView.findViewById(R.id.jobRole);
        forecastB = (Button) rowView.findViewById(R.id.forecast);
        storeforecastB = (Button) rowView.findViewById(R.id.storeforecast);


        try {

            //Assigning values from array to individual layouts in list view
            //NSNameT.setText(NSName[position]);
            roles.setText(NSName[position]);
            forecastB.setText(NSForecast[position]);
            storeforecastB.setText(NSStoreforecast[position]);


            Log.d("role " ," Name :" + NSName[position]);
            Log.d("NSForecast " ," Name :" + NSForecast[position]);
            Log.d("NSStoreforecast " ," Name :" + NSStoreforecast[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("selected Status " ," Name :" + sName);
            }
        });*/


        forecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), User_Forecast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        storeforecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Store_Forecast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        roles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (NSName[position].contentEquals("production")){
                    Intent intent = new Intent(getContext(), ProductionHomePg.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("requisition")){
                    Intent intent = new Intent(getContext(), UserMain.class); //UserMain
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("issue")){
                    Intent intent = new Intent(getContext(), ConsumableIssues.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("locations")){
                    Intent intent = new Intent(getContext(), Locations.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("positions")){
                    Intent intent = new Intent(getContext(), Positions.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("products")){
                    Intent intent = new Intent(getContext(), Products.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("warehouse")){
                    Intent intent = new Intent(getContext(), Warehouses.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("purchasable")){
                    Intent intent = new Intent(getContext(), josh.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("Purchase")){
                    Intent intent = new Intent(getContext(), purchase_order.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("admin")){
                    Intent intent = new Intent(getContext(), Admin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
