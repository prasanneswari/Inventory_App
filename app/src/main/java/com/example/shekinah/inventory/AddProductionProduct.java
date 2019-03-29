package com.example.shekinah.inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import static com.example.shekinah.inventory.ProductionUnitsAdapter.uID;
import static com.example.shekinah.inventory.ProductionUnitsAdapter.uName;

public class AddProductionProduct extends AppCompatActivity {

    TextView prdSelected, unitname;
    EditText prdQ, searchTxt;
    ListView PrdDetails;
    CheckBox check;
    String prdQS, prdSelectedS, pid1, jsonS, urlrs;
    String[] idA, productnameA, pidA, qtyIA, productnameRA, jtsproductidA, basequantityA, rawmaterialidA, jtsproductnameA, productidA, productnamecA;
    com.android.volley.RequestQueue sch_RequestQueue;
    MaterialSearchView searchView;
    JSONObject rmdt = null;
    List<String> pidL = new ArrayList<String>();
    List<String> productnameL = new ArrayList<String>();
    List<String> qtyIL = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_production_product);

        prdSelected = (TextView) findViewById(R.id.prdSelected);
        unitname = (TextView)findViewById(R.id.unitName);
        unitname.setText(uName);
        PrdDetails = (ListView)findViewById(R.id.prdDetails);
        prdQ = (EditText) findViewById(R.id.prdQ);
        Button addP = (Button) findViewById(R.id.addP);
        Button cancel = (Button)findViewById(R.id.cancel);
        check = (CheckBox)findViewById(R.id.checkUnitComp);
        Button Add = (Button) findViewById(R.id.saveComponents);

        jsonS = "{\"jtsproductid\":\""+uID+"\"}";
        Log.d("-jsnresponse add---",""+jsonS);
        urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_productiondetails/";
        try {
            rmdt = new JSONObject(jsonS);
            getVollyProductionDetails(urlrs, rmdt);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter---", "" + jsonS);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prdQS = prdQ.getText().toString();
                prdSelectedS = prdSelected.getText().toString();
                if (prdQS.contentEquals("") || prdSelectedS.contentEquals("Select Compmonent...")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductionProduct.this);
                    builder.setTitle("Incorrect Entry");
                    builder.setMessage("Please enter valid Component and Quantity.");
                    builder.setMessage("Your selected product: "+ prdSelectedS);
                    builder.setMessage("Your selected quantity: "+ prdQS );
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                else {


                    Log.d("-prdQS  ---", "" + prdQS);

                    String productnameS = prdSelectedS;

                    pidL.add(pid1);
                    qtyIL.add(prdQS);
                    productnameL.add(productnameS);

                    productidA = new String[pidL.size()];
                    basequantityA = new String[qtyIL.size()];
                    productnamecA = new String[productnameL.size()];

                    for (int l = 0; l < pidL.size(); l++) {

                        basequantityA[l] = qtyIL.get(l);
                        productidA[l] = pidL.get(l);
                        productnamecA[l] = productnameL.get(l);
                        Log.d("qtyIA ", basequantityA[l]);
                        Log.d("productnameRA ", productnamecA[l]);
                    }

                    ProductionComponentsViewAdapter reqAdapter = new ProductionComponentsViewAdapter(AddProductionProduct.this, rawmaterialidA, productidA, basequantityA, productnamecA);
                    PrdDetails.setAdapter(reqAdapter);

//                    ProductionComponentsViewAdapter reqAdapter = new ProductionComponentsViewAdapter(AddProductionProduct.this, pidA, qtyIA, productnameRA);
//                    PrdDetails.setAdapter(reqAdapter);
                    prdQ.setText("");
                    prdSelected.setText("Select Product...");
                }
            }
        });
        prdSelected.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                get_product();

                final AlertDialog.Builder builder2 = new AlertDialog.Builder(AddProductionProduct.this);
                builder2.setTitle("Choose Product...");

                builder2.setItems(productnameA, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        prdSelected.setText(productnameA[item]);
                        pid1 = idA[item];
                        Log.d("productnameA[item] ", productnameA[item]);
                        Log.d("idA[item] ", pid1);
                        //itemSelected = false;
                    }
                });
                builder2.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder search = new AlertDialog.Builder(AddProductionProduct.this);
                        search.setTitle("Product Search...");
                        search.setMessage("Enter Product: ");

                        searchTxt = new EditText(AddProductionProduct.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        searchTxt.setLayoutParams(lp);
                        search.setView(searchTxt);
                        search.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String sPrd = searchTxt.getText().toString();
                                Log.d("", "searchTxt: "+ sPrd);
                                String jsonS = "{\"search\":\""+sPrd+"\"}";
                                Log.d("-jsnresponse search---",""+jsonS);
                                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/search_product/";
                                try {
                                    JSONObject rmdt = null;

                                    rmdt = new JSONObject(jsonS);
                                    JSONSenderVolley(urlrs, rmdt);
                                } catch (JSONException e) {

                                }
                                Log.d("-jsnresponse enter---", "" + jsonS);
                            }
                        });

                        AlertDialog alertSearch = search.create();
                        alertSearch.show();
                    }
                });

                AlertDialog alert = builder2.create();
                alert.show();

