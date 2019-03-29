package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;

public class register extends AppCompatActivity {

    String jsonS, jsonS2;
    static List<String> selectedJobRoles = new ArrayList<>();
    String[] idA= {"1","2","3","4","5"};
    com.android.volley.RequestQueue sch_RequestQueue;
    String[] UserTypeArray = {"Purchase Admin", "Production", "Store Incharge", "Implementation"};
    //String[] StatusArray = {"Available", "Not available"};
    String[] GenderArray = {"Male", "Female"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText empIdE = (EditText) findViewById(R.id.empId);
        final EditText empNameE = (EditText) findViewById(R.id.empName);
        final EditText txtemailE = (EditText) findViewById(R.id.txtemail_reg);
        final EditText txtpassE = (EditText) findViewById(R.id.txtpass_reg);
        final ListView JobRole = (ListView) findViewById(R.id.txtjob_role);
        final Spinner txtgenderE = (Spinner) findViewById(R.id.txtgender);
        //final Spinner empStatusE = (Spinner) findViewById(R.id.empstatus);
        //final EditText txtmobileE = (EditText) findViewById(R.id.txtmobile_reg);
        Button Btnreg = (Button) findViewById(R.id.btn_reg);

        /*ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < UserTypeArray.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(UserTypeArray[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        jobRoleAdapter myAdapter = new jobRoleAdapter(register.this, 0, listVOs);
        JobRole.setAdapter(myAdapter);*/

//        jobRoleAdapter myAdapter = new jobRoleAdapter(register.this,idA, UserTypeArray);
//        JobRole.setAdapter(myAdapter);

        selectedJobRoles.clear();
        jobRoleAdapter reqAdapter = new jobRoleAdapter(register.this,idA, UserTypeArray);
        JobRole.setAdapter(reqAdapter);

        /*ArrayAdapter<String> userTypeArray= new ArrayAdapter<String>(register.this,android.R.layout.simple_spinner_item, UserTypeArray);
        userTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        JobRole.setAdapter(userTypeArray);*/

        /*ArrayAdapter<String> statusArray= new ArrayAdapter<String>(register.this,android.R.layout.simple_spinner_item, StatusArray);
        statusArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empStatusE.setAdapter(statusArray);*/

        ArrayAdapter<String> genderArray= new ArrayAdapter<String>(register.this,android.R.layout.simple_spinner_item, GenderArray);
        genderArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtgenderE.setAdapter(genderArray);

        Btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empIdS = empIdE.getText().toString();
                String empNameS = empNameE.getText().toString();
                String emailS = txtemailE.getText().toString();
                String passS = txtpassE.getText().toString();
                String txtgenderS = txtgenderE.getSelectedItem().toString();
                //String JobRoleS = JobRole.getSelectedItem().toString();
                //String empStatusS = empStatusE.getSelectedItem().toString();

                Log.d("-selectedJobRoles ---",""+selectedJobRoles);
                for(int k=0;k<selectedJobRoles.size();k++){

                    jsonS2 = "{\"empId\":\""+empIdS+"\",\"jobrole\":\""+selectedJobRoles.get(k)+" \"}";
                    Log.d("-selectedJobRoles(k)"," ---"+selectedJobRoles.get(k));
                    Log.d("-jsonS2"," ---"+jsonS2);

                }
                /*if (empStatusS == "Available"){
                    empStatusS = "1";
                }
                else empStatusS = "2";*/
                jsonS = "{\"empId\": \""+empIdS+"\", \"empName\": \""+empNameS+"\", \"email\": \""+emailS+"\", \"password\": \"" + passS + "\",  \"gender\": \"" + txtgenderS + "\"}";
                jsonS2 = "{\"empId\":\""+empIdS+"\",\"jobrole\":\""+selectedJobRoles+" \"}";

                //Log.d("-jsnresponse add---",""+jsonS2);
                String urlrs ="http://"+domain_name+":"+port+"/register";
                // String urlrs= "https://jtsha.in/service/validate_web";

                try {
                    JSONObject rmdt = null;

                    rmdt = new JSONObject(jsonS);

                    //JSONSenderVolleyS(urlrs,jsonS);
                    //JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

                final AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setTitle("Information");
                //builder.setMessage("Your Account is Successfully Created.");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Finishing the dialog and removing Activity from stack.
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public class StateVO {
        private String title;
        private boolean selected;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public void JSONSenderVolleyS(String url, final String jsonS)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                //String your_string_json = ; // put your json
                //return your_string_json.getBytes();
                return jsonS.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        //requestQueue.start();
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
                        Log.d("---------", "---"+response.toString());
                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
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
