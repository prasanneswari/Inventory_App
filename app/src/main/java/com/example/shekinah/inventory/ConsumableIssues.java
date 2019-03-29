package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//import static com.example.shekinah.inventory.ConsumableAdapter.urID;
import static com.example.shekinah.inventory.JobRolesMenu.val;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.port;

public class ConsumableIssues extends AppCompatActivity {

    static TextView dueDateE;
    ListView listU;
    private ProgressDialog dialog_progress ;

    String[] idA, usernameA, duedateA, requisitiondateA, statusId_idA, productidA, productnameA, duedateA2, statusId_idA2, productnameA2;
    String dueDateES, jsonS;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog_progress = new ProgressDialog(ConsumableIssues.this);
        setContentView(R.layout.activity_consumable_issues);
        listU = (ListView)findViewById(R.id.UserReq);
        Button Add = (Button) findViewById(R.id.addUR);
        Button History = (Button) findViewById(R.id.history);
        Button Refresh = (Button) findViewById(R.id.refresh);
        Button Cancel = (Button) findViewById(R.id.cancelB);
        dueDateE = (TextView) findViewById(R.id.dueDateE);
        getTodayReq();

        Button returns = (Button)findViewById(R.id.returns);
        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = 1;
            }
        });
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTodayReq();
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStoreReq();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

    public void getStoreReq() {
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_storerequisition/";
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
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> productidL = new ArrayList<String>();
                            List<String> usernameL = new ArrayList<String>();
                            List<String> duedateL = new ArrayList<String>();
                            List<String> requisitiondateL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("store_requisition");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("requisitionid");
                                String usernameS = new_array1.getJSONObject(i).getString("username");
                                String duedateS = new_array1.getJSONObject(i).getString("duedate");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String productidS = new_array1.getJSONObject(i).getString("productid");
                                String requisitiondateS = new_array1.getJSONObject(i).getString("requisitiondate");// reqDate
                                String statusId_idS = new_array1.getJSONObject(i).getString("status");
                                idL.add(idS);
                                usernameL.add(usernameS);
                                duedateL.add(duedateS);
                                requisitiondateL.add(requisitiondateS);
                                statusId_idL.add(statusId_idS);
                                productnameL.add(productnameS);
                                productidL.add(productidS);
                            }

                            idA = new String[idL.size()];
                            usernameA = new String[usernameL.size()];
                            duedateA = new String[duedateL.size()];
                            requisitiondateA = new String[requisitiondateL.size()];
                            statusId_idA = new String[statusId_idL.size()];
                            productidA = new String[productidL.size()];
                            productnameA = new String[productnameL.size()];

                            for (int l = 0; l < usernameL.size(); l++) {


                                idA[l] = idL.get(l);
                                usernameA[l] = usernameL.get(l);
                                duedateA[l] = duedateL.get(l);
                                requisitiondateA[l] = requisitiondateL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                productidA[l] = productidL.get(l);
                                productnameA[l] = productnameL.get(l);
                                Log.d("Req Id ", idA[l]);
                                Log.d("Req Name ", usernameA[l]);
                                Log.d("Req DueDate ", duedateA[l]);
                                Log.d("Date Generated ", requisitiondateA[l]);
                                Log.d("statusId_idA ", statusId_idA[l]);
                                Log.d("productidA ", productidA[l]);
//                                for (int j = l + 1 ; j < idA.length; j++) {
//                                    if (idA[l].equals(idA[j])) {
//
//                                    }
//                                    else {
//                                        uReqIdL1.add(idA[l]);
//                                    }
//                                }
                            }

//                            for (int l1 = 0; l1 < uReqIdL1.size(); l1++) {
//                                Log.d("uReqIdL1 ", uReqIdL1.get(l1));
//                            }

                            Set<String> uniqueIdA = new TreeSet<String>();
                            uniqueIdA.addAll(Arrays.asList(idA));

                            Log.d("uniqueIdA ", uniqueIdA.toString());

                            String[] uniqueIdA2 = uniqueIdA.toArray(new String[uniqueIdA.size()]);

                            duedateA2 = new String[uniqueIdA.size()];
                            statusId_idA2 = new String[uniqueIdA.size()];
                            productnameA2 = new String[uniqueIdA.size()];

                            for (int k1 = 0; k1 < uniqueIdA2.length; k1++) {
                                for (int k2 = 0; k2 < idA.length; k2++) {
                                    if(uniqueIdA2[k1].contentEquals(idA[k2])){
                                        duedateA2[k1] = duedateL.get(k2);
                                        statusId_idA2[k1] = statusId_idL.get(k2);
                                        productnameA2[k1] = productnameL.get(k2);
                                        Log.d("Req uniqueIdA2 ", uniqueIdA2[k1]);
                                        Log.d("Req productnameA2 ", productnameA2[k1]);
                                        Log.d("Req duedateA2 ", duedateA2[k1]);
                                        Log.d("statusId_idA2 ", statusId_idA2[k1]);
                                    }
                                }
                            }

