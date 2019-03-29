package com.example.shekinah.inventory;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.R.attr.handle;

public class  MainActivity extends AppCompatActivity {

    //static String domain_name = "dev.jtsha.in" ;   //external
    //static String domain_name = "192.168.173.7";   //internal
    //static String domain_name = "115.98.91.33"; //new server
    //static String port = "20111";// new port
    //static String domain_name = "192.168.173.10"; //new server internal
   /* static String domain_name = "10.105.0.7";  //cloud_3
    static String port = "20105"; //cloud_3 test*/
    //static String domain_name = "/cld003.jts-prod.in";
    //static String port = "20105"; //cloud_3 test
    static String domain_name = "/cld003.jts-prod.in";  //cloud_3
    static String port = "20106"; //cloud_3 live
    static String txtemailS, txtpassS, eid1, sessionid;
    Intent intent;
    Dialog popup;
    String[] idA,eNameA,usermenuA,eRoleA, prdA, qtyA;
    String jsonS, jES, JobRoleS, url1;
    Spinner JobRole;
    public ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    JSONObject rmdt,jEmp = null;
    String[] UserTypeArray = {"Purchase Admin", "User", "storeincharge"};
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog_progress = new ProgressDialog(MainActivity.this);
        builderLoading = new AlertDialog.Builder(MainActivity.this);
        final EditText txtemailE = (EditText) findViewById(R.id.txtemail);
        final EditText txtpassE = (EditText) findViewById(R.id.txtpass);
        Button Btnlogin = (Button) findViewById(R.id.btnsignin);
        TextView btnregT = (TextView) findViewById(R.id.btnreg);

        btnregT.setPaintFlags(btnregT.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
/*        ArrayAdapter<String> userTypeArray= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, UserTypeArray);
        userTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        JobRole.setAdapter(userTypeArray);*/

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtemailS = txtemailE.getText().toString();
                txtpassS= txtpassE.getText().toString();
//                JobRoleS = JobRole.getSelectedItem().toString();

                popup = new Dialog(MainActivity.this);
//      Dialog popup = new Dialog(this,R.style.Theme_Dialog);
                popup.requestWindowFeature(Window.FEATURE_NO_TITLE);

                popup.setContentView(R.layout.activity_splash_screen);
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();

//                AlertDialog dialog = builderLoading.create();
//                builderLoading.setTitle("Loading...");
//                dialog.show();
                jsonS = "{\"username\":\""+txtemailS+"\",\"password\":\""+txtpassS+"\"}";
                jES = "{\"username\":\""+txtemailS+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/login/";
                url1 ="http://"+domain_name+":"+port+"/InventoryApp/get_user/";

                try {

                    rmdt = new JSONObject(jsonS);
                    jEmp = new JSONObject(jES);

                    //JSONSenderVolley1(urlrs,jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        btnregT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////////
        try {
            TrustManager[] victimizedManager = new TrustManager[]{

                    new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {

                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];

                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, victimizedManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
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

                        Log.d("----getUserVolley-----", "---"+response.toString());
                        try {
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> eNameL = new ArrayList<String>();
                            List<String> jobroleL = new ArrayList<String>();
                            List<String> usermenuL = new ArrayList<String>();
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
                            }

                            idA = new String[idL.size()];
                            eNameA = new String[eNameL.size()];
                            //eRoleA = new String[jobroleL.size()];
                            usermenuA = new String[usermenuL.size()];

                            for (int l = 0; l < eNameL.size(); l++) {

                                idA[l] = idL.get(l);
                                eid1 = idA[l];
                                eNameA[l] = eNameL.get(l);
                                //eRoleA[l] = jobroleL.get(l);
                                usermenuA[l] = usermenuL.get(l);
                                Log.d("id ", idA[l]);
                                Log.d("eName ", eNameA[l]);
                                //Log.d("eRole ", eRoleA[l]);
                                Log.d("usermenuA ", usermenuA[l]);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" Error---------", "Tabitha: " + String.valueOf(error));
                Log.d("my test error-----","Tabitha: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "String.valueOf(error) ", Toast.LENGTH_LONG).show();
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

    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        dialog_progress.hide();
        //Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG).show();
        //R
        // equestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());

                        /*builderLoading.setNegativeButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        });*/
                        Log.d("----JSONSenderVolley--", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            sessionid = response.getString("sessionid");
                            //Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            if (dialog_progress.isShowing()) {
                                dialog_progress.dismiss();

                            }
                            if (errorCode.contentEquals("1")){

                                //getUserVolley(url1, jEmp);
                                intent = new Intent(MainActivity.this, JobRolesMenu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                            }
                            else {
                                //Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();builderLoading
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);

                                //builderLoading.setMessage(errorDesc);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Info");
                                builder.setMessage(errorDesc);

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                        dialogInterface1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
}
