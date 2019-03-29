package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class Store_Forecast extends AppCompatActivity {
    static TextView dueDateE;
    ListView listU;
    private ProgressDialog dialog_progress ;

    String[] qtyA,projectnameA,forecastidA, productnameA, duedateA, quantitygivenA, quantityrequiredA,requesteddateA, statusA,qtyA2,projectA2, duedateA2, statusId_idA2, productnameA2,qtygivenA2;
    String dueDateES, jsonS;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__forecast);
        dialog_progress = new ProgressDialog(Store_Forecast.this);
        listU = (ListView)findViewById(R.id.UserReq);
        //Button Add = (Button) findViewById(R.id.addUR);
        Button History = (Button) findViewById(R.id.history);
        Button Refresh = (Button) findViewById(R.id.refresh);
        Button Cancel = (Button) findViewById(R.id.cancelB);
        //dueDateE = (TextView) findViewById(R.id.dueDateE);
        getTodayReq();
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

        // ListView setOnItemClickListener function apply here.

        listU.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(MainActivity.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
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
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_forecasthistory/ ";
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
                            List<String> forecastidL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            List<String> duedateL = new ArrayList<String>();
                            List<String> quantitygivenL = new ArrayList<String>();
                            List<String> quantityrequiredL = new ArrayList<String>();
                            List<String> requesteddateL = new ArrayList<String>();
                            List<String> statusL = new ArrayList<String>();
                            List<String> qtyL = new ArrayList<String>();
                            List<String> ProjectnameL = new ArrayList<String>();

                            new_array1 = responseJSON.getJSONArray("get_forecast_history");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String forecastidS = new_array1.getJSONObject(i).getString("forecastid");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                String duedateS = new_array1.getJSONObject(i).getString("duedate");
                                String quantitygivenS = new_array1.getJSONObject(i).getString("quantitygiven");
                                String quantityrequiredS = new_array1.getJSONObject(i).getString("quantityrequired");
                                String requesteddateS = new_array1.getJSONObject(i).getString("requesteddate");// reqDate
                                String statusS = new_array1.getJSONObject(i).getString("status");
                                String qtyS = new_array1.getJSONObject(i).getString("quantityrequired");
                                String projectnameS = new_array1.getJSONObject(i).getString("projectname");

                                forecastidL.add(forecastidS);
                                productnameL.add(productnameS);
                                duedateL.add(duedateS);
                                quantitygivenL.add(quantitygivenS);
                                quantityrequiredL.add(quantityrequiredS);
                                requesteddateL.add(requesteddateS);
                                statusL.add(statusS);
                                qtyL.add(qtyS);
                                ProjectnameL.add(projectnameS);


                            }

                            forecastidA = new String[forecastidL.size()];
                            productnameA = new String[productnameL.size()];
                            duedateA = new String[duedateL.size()];
                            quantitygivenA = new String[quantitygivenL.size()];
                            quantityrequiredA = new String[quantityrequiredL.size()];
                            requesteddateA = new String[requesteddateL.size()];
                            statusA = new String[statusL.size()];
                            qtyA = new String[qtyL.size()];
                            projectnameA = new String[ProjectnameL.size()];

                            for (int l = 0; l < ProjectnameL.size(); l++) {


                                forecastidA[l] = forecastidL.get(l);
                                productnameA[l] = productnameL.get(l);
                                duedateA[l] = duedateL.get(l);
                                quantitygivenA[l] = quantitygivenL.get(l);
                                quantityrequiredA[l] = quantityrequiredL.get(l);
                                requesteddateA[l] = requesteddateL.get(l);
                                statusA[l] = statusL.get(l);
                                qtyA[l] = qtyL.get(l);
                                projectnameA[l] = ProjectnameL.get(l);

                                Log.d("forecastidA ", forecastidA[l]);
                                Log.d("productnameA ", productnameA[l]);
                                Log.d("Req DueDate ", duedateA[l]);
                                Log.d("quantitygivenA ", quantitygivenA[l]);
                                Log.d("quantityrequiredA ", quantityrequiredA[l]);
                                Log.d("requesteddateA ", requesteddateA[l]);
                                Log.d("statusA ", statusA[l]);
                                Log.d("qtyA ", qtyA[l]);
                                Log.d("projectnameA ", projectnameA[l]);

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
                            uniqueIdA.addAll(Arrays.asList(forecastidA));

                            Log.d("uniqueIdA ", uniqueIdA.toString());

                            String[] uniqueIdA2 = uniqueIdA.toArray(new String[uniqueIdA.size()]);

                            duedateA2 = new String[uniqueIdA.size()];
                            statusId_idA2 = new String[uniqueIdA.size()];
                            productnameA2 = new String[uniqueIdA.size()];
                            qtygivenA2=new String[uniqueIdA.size()];
                            qtyA2=new String[uniqueIdA.size()];
                            projectA2=new String[uniqueIdA.size()];

                            for (int k1 = 0; k1 < uniqueIdA2.length; k1++) {
                                for (int k2 = 0; k2 < forecastidA.length; k2++) {
                                    if(uniqueIdA2[k1].contentEquals(forecastidA[k2])){
                                        duedateA2[k1] = duedateL.get(k2);
                                        //statusId_idA2[k1] = statusL.get(k2);
                                        productnameA2[k1] = productnameL.get(k2);
                                        qtygivenA2[k1] = quantitygivenL.get(k2);
                                        qtyA2[k1] = qtyL.get(k2);
                                        projectA2[k1] = ProjectnameL.get(k2);

                                        Log.d("Req uniqueIdA2 ", uniqueIdA2[k1]);
                                        Log.d("Req productnameA2 ", productnameA2[k1]);
                                        Log.d("Req duedateA2 ", duedateA2[k1]);
                                        Log.d("statusId_idA2 ", statusId_idA2[k1]);
                                        Log.d("qtygivenA2 ", qtygivenA2[k1]);
                                        Log.d("qtyA2 ", qtyA2[k1]);
                                        Log.d("projectA2 ", projectA2[k1]);

                                    }
                                }
                            }

//                            Integer[] numbers = {1, 1, 2, 1, 3, 4, 5};
//                            Set<Integer> uniqKeys = new TreeSet<Integer>();
//                            uniqKeys.addAll(Arrays.asList(numbers));
//                            System.out.println("uniqKeys: " + uniqKeys);

                            //ConsumableAdapter reqAdapter = new ConsumableAdapter(ConsumableIssues.this,idA, productnameA, duedateA, statusId_idA);
                            Adapter_Store_Forecast reqAdapter = new Adapter_Store_Forecast(Store_Forecast.this,uniqueIdA2,projectA2, productnameA2, duedateA2,qtyA2,qtygivenA2);
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
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_forecasts/";
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
                            List<String> GforecastidL = new ArrayList<String>();
                            List<String> GproductnameL = new ArrayList<String>();
                            List<String> GduedateL = new ArrayList<String>();
                            List<String> GquantitygivenL = new ArrayList<String>();
                            List<String> GquantityrequiredL = new ArrayList<String>();
                            List<String> GrequesteddateL = new ArrayList<String>();
                            List<String> GstatusL = new ArrayList<String>();
                            List<String> GqtyL = new ArrayList<String>();
                            List<String> GProjectnameL = new ArrayList<String>();

                            new_array1 = responseJSON.getJSONArray("get_forecast");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String GforecastidS = new_array1.getJSONObject(i).getString("forecastid");
                                String GproductnameS = new_array1.getJSONObject(i).getString("productname");
                                String GduedateS = new_array1.getJSONObject(i).getString("duedate");
                                String GquantitygivenS = new_array1.getJSONObject(i).getString("quantitygiven");
                                String GquantityrequiredS = new_array1.getJSONObject(i).getString("quantityrequired");
                                String GrequesteddateS = new_array1.getJSONObject(i).getString("requesteddate");// reqDate
                                String GstatusS = new_array1.getJSONObject(i).getString("status");
                                String GqtyS = new_array1.getJSONObject(i).getString("quantityrequired");
                                String GprojectnameS = new_array1.getJSONObject(i).getString("projectname");

                                GforecastidL.add(GforecastidS);
                                GproductnameL.add(GproductnameS);
                                GduedateL.add(GduedateS);
                                GquantitygivenL.add(GquantitygivenS);
                                GquantityrequiredL.add(GquantityrequiredS);
                                GrequesteddateL.add(GrequesteddateS);
                                GstatusL.add(GstatusS);
                                GqtyL.add(GqtyS);
                                GProjectnameL.add(GprojectnameS);

                            }

                            forecastidA = new String[GforecastidL.size()];
                            productnameA = new String[GproductnameL.size()];
                            duedateA = new String[GduedateL.size()];
                            quantitygivenA = new String[GquantitygivenL.size()];
                            quantityrequiredA = new String[GquantityrequiredL.size()];
                            requesteddateA = new String[GrequesteddateL.size()];
                            statusA = new String[GstatusL.size()];
                            qtyA = new String[GqtyL.size()];
                            projectnameA = new String[GProjectnameL.size()];


                            for (int l = 0; l < GProjectnameL.size(); l++) {


                                forecastidA[l] = GforecastidL.get(l);
                                productnameA[l] = GproductnameL.get(l);
                                duedateA[l] = GduedateL.get(l);
                                quantitygivenA[l] = GquantitygivenL.get(l);
                                quantityrequiredA[l] = GquantityrequiredL.get(l);
                                requesteddateA[l] = GrequesteddateL.get(l);
                                statusA[l] = GstatusL.get(l);
                                qtyA[l] = GqtyL.get(l);
                                projectnameA[l] = GProjectnameL.get(l);


                                Log.d("forecastidA ", forecastidA[l]);
                                Log.d("productnameA ", productnameA[l]);
                                Log.d("Req DueDate ", duedateA[l]);
                                Log.d("quantitygivenA ", quantitygivenA[l]);
                                Log.d("quantityrequiredA ", quantityrequiredA[l]);
                                Log.d("requesteddateA ", requesteddateA[l]);
                                Log.d("statusA ", statusA[l]);
                                Log.d("qtyA ", qtyA[l]);
                                Log.d("projectnameA ", projectnameA[l]);

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

                            Adapter_Store_Forecast reqAdapter = new Adapter_Store_Forecast(Store_Forecast.this,forecastidA,projectnameA, productnameA, duedateA,qtyA ,quantitygivenA);
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