package com.example.shekinah.inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

/**
 * Created by Shekinah.. on 17/03/2018.
 */

public class ProductionComponentsViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] materialId, pId, qty, PName;
    public static String PNameS, QtyS;
    TextView componentqtyT, componentnameT;
    com.android.volley.RequestQueue sch_RequestQueue;

    public ProductionComponentsViewAdapter(Context context, String[] rawMaterialIdS, String[] pIdS, String[] qtyS, String[] PNameS) {

        super(context, R.layout.activity_add_user_req, PNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.materialId = rawMaterialIdS;
        this.pId = pIdS;
        this.qty = qtyS;
        this.PName = PNameS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.production_components_view, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);

        //Assigning IDs from xml
        componentqtyT = (TextView) rowView.findViewById(R.id.componentqty);
        componentnameT = (TextView) rowView.findViewById(R.id.componentname);

        try {

            //Assigning values from array to individual layouts in list view

            componentnameT.setText(PName[position]);
            componentqtyT.setText(qty[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do u want to delete this Component??");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String jsonS = "{\"rawmaterialid\":\""+ materialId[position] +"\"}";
                        //Log.d("-jsnresponse add---",""+jsonS);
                        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/del_production/";


                        JSONObject rmdt = null;
                        try {
                            rmdt = new JSONObject(jsonS);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON Parser", "Error parsing data " + e.toString());

                        }

                        Volley_Del(urlrs, rmdt);

                        //Log.d("-jsnresponse enter---", "" + jsonS);
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), AddUserReq.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                PNameS = PName[position];
                QtyS = qty[position];
                Log.d("component" ," name :" + PNameS);
                Log.d("component" ," qty :" + QtyS);

            }
        });

        return rowView;
    }

    public void Volley_Del(String url, final JSONObject json)
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
                                    Intent intent = new Intent(context, AddProductionProduct.class);
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
