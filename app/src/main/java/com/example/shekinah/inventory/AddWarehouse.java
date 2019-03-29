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

public class AddWarehouse extends AppCompatActivity {

    String WHIdS, WHNameS, jsonS, statuSs,LoccS, sid, locid;
    Spinner statusS, LocS;
    String[] sIdA, statusNameA, lIdA, locationNameA;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        final EditText WHNameE = (EditText) findViewById(R.id.WHName);
        statusS = (Spinner)findViewById(R.id.status);
        LocS = (Spinner)findViewById(R.id.locId);

        getStatus();
        getlocation();

        Button AddNWHB = (Button) findViewById(R.id.NWH);
        Button Cancel = (Button) findViewById(R.id.addNWC);
        // alertdialog = builder.create();

        AddNWHB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WHNameS= WHNameE.getText().toString();
                statuSs = statusS.getSelectedItem().toString();
                LoccS = LocS.getSelectedItem().toString();
                for (int l = 0; l < sIdA.length; l++) {

                    if( statusNameA[l] == statuSs){
                        sid = sIdA[l];
                    }
                    Log.d("statusName ", statusNameA[l]);
                }
                for (int l = 0; l < lIdA.length; l++) {

                    if( locationNameA[l] == LoccS){
                        locid = lIdA[l];
                    }
                    Log.d("locationName ", locationNameA[l]);
                }

                jsonS = "{\"warehousename\":\""+WHNameS+"\",\"statusid\":\""+sid+"\",\"locationid\":\""+locid+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_warehouse/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);

                    //JSONSenderVolley(urlrs,jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddWarehouse.this, Warehouses.class);
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

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddWarehouse.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                    Intent intent = new Intent(AddWarehouse.this, Warehouses.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
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
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();

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

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
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

                            for (int l = 0; l < statusNameL.size(); l++) {

                                sIdA[l] = idL.get(l);
                                statusNameA[l] = statusNameL.get(l);
                                Log.d("sId ", sIdA[l]);
                                Log.d("statusName ", statusNameA[l]);
                            }
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(AddWarehouse.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusS.setAdapter(statusA);

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

    public void getlocation() {

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
                            List<String> idL = new ArrayList<String>();
                            List<String> locationNameL = new ArrayList<String>();
                            List<String> locationAddressL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("location_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String locationNameS = new_array1.getJSONObject(i).getString("locationname");
                                idL.add(idS);
                                locationNameL.add(locationNameS);
                            }

                            lIdA = new String[idL.size()];
                            locationNameA = new String[locationNameL.size()];

                            for (int l = 0; l < locationNameL.size(); l++) {

                                lIdA[l] = idL.get(l);
                                locationNameA[l] = locationNameL.get(l);
                                Log.d("lId ", lIdA[l]);
                                Log.d("locationName ", locationNameA[l]);
                            }

                            ArrayAdapter<String> locA= new ArrayAdapter<String>(AddWarehouse.this,android.R.layout.simple_spinner_item, locationNameA);
                            locA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            LocS.setAdapter(locA);
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
}
