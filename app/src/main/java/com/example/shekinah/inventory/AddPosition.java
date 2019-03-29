package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class AddPosition extends AppCompatActivity {

    EditText nameNLE, addrNLE;
    String nameNLS1, nameNLS2, nameNLS3, nameNLS4, addrNLS,statuSs, sid, jsonS, WHSPs, whid;
    Spinner statusSp, WHSP;
    Boolean s=false, w=false;
    String[] WHIdA, WHNameA, sIdA, statusNameA;
    com.android.volley.RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        final EditText nameNLE1 = (EditText) findViewById(R.id.nameNL1);
        final EditText nameNLE2 = (EditText) findViewById(R.id.nameNL2);
        final EditText nameNLE3 = (EditText) findViewById(R.id.nameNL3);
        final EditText nameNLE4 = (EditText) findViewById(R.id.nameNL4);
        WHSP = (Spinner) findViewById(R.id.addrNL);
        Button AddLocB = (Button) findViewById(R.id.addLoc);
        final Button Cancel = (Button) findViewById(R.id.addNewC);
        statusSp = (Spinner)findViewById(R.id.statusL);
        dialog_progress = new ProgressDialog(AddPosition.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        getWH();
        getStatus();

        AddLocB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                nameNLS1 = nameNLE1.getText().toString();
                nameNLS2 = nameNLE2.getText().toString();
                nameNLS3 = nameNLE3.getText().toString();
                nameNLS4 = nameNLE4.getText().toString();

                if(nameNLS1.isEmpty()){
                    nameNLS1 = "X";
                }
                if(nameNLS2.isEmpty()){
                    nameNLS2 = "X";
                }
                if(nameNLS3.isEmpty()){
                    nameNLS3 = "X";
                }
                if(nameNLS4.isEmpty()){
                    nameNLS4 = "X";
                }

                addrNLS= WHSP.getSelectedItem().toString();

                WHSPs = WHSP.getSelectedItem().toString();

                for (int l = 0; l < WHIdA.length; l++) {

                    if( WHNameA[l] == WHSPs){
                        whid = WHIdA[l];
                    }
                    Log.d("!....", WHNameA[l]);
                }

                statuSs = statusSp.getSelectedItem().toString();

                for (int l = 0; l < statusNameA.length; l++) {

                    if( statusNameA[l] == statuSs){
                        sid = sIdA[l];
                    }
                    Log.d("!....", statusNameA[l]);
                }

                jsonS = "{\"position\":\"S"+nameNLS1+"R"+nameNLS2+"B"+nameNLS3+"P"+nameNLS4+"\",\"statusid\":\""+sid+"\",\"warehouseid\":\""+whid+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_position/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPosition.this, Positions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
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
                        Log.d(" ", response.toString());
                        dialog_progress.hide();
                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddPosition.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                    Intent intent = new Intent(AddPosition.this, Positions.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
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


    public void getWH() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_warehouse/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        w = true;
                        if( s==true && w==true){
                            Log.d("dialog_progress1 :", "--------");
                            dialog_progress.hide();
                        }
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            //requisition = responseJSON.getString("requisition");
                            //Log.d( " String", " requisition : " + requisition);
                            JSONArray new_array1;
                            List<String> idWHL = new ArrayList<String>();
                            List<String> WHNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("warehouse_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idWHS = new_array1.getJSONObject(i).getString("id");
                                String WHNameS = new_array1.getJSONObject(i).getString("warehousename");
                                idWHL.add(idWHS);
                                WHNameL.add(WHNameS);
                            }

                            WHIdA = new String[idWHL.size()];
                            WHNameA = new String[WHNameL.size()];

                            for (int l = 0; l < WHNameL.size(); l++) {

                                WHIdA[l] = idWHL.get(l);
                                WHNameA[l] = WHNameL.get(l);
                                Log.d("wHId ", WHIdA[l]);
                                Log.d("WHName ", WHNameA[l]);
                            }
                            ArrayAdapter<String> WHA= new ArrayAdapter<String>(AddPosition.this,android.R.layout.simple_spinner_item, WHNameA);
                            WHA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            WHSP.setAdapter(WHA);

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

    public void getStatus() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_status/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("hello :", response.toString());
                        s = true;
                        Log.d("s :", " "+s);
                        if( s==true && w==true){
                            Log.d("dialog_progress2 :", "--------");
                            dialog_progress.hide();
                        }
                        JSONObject responseJSON = null;
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.d( " Array", " response ->  " + response);
                            //requisition = responseJSON.getString("requisition");
                            //Log.d( " String", " requisition : " + requisition);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> statusNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("status_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String statusNameS = new_array1.getJSONObject(i).getString("statusname");
                                idL.add(idS);
                                statusNameL.add(statusNameS);
                            }

                            sIdA = new String[idL.size()];
                            statusNameA = new String[statusNameL.size()];

                            for (int l = 0; l < statusNameL.size(); l++) {

                                sIdA[l] = idL.get(l);
                                statusNameA[l] = statusNameL.get(l);
                                Log.d("sId ", sIdA[l]);
                                Log.d("statusName ", statusNameA[l]);
                            }
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(AddPosition.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusSp.setAdapter(statusA);

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

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
