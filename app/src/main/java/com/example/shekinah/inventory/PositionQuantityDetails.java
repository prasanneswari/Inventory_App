package com.example.shekinah.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import static com.example.shekinah.inventory.ProductPositionAdapter.pID;
import static com.example.shekinah.inventory.ProductPositionAdapter.pName;
import static com.example.shekinah.inventory.ProductPositionAdapter.pStatus;
import static com.example.shekinah.inventory.ProductPositionAdapter.pPos;

public class PositionQuantityDetails extends AppCompatActivity {

    String[] productidA, positionA, quantityA, positionstatusA, positionidA, quantityidA;
    TextView p1,p2, s1;
    ListView ListPrd;
    Button back, add;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_quantity_details);

        p1 = (TextView)findViewById(R.id.p1);
        p2 = (TextView)findViewById(R.id.p2);
        s1 = (TextView)findViewById(R.id.s1);
        ListPrd = (ListView)findViewById(R.id.listPrd);
        back = (Button)findViewById(R.id.back);
        add = (Button)findViewById(R.id.addPQ);//
        p1.setText(pID);
        p2.setText(pName);
        s1.setText(pStatus);

        //String jsonS = "{\"shelfname\":\""+query+"\"}";
        String jsonS = "{\"productid\":\""+pID+"\"}";//
        Log.d("-jsnresponse search---",""+jsonS);
        //String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_shelfdetails/";
        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";//
        try {
            JSONObject rmdt = null;

            rmdt = new JSONObject(jsonS);
            GetVolley(urlrs, rmdt);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter---", "" + jsonS);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jsonS = "{\"productid\":\""+pID+"\"}";//
                Log.d("-jsnresponse search---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_positionquantity/";//
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    //addPQVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(PositionQuantityDetails.this, ProductPositionPg.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });
    }

    public void GetVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("--search response---", "---"+response.toString());
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> productidL = new ArrayList<String>();
                            List<String> positionL = new ArrayList<String>();
                            List<String> quantityL = new ArrayList<String>();
                            List<String> positionidL = new ArrayList<String>();
                            List<String> quantityidL = new ArrayList<String>();
                            List<String> positionstatusL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("position_quantity");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                String quantityS = new_array1.getJSONObject(i).getString("quantity");
                                String positionidS = new_array1.getJSONObject(i).getString("positionid");
                                String quantityidS = new_array1.getJSONObject(i).getString("quantityid");
                                String positionstatusS = new_array1.getJSONObject(i).getString("positionstatus");
                                productidL.add(productidS);
                                positionL.add(positionS);
                                quantityL.add(quantityS);
                                positionidL.add(positionidS);
                                quantityidL.add(quantityidS);
                                positionstatusL.add(positionstatusS);
                            }

                            productidA = new String[productidL.size()];
                            positionA = new String[positionL.size()];
                            quantityA = new String[quantityL.size()];
                            positionidA = new String[positionidL.size()];
                            quantityidA = new String[quantityidL.size()];
                            positionstatusA = new String[positionstatusL.size()];

                            for (int l = 0; l < positionL.size(); l++) {

                                productidA[l] = productidL.get(l);
                                positionA[l] = positionL.get(l);
                                quantityA[l] = quantityL.get(l);
                                positionstatusA[l] = positionstatusL.get(l);
                                Log.d("productidA ", productidA[l]);
                                positionidA[l] = positionidL.get(l);
                                Log.d("positionidA ", positionidA[l]);
                                quantityidA[l] = quantityidL.get(l);
                                Log.d("quantityidA ", quantityidA[l]);
                                Log.d("positionA ", positionA[l]);
                                Log.d("quantityA ", quantityA[l]);
                                Log.d("positionstatusA ", positionstatusA[l]);
                            }

                            PQtyDetailsAdapter reqAdapter = new PQtyDetailsAdapter(PositionQuantityDetails.this,productidA, positionA, quantityA, positionstatusA, positionidA, quantityidA);
                            ListPrd.setAdapter(reqAdapter);


                            //////////////////////////////////////////////////////


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));

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
//        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
