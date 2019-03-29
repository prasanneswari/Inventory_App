package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class ProductPositionPg extends AppCompatActivity {

    String[] quantityidA,positionA, productidA, productnameA, quantityA, productstatusA;
    ListView ListPrd;
    EditText searchpid, searchpos, searchprd;
    Button refreshPP,cancelPrd, multiSearch;
    public ProgressDialog dialog_progress ;
    MaterialSearchView searchView;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_position_pg);
        ListPrd = (ListView)findViewById(R.id.listPrd);//
        multiSearch = (Button)findViewById(R.id.multiSearch);
        searchpid = (EditText)findViewById(R.id.searchpid);
        searchpos = (EditText)findViewById(R.id.searchpos);
        searchprd = (EditText)findViewById(R.id.searchprd);
        refreshPP = (Button)findViewById(R.id.refreshPP);
        cancelPrd = (Button)findViewById(R.id.cancelPrd);
        dialog_progress = new ProgressDialog(ProductPositionPg.this);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.searchPosB);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        // ListPos = (ListView)findViewById(R.id.listPos);
        //alertdialog = builder.create();

        multiSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchpidS = searchpid.getText().toString();
                String searchposS = searchpos.getText().toString();
                String searchprdS = searchprd.getText().toString();
                String jsonS = "{\"positionname\":\""+searchposS+"\""+",\"productid\":\"" + searchpidS+"\""+",\"productname\":\"" + searchprdS+"\"}";// {"positionname":"s1r1","productid":"1","productname":"pen"}
                Log.d("-jsnresponse search---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/search_multiple/";//
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    SearchMultiple(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener(){
            @Override
            public void onSearchViewShown(){

                Log.d( " searchViewShown", " -- " + searchView);
            }

            @Override
            public void onSearchViewClosed(){

                Log.d( " searchViewClosed", " -- " + searchView);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d( " onQueryTextSubmit", " -- " + query);

                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                //String jsonS = "{\"shelfname\":\""+query+"\"}";
                String jsonS = "{\"positionname\":\""+query+"\"}";//
                Log.d("-jsnresponse search---",""+jsonS);
                //String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_shelfdetails/";
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/search_productquantity/";//
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    SearchVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d( " onQueryTextChange", " -- " + newText);
                return true;
            }
        });
        httpRequest1();
        refreshPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpRequest1();
            }
        });
        cancelPrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductPositionPg.this, Positions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
    public void httpRequest1() {

        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_productquantity/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        dialog_progress.hide();
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> quantityidL = new ArrayList<String>();
                            List<String> positionL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            List<String> quantityL = new ArrayList<String>();
                            List<String> productstatusL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("product_quantity_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String quantityidS = new_array1.getJSONObject(i).getString("quantityid");
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String productstatusS = new_array1.getJSONObject(i).getString("productstatus");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String quantityS = new_array1.getJSONObject(i).getString("quantity");
                                quantityidL.add(quantityidS);
                                positionL.add(positionS);
                                productidL.add(productidS );
                                productnameL.add(productnameS);
                                quantityL.add(quantityS);
                                productstatusL.add(productstatusS);
                            }

                            quantityidA = new String[quantityidL.size()];
                            positionA = new String[quantityidL.size()];
                            productidA = new String[productidL.size()];
                            productnameA = new String[quantityidL.size()];
                            quantityA = new String[quantityidL.size()];
                            productstatusA = new String[productstatusL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                quantityidA[l] = quantityidL.get(l);
                                positionA[l] = positionL.get(l);
                                productidA[l] = productidL.get(l);
                                productnameA[l] = productnameL.get(l);
                                quantityA[l] = quantityL.get(l);
                                productstatusA[l] = productstatusL.get(l);
                                Log.d("quantityidA ", quantityidA[l]);
                                Log.d("positionA ", positionA[l]);
                                Log.d("productnameA ", productnameA[l]);
                                Log.d("quantityA ", quantityA[l]);
                                Log.d("productstatusA ", productstatusA[l]);
                            }

                            ProductPositionAdapter reqAdapter = new ProductPositionAdapter(ProductPositionPg.this, quantityidA, productidA, positionA, productnameA, quantityA, productstatusA);
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
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");
            }
        });

        // Add the request to the RequestQueue.

        queue.add(stringRequest);
    }

    public void SearchMultiple(String url, final JSONObject json)
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
                        dialog_progress.hide();
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> warehousenameL = new ArrayList<String>();
                            List<String> positionL = new ArrayList<String>();
                            List<String> positionidL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            List<String> quantityL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("get_shelf_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                //String warehousenameS = new_array1.getJSONObject(i).getString("warehousename");
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                String positionidS = new_array1.getJSONObject(i).getString("positionid");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String quantityS = new_array1.getJSONObject(i).getString("quantity");
                                //warehousenameL.add(warehousenameS);
                                positionL.add(positionS);
                                positionidL.add(positionidS);
                                productidL.add(productidS);
                                productnameL.add(productnameS);
                                quantityL.add(quantityS);
                            }

                            positionA = new String[productnameL.size()];
                            productidA = new String[productnameL.size()];
                            productnameA = new String[productnameL.size()];
                            quantityA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                positionA[l] = positionL.get(l);
                                productidA[l] = productidL.get(l);
                                productnameA[l] = productnameL.get(l);
                                quantityA[l] = quantityL.get(l);
                                Log.d("positionA ", positionA[l]);
                                Log.d("productnameA ", productnameA[l]);
                                Log.d("quantityA ", quantityA[l]);
                                Log.d("productstatusA ", productidA[l]);
                            }

                            ProductPositionAdapter reqAdapter = new ProductPositionAdapter(ProductPositionPg.this, quantityidA, productidA, positionA, productnameA, quantityA, productstatusA);
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


    public void SearchVolley(String url, final JSONObject json)
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
                        dialog_progress.hide();
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> quantityidL = new ArrayList<String>();
                            List<String> positionL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            List<String> quantityL = new ArrayList<String>();
                            List<String> productstatusL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("product_quantity_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String quantityidS = new_array1.getJSONObject(i).getString("quantityid");
                                String positionS = new_array1.getJSONObject(i).getString("position");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String productstatusS = new_array1.getJSONObject(i).getString("productstatus");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String quantityS = new_array1.getJSONObject(i).getString("quantity");
                                quantityidL.add(quantityidS);
                                positionL.add(positionS);
                                productnameL.add(productnameS);
                                quantityL.add(quantityS);
                                productstatusL.add(productstatusS);
                            }

                            quantityidA = new String[quantityidL.size()];
                            positionA = new String[quantityidL.size()];
                            productidA = new String[productidL.size()];
                            productnameA = new String[quantityidL.size()];
                            quantityA = new String[quantityidL.size()];
                            productstatusA = new String[productstatusL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                quantityidA[l] = quantityidL.get(l);
                                positionA[l] = positionL.get(l);
                                productidA[l] = productidL.get(l);
                                productnameA[l] = productnameL.get(l);
                                quantityA[l] = quantityL.get(l);
                                productstatusA[l] = productstatusL.get(l);
                                Log.d("quantityidA ", quantityidA[l]);
                                Log.d("positionA ", positionA[l]);
                                Log.d("productnameA ", productnameA[l]);
                                Log.d("quantityA ", quantityA[l]);
                                Log.d("productstatusA ", productstatusA[l]);
                            }

                            ProductPositionAdapter reqAdapter = new ProductPositionAdapter(ProductPositionPg.this, quantityidA, productidA, positionA, productnameA, quantityA, productstatusA);
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