//                            Integer[] numbers = {1, 1, 2, 1, 3, 4, 5};
//                            Set<Integer> uniqKeys = new TreeSet<Integer>();
//                            uniqKeys.addAll(Arrays.asList(numbers));
//                            System.out.println("uniqKeys: " + uniqKeys);

                            //ConsumableAdapter reqAdapter = new ConsumableAdapter(ConsumableIssues.this,idA, productnameA, duedateA, statusId_idA);
                            ConsumableAdapter reqAdapter = new ConsumableAdapter(ConsumableIssues.this,uniqueIdA2, productnameA2, duedateA2, statusId_idA2);
                            listU.setAdapter(reqAdapter);

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

    public void getTodayReq() {

        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_todayrequisition/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

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
                            List<String> uReqIdL1 = new ArrayList<String>();
                            List<String> usernameL = new ArrayList<String>();
                            List<String> duedateL = new ArrayList<String>();
                            List<String> requisitiondateL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("today_requisition");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("requisitionid");
                                String usernameS = new_array1.getJSONObject(i).getString("username");
                                String duedateS = new_array1.getJSONObject(i).getString("duedate");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String useridS = new_array1.getJSONObject(i).getString("userid");
                                String requisitiondateS = new_array1.getJSONObject(i).getString("requisitiondate");// reqDate
                                String statusId_idS = new_array1.getJSONObject(i).getString("status");
                                idL.add(idS);
                                usernameL.add(usernameS);
                                duedateL.add(duedateS);
                                requisitiondateL.add(requisitiondateS);
                                statusId_idL.add(statusId_idS);
                                productnameL.add(productnameS);
                            }

                            idA = new String[idL.size()];
                            usernameA = new String[usernameL.size()];
                            duedateA = new String[duedateL.size()];
                            requisitiondateA = new String[requisitiondateL.size()];
                            statusId_idA = new String[statusId_idL.size()];
                            productnameA = new String[productnameL.size()];

                            for (int l = 0; l < usernameL.size(); l++) {


                                idA[l] = idL.get(l);
                                usernameA[l] = usernameL.get(l);
                                duedateA[l] = duedateL.get(l);
                                requisitiondateA[l] = requisitiondateL.get(l);
                                statusId_idA[l] = statusId_idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                Log.d("Req Id ", idA[l]);
                                Log.d("Req Name ", usernameA[l]);
                                Log.d("Req DueDate ", duedateA[l]);
                                Log.d("Date Generated ", requisitiondateA[l]);
                                Log.d("statusId_idA ", statusId_idA[l]);
//                                for (int j = l + 1 ; j < idA.length; j++) {
//                                    if (idA[l].equals(idA[j])) {
//
//                                    }
//                                    else {
//                                        uReqIdL1.add(idA[l]);
//                                    }
//                                }
                            }

//                            for (int l1 = 0; l1 < uReqIdL1.size(); l1++) {
//                                Log.d("uReqIdL1 ", uReqIdL1.get(l1));
//                            }

                          /*  Set<String> uniqueIdA = new TreeSet<String>();
                            uniqueIdA.addAll(Arrays.asList(idA));

                            Log.d("uniqueIdA ", uniqueIdA.toString());

                            String[] uniqueIdA2 = uniqueIdA.toArray(new String[uniqueIdA.size()]);

                            for (int l1 = 0; l1 < uReqIdL1.size(); l1++) {
                                if (idA[l1] == uniqueIdA2[l1]){

                                }
                            }*/


//                            Integer[] numbers = {1, 1, 2, 1, 3, 4, 5};
//                            Set<Integer> uniqKeys = new TreeSet<Integer>();
//                            uniqKeys.addAll(Arrays.asList(numbers));
//                            System.out.println("uniqKeys: " + uniqKeys);

                            ConsumableAdapter reqAdapter = new ConsumableAdapter(ConsumableIssues.this,idA, productnameA, duedateA, statusId_idA);
                            listU.setAdapter(reqAdapter);

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
}
