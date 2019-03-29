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
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.MainActivity.txtemailS;

public class JobRolesMenu extends AppCompatActivity {

    static  int val=0;
    String url1, jES;
    static String eid;
    String[] idA,eNameA,usermenuA,forecastA,storeforecastA;
    String forecastS="FORECAST";
    String storeforecast="STORE FORECAST";
    JSONObject jEmp = null;
    ListView listRoles;
    Button logout, refresh;
    public ProgressDialog dialog_progress ;
    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_roles_menu);
        listRoles = (ListView)findViewById(R.id.listRoles);
        refresh = (Button) findViewById(R.id.refresh);
        logout = (Button) findViewById(R.id.btn_logout);
        dialog_progress = new ProgressDialog(JobRolesMenu.this);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        jES = "{\"username\":\""+txtemailS+"\"}";
        url1 ="http://"+domain_name+":"+port+"/InventoryApp/get_user/";
        try {
            jEmp = new JSONObject(jES);
            getUserVolley(url1, jEmp);
        } catch (JSONException e) {

        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                jES = "{\"username\":\""+txtemailS+"\"}";
                url1 ="http://"+domain_name+":"+port+"/InventoryApp/get_user/";
                try {
                    jEmp = new JSONObject(jES);
                    getUserVolley(url1, jEmp);
                } catch (JSONException e) {

                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(JobRolesMenu.this);
                builder.setTitle("Info");
                builder.setMessage("Do you want to logout ??");
                builder.setPositiveButton("Take me away!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(JobRolesMenu.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
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

    }

    public void getUserVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG).show();
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        dialog_progress.hide();
                        Log.d("----getUserVolley-----", "---"+response.toString());
                        try {
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> eNameL = new ArrayList<String>();
                            //List<String> jobroleL = new ArrayList<String>();
                            List<String> usermenuL = new ArrayList<String>();
                            List<String> forecastL = new ArrayList<String>();
                            List<String> storeforecastL = new ArrayList<String>();

                            new_array1 = response.getJSONArray("user_details");
                            Log.d(" Array", " : " + new_array1);

                            for (int i = 0, count = new_array1.length(); i < count; i++){
                                String idS = new_array1.getJSONObject(i).getString("employeeid");
                                String usernameS = new_array1.getJSONObject(i).getString("username");
                                //String jobroleS = new_array1.getJSONObject(i).getString("jobrole");
                                String usermenuS = new_array1.getJSONObject(i).getString("usermenus");
                                idL.add(idS);
                                eNameL.add(usernameS);
                                //jobroleL.add(jobroleS);
                                usermenuL.add(usermenuS);
                                forecastL.add(forecastS);
                                storeforecastL.add(storeforecast);
                            }

                            idA = new String[idL.size()];
                            eNameA = new String[eNameL.size()];
                            //eRoleA = new String[jobroleL.size()];
                            usermenuA = new String[usermenuL.size()];
                            forecastA = new String[forecastL.size()];
                            storeforecastA = new String[storeforecastL.size()];


                            for (int l = 0; l < eNameL.size(); l++) {

                                idA[l] = idL.get(l);
                                eid = idA[l];
                                eNameA[l] = eNameL.get(l);
                                //eRoleA[l] = jobroleL.get(l);
                                usermenuA[l] = usermenuL.get(l);
                                forecastA[l] = forecastL.get(l);
                                storeforecastA[l] = storeforecastL.get(l);

                                Log.d("id ", idA[l]);
                                Log.d("eName ", eNameA[l]);
                                //Log.d("eRole ", eRoleA[l]);
                                Log.d("usermenuA ", usermenuA[l]);
                                Log.d("forecastA ", forecastA[l]);
                                Log.d("storeforecastA ", storeforecastA[l]);

                            }

                            MyJobRoleAdapter reqAdapter = new MyJobRoleAdapter(JobRolesMenu.this,usermenuA,forecastA,storeforecastA);
                            listRoles.setAdapter(reqAdapter);


                            //Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();
                            //LIntent intentog.d("usernameS","" + idS+"--" + usernameS);

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
}
