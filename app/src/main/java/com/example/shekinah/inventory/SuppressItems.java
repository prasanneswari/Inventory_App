package com.example.shekinah.inventory;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class SuppressItems extends AppCompatActivity {

    Button cancel;

    public static ListView suppressListView;
    public static ArrayList<SuppressDict> suppressDict;
    public static SuppressAdapter adapter;

    RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppress_items);

        suppressListView = (ListView) findViewById(R.id.L_PendingList_Id);
        suppressDict = new ArrayList<SuppressDict>();

        cancel = (Button) findViewById(R.id.S_CancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuppressItems.this, PurchasePending.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        httpRequest();
    }

    public void httpRequest()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_suppressedProducts/";
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

//                            JSONArray jsonResp = jsonObjectResp.getJSONArray("purchasable_product_details");//CHANGE
//                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
//
//                            for (int i = 0; i < jsonResp.length(); i++)
//                            {
//                                Boolean checked = false;
//                                String pId = jsonResp.getJSONObject(i).getString("productid");
//                                String pName = jsonResp.getJSONObject(i).getString("productname");
//                                String pPos = jsonResp.getJSONObject(i).getString("productposition");
//                                String pThresh = jsonResp.getJSONObject(i).getString("reorderlevel");
//                                String pCurrQty = jsonResp.getJSONObject(i).getString("currentquantity");
//                                String qty = "0";
//                                SuppressDict.add(new SuppressDict(checked,pId,pName,pPos,pThresh,pCurrQty,qty));
//                            }
//
//                            updateList();

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR ",""+error);
                Toast.makeText(SuppressItems.this, ""+error, Toast.LENGTH_SHORT).show();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void updateList()
    {
        adapter = new SuppressAdapter(SuppressItems.this,suppressDict);
        suppressListView.setAdapter(adapter);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
