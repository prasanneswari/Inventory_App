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
import static com.example.shekinah.inventory.Adapter_purchase_Order.lID;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class Purchase_products extends AppCompatActivity {
    ListView prd_lst;
    String[] pur_detailsid = {};
    String[] prd_id = {};
    String[] prd_name = {};
    String[] prd_desc = {};
    String[] prd_company = {};
    String[] prd_quantity = {};


    AlertDialog.Builder builder;
    AlertDialog alertdialog;
    com.android.volley.RequestQueue sch_RequestQueue;
    Button confirmB,receivedB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_products);
        prd_lst=(ListView)findViewById(R.id.purchase_prdlist);
        confirmB=(Button)findViewById(R.id.confirmbtn);
        receivedB=(Button)findViewById(R.id.receivedbtn);
        purchase_post();

        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_orderd_post();
            }
        });
        receivedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_received_post();
            }
        });
    }
    public void update_received_post(){
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        String update_received_post = "{\"orderid\":\"" + lID + "\",\"statusid\":\"11\"}";
        Log.d(" update_received_post.", "---" + update_received_post);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String update_received_url = "http://"+domain_name+":"+port+"/InventoryApp/update_purchasestatus/";
        // String urlrs= "https://jtsha.in/service/validate_web" ;
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(update_received_post);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse received...", "---" + update_received_post);
        JSONSenderVolley(update_received_url, lstrmdt);
    }

    public void update_orderd_post(){
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        String pur_update_post = "{\"orderid\":\"" + lID + "\",\"statusid\":\"10\"}";
        Log.d("jsnresponse pur_update", "---" + pur_update_post);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String pur_update_url = "http://cld003.jts-prod.in:20105/InventoryApp/update_purchasestatus/";
        // String urlrs= "https://jtsha.in/service/validate_web" get_status;
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(pur_update_post);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + pur_update_post);
        JSONSenderVolley(pur_update_url, lstrmdt);
    }
    public void purchase_post(){
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        String pur_prds = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse pur_prds111", "---" + pur_prds);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String pur_url = "http://"+domain_name+":"+port+"/InventoryApp/get_purchasedetails/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(pur_prds);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + pur_prds);
        JSONSenderVolley(pur_url, lstrmdt);
    }
    public  void pur_request(JSONObject responseJSON){

        try {
            //Log.d( " Array", " response ->  " + response);
            JSONArray new_array1;
            List<String> pur_detailsL = new ArrayList<String>();
            List<String> prd_idL = new ArrayList<String>();
            List<String> prd_nameL = new ArrayList<String>();
            List<String> prd_descL = new ArrayList<String>();
            List<String> prd_companyL = new ArrayList<String>();
            List<String> prd_qtyL = new ArrayList<String>();
            new_array1 = responseJSON.getJSONArray("purchase_details");
            Log.d( " Array", " : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String pur_detaisS = new_array1.getJSONObject(i).getString("purchasedetailsid");
                String prd_idS = new_array1.getJSONObject(i).getString("productid");
                String prd_nameS = new_array1.getJSONObject(i).getString("productname");
                String prd_descS = new_array1.getJSONObject(i).getString("productdescription" + "");
                String prd_companyS = new_array1.getJSONObject(i).getString("productcomapany");
                String prd_quantityS = new_array1.getJSONObject(i).getString("quantityrequested");

                pur_detailsL.add(pur_detaisS);
                prd_idL.add(prd_idS);
                prd_nameL.add(prd_nameS);
                prd_descL.add(prd_descS);
                prd_companyL.add(prd_companyS);
                prd_qtyL.add(prd_quantityS);

            }
            pur_detailsid = new String[pur_detailsL.size()];
            prd_id = new String[prd_idL.size()];
            prd_name = new String[prd_nameL.size()];
            prd_desc = new String[prd_descL.size()];
            prd_company = new String[prd_companyL.size()];
            prd_quantity = new String[prd_qtyL.size()];

            for (int l = 0; l < prd_qtyL.size(); l++) {
                pur_detailsid[l] = pur_detailsL.get(l);
                prd_id[l] = prd_idL.get(l);
                prd_name[l] = prd_nameL.get(l);
                prd_desc[l] = prd_descL.get(l);
                prd_company[l] = prd_companyL.get(l);
                prd_quantity[l] = prd_qtyL.get(l);

                Log.d("pur_detailsid ", pur_detailsid[l]);
                Log.d("prd_id ", prd_id[l]);
                Log.d("prd_name ", prd_name[l]);
                Log.d("prd_desc ", prd_desc[l]);
                Log.d("prd_company ", prd_company[l]);
                Log.d("prd_quantity ", prd_quantity[l]);


            }

            Adapter_Purchase_products reqAdapter = new Adapter_Purchase_products(Purchase_products.this,pur_detailsid,prd_id, prd_name, prd_desc, prd_company,prd_quantity);
            prd_lst.setAdapter(reqAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void JSONSenderVolley(String pur_url, final JSONObject json)
    {
        Log.d("update_received_post-", "---"+pur_url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                pur_url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----pur_url values-----", "---"+response.toString());

                        pur_request(response);
                        //get_room_ssid(response);
                        //startedScanner();
                        //get_spinner_response(response);
                        //  edit_response(response);
//                        try {
//                            login_code = response.getInt("error_code");
//                            String er_discp=response.getString("error_desc");
//
//                            String[] separated = er_discp.split("=");
//                            if(login_code==0){
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//                            }else
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//
//
//                        } catch (JSONException e) {
//
//                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" ", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();

            }

        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
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

