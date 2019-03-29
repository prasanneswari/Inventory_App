package com.example.shekinah.inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.shekinah.inventory.ConsumableAdapter.urID;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class SearchPrdAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] prdName;
    private String[] prdDesc;
    private String[] prdStatus, producttype, reorderqty, reorderlevel, standardcost;
    public static String pID;
    public static String pName;
    public static String pDesc;
    public static String pStatus, pstandardcost, preorderlevel, preorderqty, pproducttype;
    TextView prdIdT,prdNameT,prdDescT,prdStatusT;
    CheckBox LocCheckbox;

    public SearchPrdAdapter(Context context, String[] idS, String[] prdNameS, String[] prdDescS, String[] prdStatusS, String[] standardcostS, String[] reorderlevelS, String[] reorderqtyS, String[] producttypeS) {

        super(context, R.layout.activity_select_product, prdNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.id = idS;
        this.prdName = prdNameS;
        this.prdDesc = prdDescS;
        this.prdStatus = prdStatusS;
        this.standardcost = standardcostS;
        this.reorderlevel = reorderlevelS;
        this.reorderqty = reorderqtyS;
        this.producttype = producttypeS;
    }

    Boolean selected = false;
    TableRow Row;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_search_prd_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        prdNameT = (TextView) rowView.findViewById(R.id.pName);
        prdDescT = (TextView) rowView.findViewById(R.id.pDesc);
        prdStatusT = (TextView) rowView.findViewById(R.id.pStatus);
        Row = (TableRow)rowView.findViewById(R.id.row);

        try {
            //Assigning values from array to individual layouts in list view
            prdNameT.setText(prdName[position]);
            prdDescT.setText(prdDesc[position]);
            prdStatusT.setText(prdStatus[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        pstandardcost = standardcost[position];
        preorderlevel = reorderlevel[position];
        preorderqty = reorderqty[position];
        pproducttype = producttype[position];

        /*prdNameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Info");
                builder.setMessage("Standard cost = "+ pstandardcost);
                builder.setMessage("Reorder level = "+ preorderlevel);
                builder.setMessage("Reorder Quantity = "+ preorderqty);
                builder.setMessage("Product type = "+ pproducttype);

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
*/

//        if(status[position]=="Not Issued"){ //6 = not issued
//            Row.setBackgroundColor(Color.RED);
//        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Row.setBackgroundColor(Color.BLUE);
                    pID = id[position];
                    pName = prdName[position];
                    pDesc = prdDesc[position];
                    pStatus = prdStatus[position];

                    Log.d("Product" ," Id :" + pID);
                    Log.d("Product" ," Name :" + pName);
                    Log.d("Product" ," Description :" + pDesc);
                    Log.d("Product" ," Status :" + pStatus);

                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Info");
                builder.setMessage("Selected Product = "+ pName);

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(context instanceof SelectProduct){
                            ((SelectProduct)context).add();
                            Intent intent = new Intent(getContext(), AddUserReq.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                        else {
                            ((PosPrdSearch)context).add();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return rowView;
    }
}
