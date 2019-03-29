package com.example.shekinah.inventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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
import static com.example.shekinah.inventory.PurchaseOrdered.statusDict;

public class OrderDetails extends AppCompatActivity {

    Button cancel,update;

    public static ListView details_listview;
    public static ArrayList<DetailsDict> detailsDict;
    public static DetailsAdapter dAdapter;

    com.android.volley.RequestQueue sch_RequestQueue;

    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        details_listview = (ListView) findViewById(R.id.L_DetailsList_Id);
        detailsDict = new ArrayList<DetailsDict>();


        cancel = (Button) findViewById(R.id.D_Cancel_Id);
        update = (Button) findViewById(R.id.D_Update);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString("orderId");
            Log.d("ORDER DETAIL","ID -- "+orderId);
            getOrderDetails(orderId);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postData();
                }
            });

        }
        else
        {
            Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }

    public void postData()
    {
        List<String> productIdList = new ArrayList<String>();
        List<String> qtyList = new ArrayList<String>();
        List<String> statusList = new ArrayList<String>();
        int count = 0;
        for(DetailsDict f : detailsDict) {
            if(f.Checked == true)
            {
                count = count + 1;
                productIdList.add(f.ProductId);
                qtyList.add(f.QtyRequested);
                for(StatusDict s : statusDict)
                {
                    Log.d("COMPARE",s.Status+" -- "+f.Status);
                    if(s.Status.equals(f.Status))
                    {
                        statusList.add(s.Id);
                        break;
                    }
                }

            }
        }

        if(count > 0)
        {
            String jsonString = "{\"purchasedetailsid\":\"" + productIdList + "\", \"quantityreceived\":\"" + qtyList + "\", \"status\":\"" + statusList + "\"}";
            Log.d("-jsnresponse add---",""+jsonString);
            String urlToCall = "http://"+domain_name+":"+port+"/InventoryApp/update_purchaserequisition/";
            try
            {
                JSONObject jsonStrObj = new JSONObject(jsonString);
                JSONSenderVolley(urlToCall, jsonStrObj);
            }
            catch (JSONException e) {
                Log.d("ERROR",""+e);
            }
        }
        else
        {
            Toast.makeText(this, "EMPTY ORDER", Toast.LENGTH_SHORT).show();
        }

    }

    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("URL", "--- "+url);
        Log.d("JSON STRING", ""+json.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("---------", "---"+response.toString());
                        try
                        {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);
                            Toast.makeText(OrderDetails.this, ""+errorDesc, Toast.LENGTH_SHORT).show();
                            if(errorCode == "1")
                            {
                                onBackPressed();
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(" VOLLEY ERROR JSON ", "" + String.valueOf(error));
                        Log.d(" VOLLEY ERROR JSON ","" + String.valueOf(error));
                        Toast.makeText(OrderDetails.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
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
    }


    public void getOrderDetails(final String orderId)
    {
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_purchasedetails/";
        String jsonString = "{\"orderid\":\"" + orderId + "\"}";
        Log.d("CREDS", ""+url+" -- "+jsonString);
        JSONObject json = null;
        try
        {
            json = new JSONObject(jsonString);
        }
        catch (JSONException e) {
            Log.d("ERROR",""+e);
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, json,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("---------", "---"+response.toString());

                        try {

                            JSONArray jsonResp = response.getJSONArray("purchase_details");//CHANGE
                            Log.d("VOLLEY", "  JSONRESP ->  " + jsonResp);
                            for (int i = 0; i < jsonResp.length(); i++)
                            {
                                String dId = jsonResp.getJSONObject(i).getString("productid");
                                String dName = jsonResp.getJSONObject(i).getString("productname");
                                String dDesc = jsonResp.getJSONObject(i).getString("productdescription");
                                String dQty = jsonResp.getJSONObject(i).getString("quantityrequested");
                                String dStatus = jsonResp.getJSONObject(i).getString("status");
                                detailsDict.add(new DetailsDict(false,dId,dName,dDesc,dQty,dStatus));
                            }

                            updateList();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(" VOLLEY ERROR JSON ", "" + String.valueOf(error));
                        Log.d(" VOLLEY ERROR JSON ","" + String.valueOf(error));
                        Toast.makeText(OrderDetails.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
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
    }

    public void updateList()
    {
        dAdapter = new DetailsAdapter(OrderDetails.this,detailsDict);
        details_listview.setAdapter(dAdapter);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

/*

{"purchase_details":[
{
"productcomapany": "",
"productdescription": "Reynolds",
"productid": 1,
"productname": "Pen",
"purchasedetailsid": 1,
"purchaseorderid": 20,
"quantityreceived": 0,
"quantityrequested": 10,
"status": "Not Issued"
},
{
"productcomapany": "",
"productdescription": "Camel",
"productid": 3,
"productname": "Permanent marker",
"purchasedetailsid": 3,
"purchaseorderid": 20,
"quantityreceived": 0,
"quantityrequested": 30,
"status": "Not Issued"
}
]}



 */
