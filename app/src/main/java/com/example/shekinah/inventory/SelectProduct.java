package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import static com.example.shekinah.inventory.UserMainAdapter.urID;
import static com.example.shekinah.inventory.SearchPrdAdapter.pName;
import static com.example.shekinah.inventory.SearchPrdAdapter.pID;
import static com.example.shekinah.inventory.SearchPrdAdapter.pproducttype;
import static com.example.shekinah.inventory.SearchPrdAdapter.preorderlevel;
import static com.example.shekinah.inventory.SearchPrdAdapter.pstandardcost;
import static com.example.shekinah.inventory.SearchPrdAdapter.preorderqty;

public class SelectProduct extends AppCompatActivity {

    ListView ListPrd;
    static EditText input;
    String[] idA, productnameA, productdescriptionA, statusId_idA, standardcostA, reorderlevelA, reorderqtyA, producttypeA;
    MaterialSearchView searchView;
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Button addB = (Button) findViewById(R.id.addPrd);
        Button refresh = (Button) findViewById(R.id.refreshP);

        Button Cancel = (Button) findViewById(R.id.cancelPrd);
        ListPrd = (ListView)findViewById(R.id.listPrd);
        getProducts();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        ListPrd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long id) {
                ListPrd.getChildAt(position).setBackgroundColor(Color.GREEN);
                Log.d("selected Prd id", ""+ pID);
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
                /*lstviewP = (ListView)findViewById(R.id.prdView);
                ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstSource);
                lstviewP.setAdapter(adapter);*/

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d( " onQueryTextSubmit", " -- " + query);

                String jsonS = "{\"search\":\""+query+"\"}";
                Log.d("-jsnresponse search---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/search_product/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*if(newText != null && !newText.isEmpty()){
                    List<String> lstfound = new ArrayList<String>();
                    for (String item:productnameA) {
                        if (item.contains(newText))
                            lstfound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstfound);
                    lstviewP.setAdapter(adapter);
                }
                else{
                    ArrayAdapter adapter = new ArrayAdapter(Products.this,android.R.layout.simple_expandable_list_item_1, lstSource);
                    lstviewP.setAdapter(adapter);
                }*/

                Log.d( " onQueryTextChange", " -- " + newText);
                return true;
            }
        });

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getProducts();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProduct.this, AddUserReq.class);
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

    public void add(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectProduct.this);
        builder.setTitle("Selected Product : "+ pName);
//        builder.setMessage("Enter Quantity : ");
//        input = new EditText(SelectProduct.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//        LinearLayout.LayoutParams.MATCH_PARENT,
//        LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        builder.setView(input);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                String jsonS = "{\"quantity\":\""+input.getText().toString()+"\",\"productid\":\""+pID+"\",\"requisitionid\":\""+urID+"\"}";
//                Log.d("-jsnresponse add---",""+jsonS);
//                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_requisitiondetails/";
//                try {
//                    JSONObject rmdt = null;
//
//                    rmdt = new JSONObject(jsonS);
//
//                    //JSONSenderVolley(urlrs,jsonS);
//                    JSONSenderVolley1(urlrs, rmdt);
//                } catch (JSONException e) {
//
//                }
//                Log.d("-jsnresponse enter---", "" + jsonS);
                        Intent intent = new Intent(SelectProduct.this, AddUserReq.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        // get pid n send to AdduserReq// 296
        // pid , pname from ProductAdapter

    }
    public void getProducts() {

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
                            List<String> productdescriptionL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            List<String> standardcostL = new ArrayList<String>();
                            List<String> reorderlevelL = new ArrayList<String>();
                            List<String> reorderqtyL = new ArrayList<String>();
                            List<String> producttypeL = new ArrayList<String>();

                            new_array1 = responseJSON.getJSONArray("product_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String productdescriptionS = new_array1.getJSONObject(i).getString("productdescription");
                                String statusId_idS = new_array1.getJSONObject(i).getString("status");
                                String standardcostS = new_array1.getJSONObject(i).getString("standardcost");
                                String reorderlevelS = new_array1.getJSONObject(i).getString("reorderlevel");
                                String reorderqtyS = new_array1.getJSONObject(i).getString("reorderqty");
                                String producttypeS = new_array1.getJSONObject(i).getString("producttype");

                                //"standardcost": "10", "reorderlevel": "10", "reorderqty": "10", "producttype": "raw material"
                                idL.add(idS);
                                productnameL.add(productnameS);
                                productdescriptionL.add(productdescriptionS);
                                statusId_idL.add(statusId_idS);
                                standardcostL.add(standardcostS);
                                reorderlevelL.add(reorderlevelS);
                                reorderqtyL.add(reorderqtyS);
                                producttypeL.add(producttypeS);
                            }

                            idA = new String[idL.size()];
                            productnameA = new String[productnameL.size()];
                            productdescriptionA = new String[productnameL.size()];
                            statusId_idA = new String[productnameL.size()];
                            standardcostA = new String[productnameL.size()];
                            reorderlevelA = new String[productnameL.size()];
                            reorderqtyA = new String[productnameL.size()];
                            producttypeA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                idA[l] = idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                productdescriptionA[l] = productdescriptionL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                standardcostA[l] = standardcostL.get(l);
                                reorderlevelA[l] = reorderlevelL.get(l);
                                reorderqtyA[l] = reorderqtyL.get(l);
                                producttypeA[l] = producttypeL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("productname ", productnameA[l]);
                                Log.d("productdescription ", productdescriptionA[l]);
                                Log.d("statusId_id ", statusId_idA[l]);
                            }

                            SearchPrdAdapter reqAdapter = new SearchPrdAdapter(SelectProduct.this,idA, productnameA, productdescriptionA, statusId_idA, standardcostA, reorderlevelA, reorderqtyA, producttypeA);
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
                            List<String> productdescriptionL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("product_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String productdescriptionS = new_array1.getJSONObject(i).getString("productdescription");
                                String statusId_idS = new_array1.getJSONObject(i).getString("status");
                                idL.add(idS);
                                productnameL.add(productnameS);
                                productdescriptionL.add(productdescriptionS);
                                statusId_idL.add(statusId_idS);
                            }

                            idA = new String[idL.size()];
                            productnameA = new String[productnameL.size()];
                            productdescriptionA = new String[productnameL.size()];
                            statusId_idA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                idA[l] = idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                productdescriptionA[l] = productdescriptionL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("productname ", productnameA[l]);
                                Log.d("productdescription ", productdescriptionA[l]);
                                Log.d("statusId_id ", statusId_idA[l]);
                            }

                            SearchPrdAdapter reqAdapter = new SearchPrdAdapter(SelectProduct.this,idA, productnameA, productdescriptionA, statusId_idA, standardcostA, reorderlevelA, reorderqtyA, producttypeA);
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

    public void JSONSenderVolley1(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            AlertDialog.Builder builder = new AlertDialog.Builder(SelectProduct.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(SelectProduct.this, AddUserReq.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

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
}
