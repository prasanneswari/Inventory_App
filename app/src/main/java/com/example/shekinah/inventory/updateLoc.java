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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.LocationAdapter.lID;
import static com.example.shekinah.inventory.LocationAdapter.lName;
import static com.example.shekinah.inventory.LocationAdapter.lAddr;
import static com.example.shekinah.inventory.LocationAdapter.lStatus;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.ProductAdapter.pID;

public class updateLoc extends AppCompatActivity {

    String[] sIdA, statusNameA, idA, locationNameA, locationAddressA, statusId_idA;
    Spinner statusULE;
    String sid, urlrs, statusId_idS;
    Integer sidI;
    JSONObject rmdt;
    EditText nameULE,addrULE;
    public ProgressDialog dialog_progress ;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loc);

        nameULE = (EditText) findViewById(R.id.nameUL);
        addrULE = (EditText) findViewById(R.id.addrUL);
        Button DelLocB = (Button) findViewById(R.id.DLoc);
        statusULE = (Spinner) findViewById(R.id.statusUL);

        final Button Update = (Button) findViewById(R.id.updateLoc);
        Button Cancel = (Button) findViewById(R.id.cancelUL);
        dialog_progress = new ProgressDialog(updateLoc.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        getStatus();
        /*nameULE.setText(lName);
        addrULE.setText(lAddr);*/
        //statusULE.setSelected();
        Log.d("!....", "before http-func");
        //httpRequest1();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress = new ProgressDialog(updateLoc.this);
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                String id = lID;
                String name = nameULE.getText().toString();
                String addr = addrULE.getText().toString();
                String statuSs = statusULE.getSelectedItem().toString();

                for (int l = 0; l < sIdA.length; l++) {

                    if( statusNameA[l] == statuSs){
                        sid = sIdA[l];
                    }
                    Log.d("!....", statusNameA[l]);
                }
                String jsonS = "{\"id\":\""+id+"\",\"locationname\":\""+name+"\",\"statusid\":\""+sid+"\",\"locationaddress\":\""+addr+"\"}";
                Log.d("-jsnresponse update---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_location/";
                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        DelLocB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog_progress = new ProgressDialog(updateLoc.this);
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                String jsonDel = "{\"id\":\""+ lID+"\"}";
                Log.d("----jsnresponse delete-", "" + jsonDel);

                //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
                urlrs ="http://"+domain_name+":"+port+"/InventoryApp/del_location/";
                // String urlrs= "https://jtsha.in/service/validate_web";

                // try {
                try {
                    rmdt = new JSONObject(jsonDel.toString());
                } catch (JSONException e) {

                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(updateLoc.this);
                builder.setTitle("Info");
                builder.setMessage("Do you want to Delete ??");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Log.d("----jsnresponse enterd3333-----", "---" + water_status);
                        JSONSenderVolleyD(urlrs, rmdt);
                        //param_delete.remove(param_delete[i]);
                        Intent intent = new Intent(updateLoc.this, Locations.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(updateLoc.this, Locations.class);
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

                            final AlertDialog.Builder builder = new AlertDialog.Builder(updateLoc.this);
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
                            List<String> statusNameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("status_details");
                            Log.d( " Array", " requisition : " + new_array1);
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
                            ArrayAdapter<String> statusA= new ArrayAdapter<String>(updateLoc.this,android.R.layout.simple_spinner_item, statusNameA);
                            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusULE.setAdapter(statusA);

                            nameULE.setText(lName);
                            addrULE.setText(lAddr);

                            Log.d(" obtained statusId ","from adapter -- " + lStatus);
                            for (int n = 0; n < statusNameL.size(); n++) {
                                if (statusNameA[n].contentEquals(lStatus)) {
                                    sidI = n;
                                    statusULE.setSelection(n);
                                    Log.d(" statusId ", statusNameA[n]);
                                }
                                else {
                                    Log.d(" statusId else", statusNameA[n]);
                                }
                            }
                            Log.d("hello1 ","exiting status fn.......");
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

    public void JSONSenderVolleyD(String url, final JSONObject json)
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
                        dialog_progress.hide();
                        Log.d("---------", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","-------" + errorCode+"--");
                            /*if (errorCode.contentEquals("0")){

                                Log.d(" Location Deleted ","Successfully... :)");
                                Intent intent = new Intent(updateLoc.this, Locations.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("error-----"," Location NOT deleted Successfully!!!!! ");
                                 }*/
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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
