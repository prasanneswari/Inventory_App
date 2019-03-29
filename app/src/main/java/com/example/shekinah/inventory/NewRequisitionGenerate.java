package com.example.shekinah.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.UserMainAdapter.reqDt;
import static com.example.shekinah.inventory.UserMain.dueDateES;
import static com.example.shekinah.inventory.UserMainAdapter.reqStat;
import static com.example.shekinah.inventory.UserMainAdapter.urDueDate;
import static com.example.shekinah.inventory.UserMainAdapter.urID;

public class NewRequisitionGenerate extends AppCompatActivity {

    AddUserReq ObjAddUserReq ;
    static String dateS, requisitiondate, statusNew, reqid;
    String jsonS, userid, requisitionidS, url_get_new, jsonS1;
    static TextView dueDateT; ;
    JSONObject jObj_get_new = null;
    private ProgressDialog dialog_progress ;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_requisition_generate);
        Button GenerateDt = (Button) findViewById(R.id.generateDt);
        dueDateT = (TextView) findViewById(R.id.dueDateE);
        dialog_progress = new ProgressDialog(NewRequisitionGenerate.this);

        dueDateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v);
            }
        });

        GenerateDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                dateS= dueDateT.getText().toString();
                Log.d( " dateS", " -- " + dateS);
                urDueDate = dateS;
                Log.d( " eid", " -- " + eid);

                jsonS = "{\"duedate\":\""+dateS+"\",\"userid\":\""+eid+"\"}";
                Log.d("-add_requisition ---",""+jsonS);

                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_requisition/";
                try {

                    //JSONObject jEmp = null;
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    //jEmp = new JSONObject(jsonS);

                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("after ", "add_requisition " + jsonS);
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
            dueDateT.setText(year + "-" + (month + 1) + "-" + day);
            dateS = dueDateT.toString();
            Log.d("setting date"," ..  "+ dateS);
        }
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
                        dialog_progress.hide();
                        Log.d("add_requisition", "response ---"+String.valueOf(response));
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            //Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            if (errorCode.contentEquals("1")){
//////////////////////
                                Log.d( " eid", " -- " + eid);
                                dialog_progress.setMessage("connecting ...");
                                dialog_progress.show();
                                jsonS1 = "{\"userid\":\""+eid+"\"}";
                                Log.d("get_newrequisition","----- "+jsonS1);

                                url_get_new ="http://"+domain_name+":"+port+"/InventoryApp/get_newrequisition/";

                                try {

                                    jObj_get_new = new JSONObject(jsonS1);

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            JSONSenderVolleyGetNewReq(url_get_new, jObj_get_new);
                                        }
                                    }, 5000);

                                } catch (JSONException e) {

                                }
                                Log.d("after", "get_newrequisition " + jsonS1);

                            }

                            Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(NewRequisitionGenerate.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


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
                Log.d("my test error NRG-----","Tabitha: " + String.valueOf(error));
                final AlertDialog.Builder builder = new AlertDialog.Builder(NewRequisitionGenerate.this);
                builder.setTitle("Process Failed");
                builder.setMessage(String.valueOf(error));
                dialog_progress.hide();
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

    public void JSONSenderVolleyGetNewReq(String url, final JSONObject json)
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
                        //Log.d(" ", response.toString());
                        Log.d("get_newrequisition", "response --"+response.toString());
                        dialog_progress.hide();
                        JSONObject responseJSON = null;
                        try {
                            responseJSON = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            new_array1 = responseJSON.getJSONArray("new_requisition");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                requisitionidS = new_array1.getJSONObject(i).getString("requisitionid");
                                String idS = new_array1.getJSONObject(i).getString("userid");
                                String duedateS = new_array1.getJSONObject(i).getString("duedate");
                                requisitiondate = new_array1.getJSONObject(i).getString("requisitiondate");
                                Log.d( " requisitionidS", " response ->  " + requisitionidS);
                                Log.d( " idS", " response ->  " + idS);
                                Log.d( " requisitiondate", " response ->  " + requisitiondate);
                            }
                            statusNew = "Not Issued";
                            reqid = requisitionidS;
                            reqStat = statusNew;
                            Intent intent = new Intent(NewRequisitionGenerate.this, AddUserReq.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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
                final AlertDialog.Builder builderF = new AlertDialog.Builder(NewRequisitionGenerate.this);
                builderF.setTitle("Info");
                builderF.setMessage(String.valueOf(error));

                builderF.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialogF = builderF.create();
                dialogF.show();
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
            }
        }) {
            /**
             * Passing some request headers
             * */
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
    }

}



