package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import static com.example.shekinah.inventory.WareHouseAdapter.whNam;
import static com.example.shekinah.inventory.WareHouseAdapter.whL;
import static com.example.shekinah.inventory.WareHouseAdapter.whS;


public class updateWarehouse extends AppCompatActivity {

    String[] locationNameA, locationIdA, sIdA, statusNameA;
    Spinner statusULE, WHULIdE;
    int locI,sidI;
    String locid, sid, urlrs;
    JSONObject rmdt;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_warehouse);

        Button UCancel = (Button) findViewById(R.id.updateUWC);

        final EditText WHUNameE = (EditText) findViewById(R.id.WHUName);
        WHULIdE = (Spinner) findViewById(R.id.WHULId);
        statusULE = (Spinner) findViewById(R.id.WHUS);
        Button DelWH = (Button) findViewById(R.id.DWH);
        final Button Update = (Button) findViewById(R.id.UWH);
        getLocation();
        getStatus();
        WHUNameE.setText(whNam);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = whID;
                String name = WHUNameE.getText().toString();
                String loc = WHULIdE.getSelectedItem().toString();
                String sidd = statusULE.getSelectedItem().toString();

                for (int l3 = 0; l3 < sIdA.length; l3++) {

                    if( statusNameA[l3] == sidd){
                        sid = sIdA[l3];
                    }
                    Log.d("statusName ", statusNameA[l3]);
                }

                for (int l = 0; l < locationIdA.length; l++) {

                    if( locationNameA[l] == loc){
                        locid = locationIdA[l];
                    }

                    Log.d("locationName ", locationNameA[l]);
                }
                String statuSs = statusULE.getSelectedItem().toString();
                String jsonS = "{\"id\":\""+whID+"\",\"warehousename\":\""+name+"\",\"statusid\":\""+sid+"\",\"locationid\":\""+locid+"\"}";
                Log.d("-jsnresponse update---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_warehouse/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        DelWH.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String jsonDel = "{\"id\":\""+whID+"\"}";
                Log.d("----jsnresponse delete-", "" + jsonDel);

                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                urlrs ="http://"+domain_name+":"+port+"/InventoryApp/del_warehouse/";
                // String urlrs= "https://jtsha.in/service/validate_web";
                // try {
                try {
                    rmdt = new JSONObject(jsonDel.toString());
                } catch (JSONException e) {

                }


                final AlertDialog.Builder builder = new AlertDialog.Builder(updateWarehouse.this);
                builder.setTitle("Info");
                builder.setMessage("Do you want to Delete ??");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Log.d("----jsnresponse enterd3333-----", "---" + water_status);
                        JSONSenderVolleyD(urlrs, rmdt);
                        //param_delete.remove(param_delete[i]);
                        Intent intent = new Intent(updateWarehouse.this, Warehouses.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        UCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(updateWarehouse.this, Warehouses.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());

                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(updateWarehouse.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

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

    public void getLocation() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_location/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> locationIdL = new ArrayList<String>();
                            List<String> locationNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("location_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String locationNameS = new_array1.getJSONObject(i).getString("locationname");
                                locationIdL.add(idS);
                                locationNameL.add(locationNameS);
                            }

                            locationIdA = new String[locationIdL.size()];
                            locationNameA = new String[locationNameL.size()];

                            for (int l = 0; l < locationNameL.size(); l++) {

                                locationIdA[l] = locationIdL.get(l);
                                locationNameA[l] = locationNameL.get(l);
                                Log.d("locationId ", locationIdA[l]);
                                Log.d("locationName ", locationNameA[l]);
                            }

                            for (int l1 = 0; l1 < locationIdA.length; l1++) {

                                if( locationNameA[l1] == whL){
                                    locid = locationIdA[l1];
                                    locI = l1;
                                    WHULIdE.setSelection(l1);
                                    Log.d("Selected locationName ", locationNameA[l1]);
                                }


                            }

                            Log.d("0000000000000000 ","");
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(updateWarehouse.this,android.R.layout.simple_spinner_item, locationNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            WHULIdE.setAdapter(statusA);

                            Log.d(" obtained locId ","from adapter-- " + whL);
                            for (int n = 0; n < locationNameL.size(); n++) {
                                if (locationNameA[n].contentEquals(whL)) {
                                    WHULIdE.setSelection(n);
                                    Log.d(" locID ", locationNameA[n]);
                                }
                                else {
                                    Log.d(" locID else", locationNameA[n]);
                                }
                            }

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
    public void getStatus() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_status/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> statusNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("status_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String statusNameS = new_array1.getJSONObject(i).getString("statusname");
                                idL.add(idS);
                                statusNameL.add(statusNameS);
                            }

                            sIdA = new String[idL.size()];
                            statusNameA = new String[statusNameL.size()];
                            Log.d("11111111111111111 ", "");

                            for (int l = 0; l < statusNameL.size(); l++) {

                                sIdA[l] = idL.get(l);
                                statusNameA[l] = statusNameL.get(l);
                                Log.d("sId ", sIdA[l]);
                                Log.d("statusName ", statusNameA[l]);
                                Log.d("222222222222222 ", "");
                            }

                            for (int l2 = 0; l2 < statusNameA.length; l2++) {

                                Log.d("33333333333333 ", "");
                                if( statusNameA[l2] == whL){
                                    sid = statusNameA[l2];
                                    sidI = l2;
                                    statusULE.setSelection(l2);
                                    Log.d("Selected ", " Spinner position ----- "+ l2 );
                                }

                                Log.d("statusName  ", statusNameA[l2]);
                            }

                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(updateWarehouse.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusULE.setAdapter(statusA);

                            Log.d(" obtained statusId ","from adapter -- " + whS);
                            for (int n = 0; n < statusNameL.size(); n++) {
                                if (statusNameA[n].contentEquals(whS)) {
                                    statusULE.setSelection(n);
                                    Log.d(" statusId ", statusNameA[n]);
                                }
                                else {
                                    Log.d(" statusId else", statusNameA[n]);
                                }
                            }

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

    public void JSONSenderVolleyD(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("JSON", "string ---" +json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("---------", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","-------" + errorCode+"--");

                            final AlertDialog.Builder builder = new AlertDialog.Builder(updateWarehouse.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
/*
                            if (errorCode.contentEquals("0")){
                                Log.d(" Warehouse Deleted ","Successfully... :)");
                                Intent intent = new Intent(updateWarehouse.this, Warehouses.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("error-----"," Ooops Warehouse NOT deleted Successfully  :(  ");
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));

            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