//                prdSelected.setText(pName);
//                pid1 = pID;
//                Log.d("prdSelected ",""+ pName);
//                Log.d("pid1 ",""+ pid1);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check.isChecked()) {

                    check.clearFocus();
                    Log.d(" uID", " -- " + uID);
                    //Log.d( " qtyPS", " -- " + qtyPS);
                    String p1 = "", q1 = "", p = "[", q = "[";
                    for (int qty = 0; qty < pidL.size(); qty++) {
                        p1 = pidL.get(qty);
                        p = p + p1 + ",";
                        Log.d(" ", "pid: " + p);
                    }
                    p = p.substring(0, p.length() - 1);
                    p = p + "]";
                    Log.d(" all pids", " -- " + p);

                    for (int qty = 0; qty < qtyIL.size(); qty++) {
                        q1 = qtyIL.get(qty);
                        q = q + q1 + ",";
                        Log.d(" ", "q: " + q);
                    }
                    q = q.substring(0, q.length() - 1);
                    q = q + "]";
                    Log.d(" all qs", " -- " + q);

                    jsonS = "{\"basequantity\":\"" + q + "\",\"productid\":\"" + p + "\",\"jtsproductid\":\"" + uID + "\"}";
                    Log.d("-jsnresponse add---", "jsonS: " + jsonS);
                    String urlrs = "http://" + domain_name + ":" + port + "/InventoryApp/add_production/";
                    Log.d("-jsnresponse add---", "urlrs: " + urlrs);
                    try {
                        JSONObject rmdt = null;

                        rmdt = new JSONObject(jsonS);

                        //JSONSenderVolley(urlrs,jsonS);
                        addComponentsVolly(urlrs, rmdt);
                    } catch (JSONException e) {

                    }
                    Log.d("-jsnresponse enter---", "" + jsonS);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductionProduct.this);
                    builder.setTitle("Verified your request? ");
                    builder.setMessage("Please check the checkbox if your are ready to submit. ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
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

    public void get_product() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_product/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("product_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                idL.add(idS);
                                productnameL.add(productnameS);
                            }

                            idA = new String[idL.size()];
                            productnameA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                idA[l] = idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("productname ", productnameA[l]);
                            }

                           /* ReqPrdSearchAdapter reqAdapter = new ReqPrdSearchAdapter(AddUserReq.this, idA, productnameA);
                            ListPrd.setAdapter(reqAdapter);

                            ListPrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    prName = productnameA[i];
                                    prdSelected.setText(prName);
                                }
                            });*/

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
                            List<String> idL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("product_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                idL.add(idS);
                                productnameL.add(productnameS);
                            }

                            idA = new String[idL.size()];
                            productnameA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                idA[l] = idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("productname ", productnameA[l]);
                            }

                            final AlertDialog.Builder builder3 = new AlertDialog.Builder(AddProductionProduct.this);
                            builder3.setTitle("Choose Product...");

                            builder3.setItems(productnameA, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    // Do something with the selection
                                    prdSelected.setText(productnameA[item]);
                                    pid1 = idA[item];
                                    Log.d("productnameA[item] ", productnameA[item]);
                                    Log.d("idA[item] ", pid1);
                                    //itemSelected = false;
                                }
                            });

                            AlertDialog dialog = builder3.create();
                            dialog.show();
//                            ReqPrdSearchAdapter reqAdapter = new ReqPrdSearchAdapter(AddUserReq.this,idA, productnameA);
//                            ListPrd.setAdapter(reqAdapter);
//
//                            ListPrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    prID = idA[i];
//                                    prName = productnameA[i];
//                                    prdSelected.setText(prName);
//                                }
//                            });
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductionProduct.this);
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

    public void addComponentsVolly(String url, final JSONObject json)
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
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            if (errorCode.contentEquals("1")){
                                pidL.clear();
                                qtyIL.clear();
                                productnameL.clear();
                            }

                            Intent intent = new Intent(AddProductionProduct.this,AddProductionProduct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductionProduct.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(AddProductionProduct.this,ProductionHomePg.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.setCancelable(false);
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

    public void getVollyProductionDetails(String url, final JSONObject json)
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
                            List<String> jtsproductidL = new ArrayList<String>();
                            List<String> basequantityL = new ArrayList<String>();
                            List<String> rawmaterialidL = new ArrayList<String>();
                            List<String> jtsproductnameL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("get_production_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String jtsproductidS = new_array1.getJSONObject(i).getString("jtsproductid");
                                String basequantityS = new_array1.getJSONObject(i).getString("basequantity");
                                String rawmaterialidS = new_array1.getJSONObject(i).getString("rawmaterialid");
                                String jtsproductnameS = new_array1.getJSONObject(i).getString("jtsproductname");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                jtsproductidL.add(jtsproductidS);
                                basequantityL.add(basequantityS);
                                rawmaterialidL.add(rawmaterialidS);
                                jtsproductnameL.add(jtsproductnameS);
                                productidL.add(productidS);
                                productnameL.add(productnameS);

                            }

                            jtsproductidA = new String[productnameL.size()];
                            basequantityA = new String[productnameL.size()];
                            rawmaterialidA = new String[productnameL.size()];
                            jtsproductnameA = new String[productnameL.size()];
                            productidA = new String[productnameL.size()];
                            productnamecA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                jtsproductidA[l] = jtsproductidL.get(l);
                                basequantityA[l] = basequantityL.get(l);
                                rawmaterialidA[l] = rawmaterialidL.get(l);
                                jtsproductnameA[l] = jtsproductnameL.get(l);
                                productidA[l] = productidL.get(l);
                                productnamecA[l] = productnameL.get(l);
                                Log.d("qtyIA ", basequantityA[l]);
                                Log.d("productnameRA ", productnamecA[l]);
                            }

                            ProductionComponentsViewAdapter reqAdapter = new ProductionComponentsViewAdapter(AddProductionProduct.this, rawmaterialidA, productidA, basequantityA, productnamecA);
                            PrdDetails.setAdapter(reqAdapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                       /* try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(ReqDetails.this);
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
                        }*/
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
