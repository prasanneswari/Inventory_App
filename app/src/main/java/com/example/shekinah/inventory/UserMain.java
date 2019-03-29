package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.JobRolesMenu.val;
import static com.example.shekinah.inventory.MainActivity.domain_name;
//import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.UserMainAdapter.urID;

public class UserMain extends AppCompatActivity {

    static TextView dueDateE, category;
    ListView listU;
    private ProgressDialog dialog_progress ;
    static TextView input;
    String[] idA, usernameA, duedateA, statusId_idA, cidA,cnameA, uReqIdA, dueDtA, reqDtA, UNameA, pCatA, rstatA, requisitionidA, idUA, productnameRA;
    String jsonS, urlrs2;
    String cID;
    String cid;
    static boolean requesting = false;
    static String dateS;
    Button Add1;
    static String dueDateES;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        listU = (ListView)findViewById(R.id.UserReq);
        Button Add = (Button) findViewById(R.id.addUR);
        Button Refresh = (Button) findViewById(R.id.refreshUR);
        Button Cancel = (Button) findViewById(R.id.cancelB);
        dueDateE = (TextView) findViewById(R.id.dueDateE);
        dialog_progress = new ProgressDialog(UserMain.this);

        Button returns = (Button)findViewById(R.id.returns);
        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val = 2;
            }
        });

        listU.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adpterView, View view, int position,
                                    long id) {
                for (int i = 0; i < listU.getChildCount(); i++) {
                    if(position == i ){
                        listU.getChildAt(i).setBackgroundColor(Color.BLUE);
                    }else{
                        listU.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        input = new TextView(UserMain.this);
        //category = (TextView) findViewById(R.id.category);
        //getCategory();
        //getUserReq();
        jsonS = "{\"id\":\""+eid+"\"}";
        Log.d("-jsnresponse add---",""+jsonS);
        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_requisition/";
        try {
            JSONObject rmdt = null;

            dialog_progress.setMessage("connecting ...");
            dialog_progress.show();
            rmdt = new JSONObject(jsonS);
            getVollyUserReqs(urlrs, rmdt);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter---", "" + jsonS);

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonS = "{\"id\":\""+eid+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_requisition/";
                try {
                    JSONObject rmdt = null;
                    dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();
                    rmdt = new JSONObject(jsonS);
                    getVollyUserReqs(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserMain.this, AddUserReq.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                Log.d("in add request date", " ");
//                create(); //requesting set to true

                /*if ( requesting == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserMain.this);
                    builder.setTitle("New Requisition Created... ");

//                final TextView input = new TextView(UserMain.this);

                    builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Log.d("in add send" ," date");
                            sendDate(); //requesting set to false

                        }
                    });

                    *//*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });*//*
                    AlertDialog alert = builder.create();
                    alert.show();
                }*/
            }
        });

        /*Add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dueDateES= dueDateE.getText().toString();
                //categoryS = category.getText().toString();
                Log.d( " dueDateES", " -- " + dueDateES);
                Log.d( " eid", " -- " + eid);
                //Log.d( " categoryS", " -- " + categoryS);
*//*
                for (int l = 0; l < cidA.length; l++) {

                    if( cnameA[l] == categoryS){
                        cid = cidA[l];
                    }
                    Log.d("!....", cnameA[l]);
                }*//*

                jsonS = "{\"duedate\":\""+dueDateES+"\",\"userid\":\""+eid+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);

                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_requisition/";
                try {

                    JSONObject jEmp = null;
                    //JSONObject rmdt;


                    JSONObject rmdt = new JSONObject(jsonS);
                    //jEmp = new JSONObject(jsonS);

                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });
*/

       /* category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserMain.this);
                builder.setTitle("Select Product...");
                builder.setItems(cnameA, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        category.setText(cnameA[item]);
                        cID = cidA[item];
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });*/

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

       /* dueDateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });*/
    }

    public void create(){

        requesting = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMain.this);
            builder.setTitle("Creating New Requisition... ");
            builder.setMessage("Select Due Date : ");

//                final TextView input = new TextView(UserMain.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);
            builder.setNeutralButton("Choose Date", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DialogFragment newFragment = new UserMain.DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
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

    }
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new UserMain.DatePickerFragment();
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

            dueDateE.setText(year + "-" + (month + 1) + "-" + day);
            dateS = dueDateE.toString();
            Log.d("setting date"," ..  "+ dateS);
            //sendDate(dateS);
            UserMain send = new UserMain();
            send.sendDate();
        }
    }

    public void sendDate(){
        requesting = false;
        dueDateES= dueDateE.getText().toString();
        //categoryS = category.getText().toString();
        Log.d( " dueDateES", " -- " + dueDateES);
        Log.d( " eid", " -- " + eid);

        jsonS = "{\"duedate\":\""+dueDateES+"\",\"userid\":\""+eid+"\"}";
        Log.d("-jsnresponse add---",""+jsonS);

        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_requisition/";
        try {

            JSONObject jEmp = null;
            JSONObject rmdt = null;

            dialog_progress.setMessage("connecting ...");
            dialog_progress.show();
            rmdt = new JSONObject(jsonS);
            //jEmp = new JSONObject(jsonS);

            JSONSenderVolley(urlrs, rmdt);
        } catch (JSONException e) {

        }
        Log.d("-jsnresponse enter---", "" + jsonS);

    }
    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG).show();
        //RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----UserMain-----", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            //Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            if (dialog_progress.isShowing()) {
                                dialog_progress.dismiss();

                            }
                            if (errorCode.contentEquals("1")){

                                jsonS = "{\"id\":\""+eid+"\"}";
                                Log.d("-jsnresponse add---",""+jsonS);
                                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_requisition/";
                                try {
                                    JSONObject rmdt = null;

                                    rmdt = new JSONObject(jsonS);
                                    getVollyUserReqs(urlrs, rmdt);
                                } catch (JSONException e) {

                                }
                                Log.d("-jsnresponse enter---", "" + jsonS);
//                                Intent intent = new Intent(UserMain.this, AddUserReq.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);

                            }

                                Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(UserMain.this);
                                builder.setTitle("Info");
                                builder.setMessage(errorDesc);

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                //dialog.show();

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

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("userid", txtemailS);
//                params.put("password", txtpassS);
//                params.put("jobrole", JobRoleS);
//                return params;
//            }
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

    public void getCategory() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_category/";
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
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> usernameL = new ArrayList<String>();
                            List<String> duedateL = new ArrayList<String>();
                            List<String> statusId_idL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("get_category");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String usernameS = new_array1.getJSONObject(i).getString("category");
                                idL.add(idS);
                                usernameL.add(usernameS);
                            }

                            cidA = new String[idL.size()];
                            cnameA = new String[usernameL.size()];

                            for (int l = 0; l < usernameL.size(); l++) {

                                cidA[l] = idL.get(l);
                                cnameA[l] = usernameL.get(l);
                                Log.d("Req Id ", cidA[l]);
                                Log.d("Req Name ", cnameA[l]);
                            }
