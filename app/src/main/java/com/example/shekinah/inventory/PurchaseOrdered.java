package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;


public class PurchaseOrdered extends AppCompatActivity {

    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builder;
    AlertDialog alert_dialog;
    Button cancel;

    public static ListView orders_listview;
    public static ArrayList<OrderedDict> orderedDict;
    public static OrderedAdapter adapter;

    public static ArrayList<StatusDict> statusDict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ordered);

        orders_listview = (ListView) findViewById(R.id.L_OrderedList_Id);
        orderedDict = new ArrayList<OrderedDict>();
        statusDict = new ArrayList<StatusDict>();

        cancel = (Button) findViewById(R.id.B_Cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurchaseOrdered.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        httpRequest1();
    }

    public void httpRequest2() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_purchaserequisitions/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());

                        try
                        {
                            JSONObject jsonObjectResp = new JSONObject(response);

                            JSONArray jsonResp = jsonObjectResp.getJSONArray("purchasable_product_details");
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                String orderId = jsonResp.getJSONObject(i).getString("orderid");
                                String reqDate = jsonResp.getJSONObject(i).getString("requesteddate");
                                String dueDate = jsonResp.getJSONObject(i).getString("duedate");
                                String status = jsonResp.getJSONObject(i).getString("status");

                                orderedDict.add(new OrderedDict(orderId,reqDate,dueDate,status));
                            }

                            updateList();

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR ",""+error);
                Toast.makeText(PurchaseOrdered.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void httpRequest1()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_status/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());

                        try
                        {
                            JSONObject jsonObjectResp = new JSONObject(response);

                            JSONArray jsonResp = jsonObjectResp.getJSONArray("status_details");//CHANGE
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);

                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                String id = jsonResp.getJSONObject(i).getString("id");
                                String status = jsonResp.getJSONObject(i).getString("statusname");
                                statusDict.add(new StatusDict(id,status));
                            }
                            httpRequest2();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR ",""+error);
                Toast.makeText(PurchaseOrdered.this, ""+error, Toast.LENGTH_SHORT).show();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateList()
    {
        adapter = new OrderedAdapter(PurchaseOrdered.this,orderedDict);
        orders_listview.setAdapter(adapter);
    }
}

/*
{"purchasable_product_details":[
{
"duedate": "2018-03-30",
"orderid": 20,
"requesteddate": "2018-03-01",
"status": "Not Ordered"
},
{ "duedate": "2018-03-30", "orderid": 21, "requesteddate": "2018-03-01", "status": "Not Ordered" } ]}
 */
