package com.example.shekinah.inventory;

/**
 * Created by Shekinah.. on 06/03/2018.
 */

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

public class purchase_order extends AppCompatActivity {
    ListView purchase_lst;
    String[] purid = {};
    String[] purReqdate = {};
    String[] purDuedate = {};
    String[] purStatus = {};
    public static String puridS,purReqdateS,purDuedateS,purStatusS;


    AlertDialog.Builder builder;
    AlertDialog alertdialog;
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);
        purchase_lst=(ListView)findViewById(R.id.purchase_list);

        httpRequest();

    }
    public void httpRequest() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url="http://"+domain_name+":"+port+"/InventoryApp/get_purchaserequisitions/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello response :", response.toString());
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
                            List<String> puridL = new ArrayList<String>();
                            List<String> req_dateL = new ArrayList<String>();
                            List<String> due_dateL = new ArrayList<String>();
                            List<String> statusL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("purchasable_product_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                puridS = new_array1.getJSONObject(i).getString("orderid");
                                Log.d("puridS0000000 ", puridS);
                                purReqdateS = new_array1.getJSONObject(i).getString("requesteddate");
                                purDuedateS = new_array1.getJSONObject(i).getString("duedate");
                                purStatusS = new_array1.getJSONObject(i).getString("status");
                                puridL.add(puridS);
                                req_dateL.add(purReqdateS);
                                due_dateL.add(purDuedateS);
                                statusL.add(purStatusS);
                            }

                            purid = new String[puridL.size()];
                            purReqdate = new String[req_dateL.size()];
                            purDuedate = new String[due_dateL.size()];
                            purStatus = new String[statusL.size()];

                            for (int l = 0; l < statusL.size(); l++) {

                                purid[l] = puridL.get(l);
                                purReqdate[l] = req_dateL.get(l);
                                purDuedate[l] = due_dateL.get(l);
                                purStatus[l] = statusL.get(l);
                                Log.d("purid ", purid[l]);
                                Log.d("purReqdate ", purReqdate[l]);
                                Log.d("purDuedate ", purDuedate[l]);
                                Log.d("purStatus ", purStatus[l]);
                            }

                            Adapter_purchase_Order reqAdapter = new Adapter_purchase_Order(purchase_order.this,purid, purReqdate, purDuedate, purStatus);
                            purchase_lst.setAdapter(reqAdapter);

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

    public void JSONSenderVolley(String url, final JSONObject json)
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
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(purchase_order.this);
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