//                            ReqPrdSearchAdapter reqAdapter = new ReqPrdSearchAdapter(UserMain.this, cidA, cnameA);
//                            ListPrd.setAdapter(reqAdapter);

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

    public void getVollyUserReqs(String url, final JSONObject json)
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
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> uReqIdL = new ArrayList<String>();
                            List<String> dueDtL = new ArrayList<String>();
                            List<String> reqDtL = new ArrayList<String>();
                            List<String> UNameL = new ArrayList<String>();
                            List<String> pCatL = new ArrayList<String>();
                            List<String> rstatL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("requisition");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String uReqIdS = new_array1.getJSONObject(i).getString("id");
                                String dueDtS = new_array1.getJSONObject(i).getString("duedate");
                                String reqDtS = new_array1.getJSONObject(i).getString("requisitiondate");
                                String UNameS = new_array1.getJSONObject(i).getString("username");
                                String pCatS = new_array1.getJSONObject(i).getString("materialcategory");
                                String rstatS = new_array1.getJSONObject(i).getString("status");
                                uReqIdL.add(uReqIdS);
                                dueDtL.add(dueDtS);
                                reqDtL.add(reqDtS);
                                UNameL.add(UNameS);
                                pCatL.add(pCatS);
                                rstatL.add(rstatS);

                            }

                            uReqIdA = new String[uReqIdL.size()];
                            dueDtA = new String[dueDtL.size()];
                            reqDtA = new String[reqDtL.size()];
                            UNameA = new String[UNameL.size()];
                            pCatA = new String[pCatL.size()];
                            rstatA = new String[rstatL.size()];

                            for (int l = 0; l < uReqIdL.size(); l++) {

                                uReqIdA[l] = uReqIdL.get(l);
                                dueDtA[l] = dueDtL.get(l);
                                reqDtA[l] = reqDtL.get(l);
                                UNameA[l] = UNameL.get(l);
                                pCatA[l] = pCatL.get(l);
                                rstatA[l] = rstatL.get(l);
                                Log.d("uReqIdA ", uReqIdA[l]);
                                Log.d("dueDtA ", dueDtA[l]);
                                Log.d("reqDtA ", reqDtA[l]);
                                Log.d("UNameA ", UNameA[l]);
                                Log.d("pCatA ", pCatA[l]);
                                Log.d("rstatA ", rstatA[l]);
                            }

                            /*for (int r=0; r<uReqIdL.size(); r++)
                            {
                                jsonS = "{\"id\":\""+uReqIdA[r]+"\"}";
                                Log.d("-jsnresponse add---",""+jsonS);
                                urlrs2 ="http://"+domain_name+":"+port+"/InventoryApp/get_requisitiondetails/";
                                try {
                                    dialog_progress.setMessage("connecting ...");
                                    dialog_progress.show();
                                    JSONObject rmdt2 = null;
                                    rmdt2 = new JSONObject(jsonS);
                                    getVollyUserReqDetails(urlrs2, rmdt2);
                                } catch (JSONException e) {

                                }
                                Log.d("-jsnresponse enter---", "" + jsonS);
                            }
*/

                            UserMainAdapter reqAdapter = new UserMainAdapter(UserMain.this, uReqIdA, UNameA, dueDtA, reqDtA, rstatA);
                            listU.setAdapter(reqAdapter);

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

    public void getVollyUserReqDetails(String url, final JSONObject json)
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
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> requisitionidL = new ArrayList<String>();
                            List<String> idL = new ArrayList<String>();
                            List<String> qtyRL = new ArrayList<String>();
                            List<String> qtyIL = new ArrayList<String>();
                            List<String> newProdL = new ArrayList<String>();
                            List<String> productnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("requisition_details");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String requisitionidS = new_array1.getJSONObject(i).getString("requisitionid");
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String productnameS = new_array1.getJSONObject(i).getString("productname");
                                requisitionidL.add(requisitionidS);
                                idL.add(idS);
                                productnameL.add(productnameS);

                            }

                            requisitionidA = new String[requisitionidL.size()];
                            idUA = new String[idL.size()];
                            productnameRA = new String[productnameL.size()];

                            for (int l = 0; l < requisitionidL.size(); l++) {

                                requisitionidA[l] = requisitionidL.get(l);
                                idUA[l] = idL.get(l);
                                productnameRA[l] = productnameL.get(l);
                                Log.d("requisitionidA ", requisitionidA[l]);
                                Log.d("idUA ", idUA[l]);
                                Log.d("productnameRA ", productnameRA[l]);

                                if( l==0){
                                    newProdL.add(productnameL.get(l));
                                    Log.d("newProdL ", newProdL.get(l));
                                }
                            }
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


}
