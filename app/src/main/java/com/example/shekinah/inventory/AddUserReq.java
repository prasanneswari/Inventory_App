package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.port;
//import static com.example.shekinah.inventory.UserMain.categoryS;
import static com.example.shekinah.inventory.UserMainAdapter.urID;
import static com.example.shekinah.inventory.NewRequisitionGenerate.reqid;
import static com.example.shekinah.inventory.MainActivity.txtemailS;

public class AddUserReq extends AppCompatActivity {

    String[] idA, pidA, productnameA, productdescriptionA, statusId_idA, requisitionidA, idUA, qtyRA, qtyIA, qtyStatusA, productnameRA;
    String jsonS, urlrs, pid1, prdQS="", prdSelectedS, jtsproductid = "", jtsproductquantity = "";
    ListView ListPrd, PrdDetails;
    EditText qtyP, prdQ;
    Button Add1;
    EditText searchTxt;
    CheckBox check;
    Boolean itemSelected = false;
    JSONObject rmdt = null;
    TextView prdSelected;

    static TextView ReqDueDt,ReqId, ReqDt, ReqStatus;
    com.android.volley.RequestQueue sch_RequestQueue;
    MaterialSearchView searchView;
    static String gtime;
    List<String> requisitionidL = new ArrayList<String>();
    List<String> qtyRL = new ArrayList<String>();
    List<String> qtyIL = new ArrayList<String>();
    List<String> qtyStatusL = new ArrayList<String>();
    List<String> idL = new ArrayList<String>();
    List<String> pidL = new ArrayList<String>();
    List<String> pL = new ArrayList<String>();
    List<String> qL = new ArrayList<String>();
    List<String> productnameL = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_req);

        get_product();
        PrdDetails = (ListView)findViewById(R.id.prdDetails);
        ListPrd = (ListView)findViewById(R.id.listPrd);
        Button Add = (Button) findViewById(R.id.addURq);
        ReqDueDt = (TextView)findViewById(R.id.reqDueDt);
        check = (CheckBox)findViewById(R.id.checkUserReq);
        TextView ReqUser = (TextView)findViewById(R.id.reqUserName);  //addP
        Button addP = (Button) findViewById(R.id.addP);
        Button back = (Button) findViewById(R.id.back);
        prdQ = (EditText) findViewById(R.id.prdQ);
        prdQ.setInputType(InputType.TYPE_CLASS_NUMBER);

        ReqDueDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v);
            }
        });

        ReqUser.setText(txtemailS);

        prdSelected = (TextView) findViewById(R.id.prdSelected);

        back.setOnClickListener(new View.OnClickListener() {
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
                Log.d("pid1.charAt(0)", "in add component--"+ pid1.substring(0,1)); //C
                if (prdQS.contentEquals("") || prdSelectedS.contentEquals("Select Product...")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddUserReq.this);
                    builder.setTitle("Incorrect Entry");
                    builder.setMessage("Please enter a valid Product and Quantity.");
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

                    if(pid1.substring(0,1).contentEquals("C")){
                        jsonS = "{\"jtsproductid\":\""+pid1+"\"}";
                        jtsproductid = pid1;
                        Log.d("-jsnresponse add---",""+jsonS);
                        jtsproductquantity = prdQS;
                        Log.d("-jtsproductquantity---",""+prdQS);

                    }
                    String requisitionidS = urID;
                    Log.d("-prdQS  ---", "" + prdQS);
                    //String qtyRS = prdQS;
                    String qtyIS = "0";
                    String qtyStatusS = "Not Issued";
                    String productnameS = prdSelectedS;
                    //requisitionidL.add(requisitionidS);
                    qtyRL.add(prdQS);
                    //pidL.add(pID);
                    pidL.add(pid1);
                    if(!pid1.substring(0,1).contentEquals("C")) {
                        pL.add(pid1);
                        qL.add(prdQS);
                    }
                    qtyIL.add(qtyIS);
                    qtyStatusL.add(qtyStatusS);
                    productnameL.add(productnameS);

                    //requisitionidA = new String[requisitionidL.size()];
                    qtyRA = new String[qtyRL.size()];
                    pidA = new String[pidL.size()];
                    qtyIA = new String[qtyIL.size()];
                    qtyStatusA = new String[qtyStatusL.size()];
                    productnameRA = new String[productnameL.size()];

                    for (int l = 0; l < productnameL.size(); l++) {

                        //requisitionidA[l] = requisitionidL.get(l);
                        qtyRA[l] = qtyRL.get(l);
                        qtyIA[l] = qtyIL.get(l);
                        pidA[l] = pidL.get(l);
                        qtyStatusA[l] = qtyStatusL.get(l);
                        productnameRA[l] = productnameL.get(l);
                        //Log.d("", "requisitionidA " + requisitionidA[l]);
                        Log.d("qtyRA ", qtyRA[l]);
                        Log.d("qtyIA ", qtyIA[l]);
                        Log.d("qtyStatusA ", qtyStatusA[l]);
                        Log.d("productnameRA ", productnameRA[l]);
                    }

                    userReqAdapter reqAdapter = new userReqAdapter(AddUserReq.this, qtyRA, qtyIA, qtyStatusA, productnameRA);
                    PrdDetails.setAdapter(reqAdapter);
                    prdQ.setText("");
                    prdSelected.setText("Select Product...");
                }
            }
        });
        prdSelected.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                get_product();

                final AlertDialog.Builder builder2 = new AlertDialog.Builder(AddUserReq.this);
                builder2.setTitle("Choose Product...");

                builder2.setItems(productnameA, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        prdSelected.setText(productnameA[item]);
                        pid1 = idA[item];
                        Log.d("productnameA[item] ", productnameA[item]);
                        Log.d("pid1.charAt(0)", pid1.substring(0,1)); //C
                        Log.d("idA[item] ", pid1);
                        itemSelected = false;
                    }
                });
                builder2.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder search = new AlertDialog.Builder(AddUserReq.this);
                        search.setTitle("Product Search...");
                        search.setMessage("Enter Product: ");

                        searchTxt = new EditText(AddUserReq.this);
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

            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check.isChecked()) {

                    check.clearFocus();
                    Log.d(" urID", " -- " + urID);
                    //Log.d( " qtyPS", " -- " + qtyPS);
                    String p1 = "", q1 = "", p = "[", q = "[";
                    for (int qty = 0; qty < pL.size(); qty++) {
                        p1 = pL.get(qty);
                        p = p + p1 + ",";
                        Log.d(" ", "pid: " + p);
                    }
                    if(!p.substring(0,1).contentEquals("[") || p.substring(p.length()-1).contentEquals(",")){
                        p = p.substring(0, p.length() - 1);
                    }
                    if(p.substring(p.length()-1).contentEquals("[")){
                      p = p + "0";
                    }
                    p = p + "]";
                    Log.d(" all pids", " -- " + p);

                    for (int qty = 0; qty < qL.size(); qty++) {
                        q1 = qL.get(qty);
                        q = q + q1 + ",";
                        Log.d(" ", "q: " + q);
                    }
                    if(!q.substring(0,1).contentEquals("[") || q.substring(q.length()-1).contentEquals(",")){
                        q = q.substring(0, q.length() - 1);
                    }
                    q = q + "]";
                    Log.d(" all qs", " -- " + q);

                    if(!ReqDueDt.getText().toString().contentEquals("Choose Date")) {

                        //{"userid":"9","duedate":"2018-03-31","quantity":"[15,20]","productid":"[4,5]","jtsproductid":"C1","jtsproductquantity":"15","returnid":"1"}
                        jsonS = "{\"userid\":\"" + eid + "\",\"duedate\":\"" + ReqDueDt.getText().toString() + "\",\"quantity\":\"" + q + "\",\"productid\":\"" + p + "\",\"returnid\":\"" + "1" + "\",\"jtsproductid\":\"" + jtsproductid + "\",\"jtsproductquantity\":\"" + jtsproductquantity + "\"}";
                        Log.d("-jsnresponse add---", "jsonS: " + jsonS);
                        String urlrs = "http://" + domain_name + ":" + port + "/InventoryApp/add_requisitiondetails/";
                        Log.d("-jsnresponse add---", "urlrs: " + urlrs);
                        try {
                            JSONObject rmdt = null;

                            rmdt = new JSONObject(jsonS);

                            addReqVolly(urlrs, rmdt);
                        } catch (JSONException e) {

                        }
                        Log.d("-jsnresponse enter---", "" + jsonS);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddUserReq.this);
                        builder.setTitle("Incorrect Entry");
                        builder.setMessage("Please choose Due date for your request.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddUserReq.this);
                    builder.setTitle("Verified your request? ");
                    builder.setMessage("Please check the checkbox if your are ready to submit. ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
// Do something with the date chosen by the user
            //dueDateE.setText(day + "/" + (month + 1) + "/" + year);

            gtime=year + "-" + (month + 1) + "-" + day;
            ReqDueDt.setText(year + "-" + (month + 1) + "-" + day);
            Log.d("setting date"," ..  "+ ReqDueDt);

        }
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
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_products/";
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
                            List<String> productnamecL = new ArrayList<String>();
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
                                productnamecL.add(productnameS);


                                String requisitionidS = urID;
                                Log.d("-prdQS  ---", "" + prdQS);
                                String qtyRS = prdQS;
                                String qtyIS = "0";
                                String qtyStatusS = "Not Issued";
                                requisitionidL.add(requisitionidS);
                                qtyRL.add(basequantityS);
                                //pidL.add(pID);
                                pidL.add(pid1);
                                qtyIL.add(qtyIS);
                                qtyStatusL.add(qtyStatusS);
                                productnameL.add(productnameS);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        requisitionidA = new String[requisitionidL.size()];
                        qtyRA = new String[qtyRL.size()];
                        pidA = new String[pidL.size()];
                        qtyIA = new String[qtyIL.size()];
                        qtyStatusA = new String[qtyStatusL.size()];
                        productnameRA = new String[productnameL.size()];

                        for (int l = 0; l < requisitionidL.size(); l++) {

                            requisitionidA[l] = requisitionidL.get(l);
                            qtyRA[l] = qtyRL.get(l);
                            qtyIA[l] = qtyIL.get(l);
                            pidA[l] = pidL.get(l);
                            qtyStatusA[l] = qtyStatusL.get(l);
                            productnameRA[l] = productnameL.get(l);
                            Log.d("", "requisitionidA " + requisitionidA[l]);
                            Log.d("qtyRA ", qtyRA[l]);
                            Log.d("qtyIA ", qtyIA[l]);
                            Log.d("qtyStatusA ", qtyStatusA[l]);
                            Log.d("productnameRA ", productnameRA[l]);
                        }

                        userReqAdapter reqAdapter = new userReqAdapter(AddUserReq.this, qtyRA, qtyIA, qtyStatusA, productnameRA);
                        PrdDetails.setAdapter(reqAdapter);
                        prdQ.setText("");
                        prdSelected.setText("Select Product...");
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
                            productdescriptionA = new String[productnameL.size()];
                            statusId_idA = new String[productnameL.size()];

                            for (int l = 0; l < productnameL.size(); l++) {

                                idA[l] = idL.get(l);
                                productnameA[l] = productnameL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("productname ", productnameA[l]);
                            }

                            final AlertDialog.Builder builder3 = new AlertDialog.Builder(AddUserReq.this);
                            builder3.setTitle("Choose Product...");

                            builder3.setItems(productnameA, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    // Do something with the selection
                                    prdSelected.setText(productnameA[item]);
                                    pid1 = idA[item];
                                    Log.d("productnameA[item].. ", productnameA[item]);

                                    Log.d("idA[item] ", pid1);
                                    itemSelected = false;
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

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddUserReq.this);
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


    public void addReqVolly(String url, final JSONObject json)
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
                                qtyRL.clear();
                            }
                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddUserReq.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(AddUserReq.this, UserMain.class);
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

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
