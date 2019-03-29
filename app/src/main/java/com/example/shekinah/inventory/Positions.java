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

public class Positions extends AppCompatActivity {

    String[] idA = {};
    String[] positionNameA = {};
    String[] positionAddressA = {};
    String[] statusId_idA = {};
    private ProgressDialog dialog_progress ;
    ListView ListPos;
    AlertDialog.Builder builder;
    AlertDialog alertdialog;
    MaterialSearchView searchView;
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positions);
        dialog_progress = new ProgressDialog(Positions.this);
        httpRequest1();
        Button AddPosB = (Button) findViewById(R.id.addPos);
        Button pPrd = (Button) findViewById(R.id.pPrd);
        Button Cancel = (Button) findViewById(R.id.cancelPos);
        Toolbar toolbar = (Toolbar)findViewById(R.id.searchPosB);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
       // ListPos = (ListView)findViewById(R.id.listPos);
        //alertdialog = builder.create();

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
                String jsonS = "{\"positionname\":\""+query+"\"}";
                Log.d("-jsnresponse search---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/search_position/";
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
        AddPosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Positions.this, AddPosition.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        pPrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Positions.this, ProductPositionPg.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Positions.this, JobRolesMenu.class);
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
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_position/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        dialog_progress.hide();
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
                            List<String> positionNameL = new ArrayList<String>();
                            List<String> positionAddressL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("position_details");
                            Log.d( " Array", " position : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("positionid");
                                String positionNameS = new_array1.getJSONObject(i).getString("position");
                                String positionAddressS = new_array1.getJSONObject(i).getString("warehousename");
                                String statusId_idS = new_array1.getJSONObject(i).getString("statusname");
                                idL.add(idS);
                                positionNameL.add(positionNameS);
                                positionAddressL.add(positionAddressS);
                                statusId_idL.add(statusId_idS);
                            }

                            idA = new String[idL.size()];
                            positionNameA = new String[positionNameL.size()];
                            positionAddressA = new String[positionNameL.size()];
                            statusId_idA = new String[positionNameL.size()];

                            for (int l = 0; l < positionNameL.size(); l++) {

                                idA[l] = idL.get(l);
                                positionNameA[l] = positionNameL.get(l);
                                positionAddressA[l] = positionAddressL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("!....positionName ", positionNameA[l]);
                                Log.d("!....positionAddress ", positionAddressA[l]);
                                Log.d("!....statusId_id ", statusId_idA[l]);
                            }
                            ListPos = (ListView)findViewById(R.id.listPos);

                            PositionsAdapter reqAdapter = new PositionsAdapter(Positions.this,idA, positionNameA, positionAddressA, statusId_idA);
                            ListPos.setAdapter(reqAdapter);

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
                            List<String> idL = new ArrayList<String>();
                            List<String> positionNameL = new ArrayList<String>();
                            List<String> positionAddressL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("position_details");
                            Log.d( " Array", " position : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("positionid");
                                String positionNameS = new_array1.getJSONObject(i).getString("position");
                                String positionAddressS = new_array1.getJSONObject(i).getString("warehousename");
                                String statusId_idS = new_array1.getJSONObject(i).getString("statusname");
                                idL.add(idS);
                                positionNameL.add(positionNameS);
                                positionAddressL.add(positionAddressS);
                                statusId_idL.add(statusId_idS);
                            }

                            idA = new String[idL.size()];
                            positionNameA = new String[positionNameL.size()];
                            positionAddressA = new String[positionNameL.size()];
                            statusId_idA = new String[positionNameL.size()];

                            for (int l = 0; l < positionNameL.size(); l++) {

                                idA[l] = idL.get(l);
                                positionNameA[l] = positionNameL.get(l);
                                positionAddressA[l] = positionAddressL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("!....positionName ", positionNameA[l]);
                                Log.d("!....positionAddress ", positionAddressA[l]);
                                Log.d("!....statusId_id ", statusId_idA[l]);
                            }
                            ListPos = (ListView)findViewById(R.id.listPos);

                            PositionsAdapter reqAdapter = new PositionsAdapter(Positions.this,idA, positionNameA, positionAddressA, statusId_idA);
                            ListPos.setAdapter(reqAdapter);

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

/*    public void JSONSenderVolley(String url, final JSONObject json)
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
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(Positions.this);
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
                            httpRequest1();
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
            *//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**
             * Passing some request headers
             * *//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//*
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
    }*/

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
