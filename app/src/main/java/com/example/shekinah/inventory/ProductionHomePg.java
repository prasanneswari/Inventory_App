package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.port;

public class ProductionHomePg extends AppCompatActivity {

    String unitName;
    String[] jtsproductidA, jtsproductnameA;
    EditText Name;
    ListView listRoles;
    private ProgressDialog dialog_progress ;
    Button GA, Alarm, seven_switch, Curtain, Back, RequestNew, addNew;
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_home_pg);

        dialog_progress = new ProgressDialog(ProductionHomePg.this);
        Back = (Button)findViewById(R.id.Back);
        listRoles = (ListView)findViewById(R.id.listRoles);
        addNew = (Button)findViewById(R.id.addNew);
        RequestNew = (Button)findViewById(R.id.requestNew);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        httpRequest1();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder newUnit = new AlertDialog.Builder(ProductionHomePg.this);
                newUnit.setTitle("New Production Unit");
                newUnit.setMessage("Enter Name: ");

                Name = new EditText(ProductionHomePg.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                Name.setLayoutParams(lp);
                newUnit.setView(Name);
                newUnit.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = Name.getText().toString();
                        Log.d("", "searchTxt: "+ name);
                        String jsonS = "{\"productname\":\""+name+"\"}";
                        Log.d("-jsnresponse search---",""+jsonS);
                        String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_jtsproductname/";
                        try {
                            JSONObject rmdt = null;

                            rmdt = new JSONObject(jsonS);
                            addName(urlrs, rmdt);
                        } catch (JSONException e) {

                        }
                        Log.d("-jsnresponse enter---", "" + jsonS);
                    }
                });

                AlertDialog alertSearch = newUnit.create();
                alertSearch.show();
            }
        });

        RequestNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductionHomePg.this, UserMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void httpRequest1() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_jtsproducts/";
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
                            List<String> jtsproductidL = new ArrayList<String>();
                            List<String> jtsproductnameL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("get_jtsproducts");
                            Log.d( " Array", " : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String jtsproductidS = new_array1.getJSONObject(i).getString("jtsproductid");
                                String jtsproductnameS = new_array1.getJSONObject(i).getString("jtsproductname");
                                jtsproductidL.add(jtsproductidS);
                                jtsproductnameL.add(jtsproductnameS);
                            }

                            jtsproductidA = new String[jtsproductidL.size()];
                            jtsproductnameA = new String[jtsproductnameL.size()];

                            for (int l = 0; l < jtsproductidL.size(); l++) {

                                jtsproductidA[l] = jtsproductidL.get(l);
                                jtsproductnameA[l] = jtsproductnameL.get(l);
                                Log.d("jtsproductidA ", jtsproductidA[l]);
                                Log.d("locationName ", jtsproductnameA[l]);
                            }

                            ProductionUnitsAdapter reqAdapter = new ProductionUnitsAdapter(ProductionHomePg.this,jtsproductidA,jtsproductnameA);
                            listRoles.setAdapter(reqAdapter);
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
    public void addName(String url, final JSONObject json)
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

                            final AlertDialog.Builder builder = new AlertDialog.Builder(ProductionHomePg.this);
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

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
