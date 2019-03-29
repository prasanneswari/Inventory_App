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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.PosPrdSearchAdapter.pID;

public class PQtyDetailsAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] pid;
    private String[] pos_, posid, qtyid;
    private String[] prd;
    private String[] Qty,Status, s={"Active","Inactive"};
    public static String posS, QtyS, StatusS;
    TextView posT,qtyT, sT, posTA,qtyTA, sTA;
    CheckBox LocCheckbox;
    com.android.volley.RequestQueue sch_RequestQueue;

    public PQtyDetailsAdapter(Context context, String[] pidS, String[] posS, String[] QtyS, String[] StatusS, String[] posidS, String[] qtyidS) {

        super(context, R.layout.activity_pqty_details_adapter, pidS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        //productidA, positionA, quantityA, positionstatusA
        this.context = context;
        this.pid = pidS;
        this.pos_ = posS;
        this.Qty = QtyS;
        this.Status = StatusS;
        this.posid = posidS;
        this.qtyid = qtyidS;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_pqty_details_adapter, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        posT = (TextView) rowView.findViewById(R.id.pos);
        qtyT = (TextView) rowView.findViewById(R.id.qty);
        sT = (TextView) rowView.findViewById(R.id.Status);

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, s);

        final Spinner sp = new Spinner(context);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        try {
            //Assigning values from array to individual layouts in list view

            posT.setText(pos_[position]);
            qtyT.setText(Qty[position]);
            sT.setText(Status[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                posS = pos_[position];
                QtyS = Qty[position];
                StatusS = Status[position];
                Log.d("Product" ," posS :" + posS);
                Log.d("Product" ," QtyS :" + QtyS);
                Log.d("Product" ," StatusS :" + StatusS);

                View alertLayout = inflater.inflate(R.layout.position_quantity_popup, null);
                posTA = (TextView) alertLayout.findViewById(R.id.POS);
                qtyTA = (TextView) alertLayout.findViewById(R.id.QTY);
                sTA = (TextView) alertLayout.findViewById(R.id.Stat);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("PRODUCT DETAILS");
                posTA.setText(posS);
                qtyTA.setText(QtyS);
                sTA.setText(StatusS);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);

                try {
                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String jsonS = "{\"quantityid\":\"" + qtyid[position] + "\",\"quantity\":\""+qtyTA.getText().toString()+"\",\"productid\":\""+pid[position]+"\",\"positionid\":\"" + posid[position] + "\",\"locationid\":\"1\",\"warehouseid\":\"1\"}";
                            //Log.d("-jsnresponse add---",""+jsonS);
                            String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_productquantity/";


                            JSONObject rmdt = null;
                            try {
                                rmdt = new JSONObject(jsonS);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("JSON Parser", "Error parsing data " + e.toString());

                            }

                            Volley_Change(urlrs, rmdt);

                            //Log.d("-jsnresponse enter---", "" + jsonS);
                            Toast.makeText(context, "Save clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();

            }

        });

        return rowView;

    }
    public void Volley_Change(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("---------", "---"+response.toString());
                        //dialog_progress.hide();
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, PositionQuantityDetails.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intent);

                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));
                Toast.makeText(context, "connection error ", Toast.LENGTH_LONG).show();

            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        // Adding request to request queue
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
//        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(context);

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
