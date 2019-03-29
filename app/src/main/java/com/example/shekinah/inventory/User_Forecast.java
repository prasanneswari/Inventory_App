package com.example.shekinah.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

import static com.example.shekinah.inventory.JobRolesMenu.eid;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;
import static com.example.shekinah.inventory.ReqDetailsAdapter.jsonS;

public class User_Forecast extends AppCompatActivity {
    ListView user_forecast_lst;
    String[] Forecastid = {};
    String[] Name = {};
    String[] Duedate = {};
    String[] Qtygiven = {};
    String[] Qtyreq = {};
    String[] Reqdate = {};
    String[] Status = {};
    public static String forecastS,nameS,duedateS,statusS,qtygivenS,qtyreqS,reqdateS;
    Button newbtn,backbtn;


    AlertDialog.Builder builder;
    AlertDialog alertdialog;
    com.android.volley.RequestQueue sch_RequestQueue;
    private ProgressDialog dialog_progress ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__forecast);
        user_forecast_lst=(ListView)findViewById(R.id.user_forecastlist);
        newbtn=(Button)findViewById(R.id.btnnew);
        backbtn=(Button)findViewById(R.id.btnback);
        Button Refresh = (Button) findViewById(R.id.refreshUR);
        dialog_progress = new ProgressDialog(User_Forecast.this);


        user_forecast_post();
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Forecast.this, Forecasting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonS = "{\"id\":\""+eid+"\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/get_userforecasts/";
                try {
                    JSONObject rmdt = null;
                    dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();
                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });
    }
    public void user_forecast_post(){
        // String pur_prds = "{\"username\":\"admin\",\"password\":\"admin\"}";
        String user_forecast = "{\"userid\":\"3\"}";
        Log.d(" user_forecast111", "---" + user_forecast);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String pur_url = "http://"+domain_name+":"+port+"/InventoryApp/get_userforecasts/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(user_forecast);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + user_forecast);
        JSONSenderVolley(pur_url, lstrmdt);
    }
    public  void forecast_request(JSONObject responseJSON){

        try {
            //Log.d( " Array", " response ->  " + response);
            JSONArray new_array1;
            List<String> forecastL = new ArrayList<String>();
            List<String> nameL = new ArrayList<String>();
            List<String> duedateL = new ArrayList<String>();
            List<String> qtygivenL = new ArrayList<String>();
            List<String> qtyreqL = new ArrayList<String>();
            List<String> reqdateL = new ArrayList<String>();
            List<String> statusL = new ArrayList<String>();

            new_array1 = responseJSON.getJSONArray("get_userforecast");
            Log.d( " Array", " : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                 forecastS = new_array1.getJSONObject(i).getString("forecastid");
                 nameS = new_array1.getJSONObject(i).getString("productname");
                 duedateS = new_array1.getJSONObject(i).getString("duedate");
                 qtygivenS= new_array1.getJSONObject(i).getString("quantitygiven");
                 qtyreqS = new_array1.getJSONObject(i).getString("quantityrequired");
                 reqdateS = new_array1.getJSONObject(i).getString("requesteddate");
                 statusS = new_array1.getJSONObject(i).getString("status" + "");

                forecastL.add(forecastS);
                nameL.add(nameS);
                duedateL.add(duedateS);
                qtygivenL.add(qtygivenS);
                qtyreqL.add(qtyreqS);
                reqdateL.add(reqdateS);
                statusL.add(statusS);


            }
            Forecastid = new String[forecastL.size()];
            Name = new String[nameL.size()];
            Duedate = new String[duedateL.size()];
            Qtygiven = new String[qtygivenL.size()];
            Qtyreq = new String[qtyreqL.size()];
            Reqdate = new String[reqdateL.size()];
            Status = new String[statusL.size()];


            for (int l = 0; l < statusL.size(); l++) {
                Forecastid[l] = forecastL.get(l);
                Name[l] = nameL.get(l);
                Duedate[l] = duedateL.get(l);
                Qtygiven[l] = qtygivenL.get(l);
                Qtyreq[l] = qtyreqL.get(l);
                Reqdate[l] = reqdateL.get(l);
                Status[l] = statusL.get(l);


                Log.d("Forecastid ", Forecastid[l]);
                Log.d("Name ", Name[l]);
                Log.d("Duedate ", Duedate[l]);
                Log.d("Qtygiven ", Qtygiven[l]);
                Log.d("Qtyreq ", Qtyreq[l]);
                Log.d("Reqdate ", Reqdate[l]);
                Log.d("Status ", Status[l]);


            }

            Adapter_userforecast reqAdapter = new Adapter_userforecast(User_Forecast.this,Forecastid,Name, Duedate,Qtygiven,Qtyreq,Reqdate, Status);
            user_forecast_lst.setAdapter(reqAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void JSONSenderVolley(String pur_url, final JSONObject json)
    {
        Log.d("update_received_post-", "---"+pur_url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                pur_url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----pur_url values-----", "---"+response.toString());

                        forecast_request(response);
                        //get_room_ssid(response);
                        //startedScanner();
                        //get_spinner_response(response);
                        //  edit_response(response);
//                        try {
//                            login_code = response.getInt("error_code");
//                            String er_discp=response.getString("error_desc");
//
//                            String[] separated = er_discp.split("=");
//                            if(login_code==0){
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//                            }else
//                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
//
//
//                        } catch (JSONException e) {
//
//                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" ", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();

            }

        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
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
