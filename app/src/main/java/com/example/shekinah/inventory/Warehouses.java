package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.WareHouseAdapter.whID;

public class Warehouses extends AppCompatActivity {

    String[] idA = {};
    String[] WHNameA = {};
    String[] LocIdA = {};
    String[] StatusA = {};

    private ProgressDialog dialog_progress ;
    ListView ListWH;
    AlertDialog alertdialog;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouses);
        httpRequest1();
        Button AddWH = (Button) findViewById(R.id.addWH);
        Button Cancel = (Button) findViewById(R.id.cancelWH);
        ListWH = (ListView)findViewById(R.id.listWH);
        dialog_progress = new ProgressDialog(Warehouses.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        AddWH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Warehouses.this, AddWarehouse.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void httpRequest1() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://"+domain_name+":"+port+"/InventoryApp/get_warehouse/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.d("hello :", response.toString());

                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> warehouseNameL = new ArrayList<String>();
                            List<String> locationId_idL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();

                            new_array1 = responseJSON.getJSONArray("warehouse_details");
                            Log.d( " Array", " requisition : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String warehouseNameS = new_array1.getJSONObject(i).getString("warehousename");
                                String locationId_idS = new_array1.getJSONObject(i).getString("locationname");
                                String statusId_idS = new_array1.getJSONObject(i).getString("statusname");

                                idL.add(idS);
                                warehouseNameL.add(warehouseNameS);
                                locationId_idL.add(locationId_idS);
                                statusId_idL.add(statusId_idS);
                            }
                            idA = new String[idL.size()];
                            WHNameA = new String[idL.size()];
                            LocIdA = new String[idL.size()];
                            StatusA = new String[idL.size()];

                            for (int l = 0; l < idL.size(); l++) {
                                idA[l] = idL.get(l);
                                WHNameA[l] = warehouseNameL.get(l);
                                LocIdA[l] = locationId_idL.get(l);
                                StatusA[l] = statusId_idL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("WHName ", WHNameA[l]);
                                Log.d("LocId ", LocIdA[l]);
                                Log.d("Status ", StatusA[l]);
                            }
//
//                            //jsonReq = responseJSON.optJSONObject("requisition");
//                            //String reqStatus = jsonReq.getString("status");
//                            //Assigning arrays to adapter required for the front end in list view
//
                            WareHouseAdapter reqAdapter = new WareHouseAdapter(Warehouses.this, idA, WHNameA, LocIdA, StatusA);
                            ListWH.setAdapter(reqAdapter);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
