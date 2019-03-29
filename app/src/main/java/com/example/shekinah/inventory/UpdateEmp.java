package com.example.shekinah.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.logging.LogRecord;

import static com.example.shekinah.inventory.EmployeesAdapter.eID;
import static com.example.shekinah.inventory.MainActivity.domain_name;
import static com.example.shekinah.inventory.MainActivity.port;


public class UpdateEmp extends AppCompatActivity {

    public Handler handler;
    JSONObject responseJSON = null;
    Boolean role = false, loc = false, hr = false, dept = false;

    String [] jIdA, lIdA, dIdA, deptA, deptA1, idA, nameA, userA, headA, mailA, locationA, locationA1, statusNameA, jobRoleA, jobRoleA1, hrIdA, hrA, hrA1, BloodA1, genderA1, DOBA, DOJA, AddrA, Blood = {"O+","A+","B+","AB+","O-","A-","B-","AB-"}, Gender = {"male", "female","Other"};
    Spinner BloodESp, genderESp, roleSp, LocSp, deptSp, HRESp;
    EditText nameANE,userNE, mailE, headE, DOBE, DOJE, AddrE;
    String uid, mid,lid, did, jid, hrid,jsonS, nameANS, userNES, mailS, pswS, deptS, roleS, headS, HRS, LocS, BloodES, veES, genderES, DOBS, DOJS, DOES, AddrS, statusS;
    com.android.volley.RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_emp);

        this.handler = new Handler();

      /* final Handler responseHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                txtView.setText((String) msg.obj);
            }
        };


        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        requestHandler.post(myRunnable);*/

        Log.d("productId obtained", eID);
        Button extraB = (Button) findViewById(R.id.family);
        Button updateEB = (Button) findViewById(R.id.updateEmp);
        Button CancelEB = (Button) findViewById(R.id.cancelUL);
        //alertdialog = builder.create();

        nameANE = (EditText) findViewById(R.id.nameE);
        userNE = (EditText) findViewById(R.id.uNameE);
        mailE = (EditText) findViewById(R.id.mailE);
        //pswE = (EditText) findViewById(R.id.pswE);
        deptSp = (Spinner) findViewById(R.id.deptE);
        roleSp = (Spinner) findViewById(R.id.roleE);
        headE = (EditText) findViewById(R.id.headE);
        HRESp = (Spinner) findViewById(R.id.HRE);
        LocSp = (Spinner) findViewById(R.id.LocE);
        BloodESp = (Spinner)findViewById(R.id.BloodE);
        //veESp = (Spinner)findViewById(R.id.veE);
        genderESp = (Spinner)findViewById(R.id.genderE);
        DOBE = (EditText) findViewById(R.id.DOBE);
        DOJE = (EditText) findViewById(R.id.DOJE);
        AddrE = (EditText) findViewById(R.id.AddrE);

        //alertdialog = builder.create();
        getJobRole();
        getEmpLoc();
        getDept();
        getHR();
        ArrayAdapter<String> bgrpA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, Blood);
        bgrpA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BloodESp.setAdapter(bgrpA);

        ArrayAdapter<String> genderA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, Gender);
        genderA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderESp.setAdapter(genderA);

/*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                r.run();
            }
        }, 3000);
*/

        Log.d("before", "httpReq1");
        //httpRequest1();

        Log.d(" Back to main", " function.");

        updateEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameANS = nameANE.getText().toString();
                userNES = userNE.getText().toString();
                mailS = mailE.getText().toString();
                //pswS = pswE.getText().toString();
                deptS = deptSp.getSelectedItem().toString();
                roleS = roleSp.getSelectedItem().toString();
                headS = headE.getText().toString();
                HRS = HRESp.getSelectedItem().toString();
                LocS = LocSp.getSelectedItem().toString();
                BloodES = BloodESp.getSelectedItem().toString();
                //veES = veESp.getSelectedItem().toString();
                genderES = genderESp.getSelectedItem().toString();
                DOBS = DOBE.getText().toString();
                DOJS = DOJE.getText().toString();
                //DOES= DOEE.getText().toString();
                AddrS = AddrE.getText().toString();

                for (int l = 0; l < jIdA.length; l++) {

                    if( jobRoleA[l] == roleS){
                        jid = jIdA[l];
                    }
                    Log.d("JobRole-- ", jobRoleA[l] +" indexA... "+l);
                }

                for (int m = 0; m < lIdA.length; m++) {

                    if( locationA[m] == LocS){
                        lid = lIdA[m];
                    }
                    Log.d("JobRole-- ", locationA[m]+" indexA... "+m);
                }

                for (int n = 0; n < dIdA.length; n++) {

                    if( deptA[n] == deptS){
                        did = dIdA[n];
                    }
                    Log.d("JobRole-- ", deptA[n]+" indexA... "+n);
                }

                for (int n1 = 0; n1 < hrIdA.length; n1++) {

                    if( hrA[n1] == HRS){
                        hrid = hrIdA[n1];
                    }
                    Log.d("JobRole-- ", hrA[n1] +" indexA... "+n1);
                }

                jsonS = "{\"id\":\"" + eID + "\",\"fullname\":\"" + nameANS + "\",\"username\":\"" + userNES + "\",\"emailid\":\"" + mailS + "\",\"gender\":\"" + genderES + "\",\"bloodgroup\":\"" + BloodES + "\",\"dateofbirth\":\"" + DOBS + "\",\"dateofjoining\":\"" + DOJS + "\",\"department\":\"" + did + "\",\"reportinghead\":\"" + headS + "\",\"reportinghr\":\"" + hrid + "\",\"jobrole\":\"" + jid + "\",\"address\":\"" + AddrS + "\",\"worklocation\":\"" + lid + "\"}";
                //jsonS = "{\"productname\":\"" + nameNPS + "\",\"productdescription\":\"" + descNPS + "\",\"productmodel\":\"" + modelNPS + "\",\"productcompany\":\"" + companyNPS + "\",\"productpackage\":\"" + PackageNPS + "\",\"productpurchasable\":\""+purchaseableS+"\",\"productpurchaselistprice\":\"" + listpriceNPS + "\",\"productpricetolerancepercent\":\""+tolerancepercentNPS+"\",\"productsellable\":\"" + sellableS + "\",\"productserialcontrolled\":\"" + serialcontrolledS + "\",\"productbatchtracked\":\"" + batchtrackedS + "\",\"standardcost\":\"" + standardcostS + "\",\"reorderlevel\":\"" + reorderlevelS + "\",\"reorderquantity\":\"" + reorderqtyS + "\",\"producttype\":\"" + mid + "\",\"productuom\":\"" + uid + "\",\"statusid\":\"" + sid + "\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/update_employee/";
                try {
                    JSONObject rmdt = null;
                    rmdt = new JSONObject(jsonS);
                    JSONSenderVolley(urlrs, rmdt);
                } catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);
            }
        });

        CancelEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UpdateEmp.this.handler.removeCallbacks(r);
                Intent intent = new Intent(UpdateEmp.this, Employees.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        extraB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateEmp.this, FamilyDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        r.run();

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

                        Log.d("----234-----", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEmp.this);
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

    public void getEmpLoc() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_emplocation/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d("hello :", response.toString());
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
                            List<String> lIdL = new ArrayList<String>();
                            List<String> locationL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("emp_location_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " Emp Loc : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String lIdS = new_array1.getJSONObject(i).getString("id");
                                String locationS = new_array1.getJSONObject(i).getString("location");
                                lIdL.add(lIdS);
                                locationL.add(locationS);
                            }

                            lIdA = new String[lIdL.size()];
                            locationA = new String[locationL.size()];

                            for (int l1 = 0; l1 < locationA.length; l1++) {

                                lIdA[l1] = lIdL.get(l1);
                                locationA[l1] = locationL.get(l1);
                                Log.d("lId ", lIdA[l1] +" indexA... "+l1);
                                Log.d("location ", locationA[l1]+" indexA... "+l1);
                            }
                            ArrayAdapter<String> locA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, locationA);
                            locA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            LocSp.setAdapter(locA);

                            loc = true;
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


    public void getJobRole() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_empjobrole/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d("hello :", response.toString());
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
                            List<String> jobRoleL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("emp_jobrole_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " requisition : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String idS = new_array1.getJSONObject(i).getString("id");
                                String jobRoleS = new_array1.getJSONObject(i).getString("jobrole");
                                idL.add(idS);
                                jobRoleL.add(jobRoleS);
                            }

                            jIdA = new String[idL.size()];
                            jobRoleA = new String[jobRoleL.size()];

                            Log.d("jobRoleAlength ", jobRoleA.length + " jobRoleLsize "+ jobRoleL.size() );
                            for (int l2 = 0; l2 < jobRoleA.length; l2++) {

                                jIdA[l2] = idL.get(l2);
                                jobRoleA[l2] = jobRoleL.get(l2);
                                Log.d("jId ", jIdA[l2]+" indexA... "+l2);
                                Log.d("jobRole ", jobRoleA[l2]+" indexA... "+l2);
                            }
                            ArrayAdapter<String> jobA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, jobRoleA);
                            jobA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roleSp.setAdapter(jobA);

                            role = true;

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

    public void getDept() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_departments/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d("hello :", response.toString());
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
                            List<String> dIdL = new ArrayList<String>();
                            List<String> deptL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("department_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " dept : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String dIdS = new_array1.getJSONObject(i).getString("id");
                                String deptS = new_array1.getJSONObject(i).getString("departmentname");
                                dIdL.add(dIdS);
                                deptL.add(deptS);
                            }

                            dIdA = new String[dIdL.size()];
                            deptA = new String[deptL.size()];

                            Log.d("deptAlength ", deptA.length + " deptLsize "+ deptL.size() );
                            for (int l3 = 0; l3 < deptA.length; l3++) {

                                dIdA[l3] = dIdL.get(l3);
                                deptA[l3] = deptL.get(l3);
                                Log.d("dId ", dIdA[l3]+" indexA... "+l3);
                                Log.d("dept ", deptA[l3]+" indexA... "+l3);
                            }
                            ArrayAdapter<String> jobA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, deptA);
                            jobA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            deptSp.setAdapter(jobA);

                            dept = true;
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

    public void getHR() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_hr/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d("hello :", response.toString());
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
                            List<String> hrIdL = new ArrayList<String>();
                            List<String> hrL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("hr_details");
                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d( " Array", " HR : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String hrIdS = new_array1.getJSONObject(i).getString("id");
                                String hrS = new_array1.getJSONObject(i).getString("hrname");
                                hrIdL.add(hrIdS);
                                hrL.add(hrS);
                            }

                            hrIdA = new String[hrIdL.size()];
                            hrA = new String[hrL.size()];

                            Log.d("hrAlength ", hrA.length + "hrLsize "+ hrL.size() );
                            for (int l4 = 0; l4 < hrA.length; l4++) {

                                hrIdA[l4] = hrIdL.get(l4);
                                hrA[l4] = hrL.get(l4);
                                Log.d("hrId ", hrIdA[l4]+" indexA... "+l4);
                                Log.d("hr_name ", hrA[l4]+" indexA... "+l4);
                            }
                            ArrayAdapter<String> HRA= new ArrayAdapter<String>(UpdateEmp.this,android.R.layout.simple_spinner_item, hrA);
                            HRA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            HRESp.setAdapter(HRA);

                            hr = true;
                            Log.d("exiting ", " getHR fn");
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

    public void httpRequest1() {

        Log.d( " in ", " get httpRequest1 ");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=" http://"+domain_name+":"+port+"/InventoryApp/get_employee/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Log.d("hello :", response.toString());
                        JSONObject jsonReq;
                        try {
                            responseJSON = new JSONObject(response);
                            //response_pro(responseJSON);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d( " Array", " response ->  " + response);

                        try {
                            // Log.d( " Array", " response ->  " + response);
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> nameL = new ArrayList<String>();
                            List<String> userL = new ArrayList<String>();
                            List<String> mailL = new ArrayList<String>();
                            List<String> pswL = new ArrayList<String>();
                            List<String> deptL = new ArrayList<String>();
                            List<String> roleL = new ArrayList<String>();
                            List<String> headL = new ArrayList<String>();
                            List<String> HRL = new ArrayList<String>();
                            List<String> LocL = new ArrayList<String>();
                            List<String> BloodL = new ArrayList<String>();
                            List<String> veL = new ArrayList<String>();
                            List<String> genderL = new ArrayList<String>();
                            List<String> DOBL = new ArrayList<String>();
                            List<String> DOJL = new ArrayList<String>();
                            List<String> AddrL = new ArrayList<String>();
                            new_array1 = responseJSON.getJSONArray("user_details");
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                //Log.d( " count", " i " + count + i);
                                String idS = new_array1.getJSONObject(i).getString("id");
                                //Log.d("productId from "," GET details" + prdidS);
                                String nameS = new_array1.getJSONObject(i).getString("fullname");
                                String userS = new_array1.getJSONObject(i).getString("username");
                                String mailS = new_array1.getJSONObject(i).getString("emailid");
                                //String pswS = new_array1.getJSONObject(i).getString("password");
                                String deptS = new_array1.getJSONObject(i).getString("department");
                                String roleS = new_array1.getJSONObject(i).getString("jobrole");
                                String headS = new_array1.getJSONObject(i).getString("reportinghead");
                                String HRS = new_array1.getJSONObject(i).getString("reportinghr");
                                String LocS = new_array1.getJSONObject(i).getString("worklocation");
                                String BloodS = new_array1.getJSONObject(i).getString("bloodgroup");
                                Log.d("BloodS from "," GET details" + BloodS);
                                String genderS = new_array1.getJSONObject(i).getString("gender");
                                String DOBS = new_array1.getJSONObject(i).getString("dateofbirth");
                                String DOJS = new_array1.getJSONObject(i).getString("dateofjoining");
                                String AddrS = new_array1.getJSONObject(i).getString("address");

                                idL.add(idS);
                                nameL.add(nameS);
                                userL.add(userS);
                                mailL.add(mailS);
                                //pswL.add(pswS);
                                deptL.add(deptS);
                                roleL.add(roleS);
                                headL.add(headS);
                                HRL.add(HRS);
                                LocL.add(LocS);
                                BloodL.add(BloodS);
                                genderL.add(genderS);
                                DOBL.add(DOBS);
                                DOJL.add(DOJS);
                                AddrL.add(AddrS);
                            }

                            idA = new String[idL.size()];
                            nameA = new String[nameL.size()];
                            userA = new String[userL.size()];
                            mailA = new String[mailL.size()];
                            //pswA = new String[pswL.size()];
                            deptA1 = new String[deptL.size()];
                            jobRoleA1 = new String[roleL.size()];
                            headA = new String[headL.size()];
                            hrA1 = new String[HRL.size()];
                            locationA1 = new String[LocL.size()];
                            BloodA1 = new String[BloodL.size()];
                            genderA1 = new String[genderL.size()];
                            DOBA = new String[DOBL.size()];
                            DOJA = new String[DOJL.size()];
                            AddrA = new String[AddrL.size()];

                            Log.d("idAlength ", idA.length + " idLsize "+ idL.size()+  BloodL.size() + genderL.size());

                            for (int l5 = 0; l5 < nameA.length; l5++) {

                                idA[l5] = idL.get(l5);
                                nameA[l5] = nameL.get(l5);
                                userA[l5] = userL.get(l5);
                                mailA[l5] = mailL.get(l5);
                                //pswA[l5] = pswL.get(l5);
                                deptA1[l5] = deptL.get(l5);
                                jobRoleA1[l5] = roleL.get(l5);
                                headA[l5] = headL.get(l5);
                                hrA1[l5] = HRL.get(l5);
                                locationA1[l5] = LocL.get(l5);
                                BloodA1[l5] = BloodL.get(l5);
                                Log.d("BloodA  ","....A..." + BloodA1[l5]);
                                genderA1[l5] = genderL.get(l5);
                                DOBA[l5] = DOBL.get(l5);
                                DOJA[l5] = DOJL.get(l5);
                                AddrA[l5] = AddrL.get(l5);

                            }

                            Log.d( " out of ", " pid loop in httpRequest1 ");
                        }
                        catch (JSONException e) {
                            Log.d("http  ","catch.......");
                            e.printStackTrace();
                        }


                        set();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("hello1 ","error.......");
            }
        });

        Log.d("before add  ","stringRequest");
        // Add the request to the RequestQueue.

        queue.add(stringRequest);
        Log.d( " exiting ", " httpRequest1 ");
    }


    final Runnable r = new Runnable() {
        public void run() {
            if (role && hr && loc && dept){

                role = false;
                hr = false;
                dept = false;
                loc = false;
                httpRequest1();
            }
            UpdateEmp.this.handler.postDelayed(r, 5000);
        }
    };

    public void set(){

        this.handler.removeCallbacks(r);

            for (int k1 = 0; k1 < idA.length; k1++) {

                if( idA[k1] == eID){
                    nameANE.setText(nameA[k1]);
                    Log.d(" nameA[k] ", nameA[k1]+" indexA... "+k1 + " idALength.. "+ idA.length);
                    userNE.setText(userA[k1]);
                    Log.d(" userA[k] ", userA[k1]+" indexA... "+k1 + " idALength.. "+ idA.length);
                    mailE.setText(mailA[k1]);
                    Log.d(" mailA[k] ", mailA[k1]+" indexA... "+k1 + " idALength.. "+ idA.length);
                    //pswE.setText(pswA[k1]);
                    //Log.d(" pswA[k] ", pswA[k1]+" indexA... "+k1 + " idALength.. "+ idA.length);
                    deptS = deptA1[k1];
                    Log.d(" deptA1[k] ", deptA1[k1]+" indexA... "+k1 + " deptA1Length.. "+ deptA1.length + deptA.length);

                    for (int d1 = 0; d1 < deptA.length; d1++) {
                        if (deptA[d1].contentEquals(deptS)) {
                            deptSp.setSelection(d1);
                            Log.d(" deptA ", deptA[d1]+ d1 + " deptALength.. "+ deptA.length);
                        }
                    }

                    roleS = jobRoleA1[k1];
                    Log.d(" jobRoleA1[k] ", jobRoleA1[k1]+ " jobRoleA1Length.. "+ jobRoleA1.length+ jobRoleA.length);

                    for (int e1 = 0; e1 < jobRoleA.length; e1++) {
                        if (jobRoleA[e1].contentEquals(roleS)) {
                            roleSp.setSelection(e1);
                            Log.d(" jobRoleA ", jobRoleA[e1]+ e1+ " jobALength.. "+ jobRoleA.length);
                        }
                        else{
                            Log.d(" roleS ", roleS+ "  "+ jobRoleA.length);
                        }
                    }

                    headE.setText(headA[k1]);
                    Log.d(" headA[k] ", headA[k1]+ " idALength.. "+ idA.length);

                    HRS = hrA1[k1];
                    Log.d(" hrA1[k] ", hrA1[k1]+ " hrA1Length.. "+ hrA1.length);
                    //HRESp.setSelection(k1);
                    //Log.d(" HR ", hrA[k1]+ k1 + " idALength.. "+ idA.length);

                    for (int e = 0; e < hrA.length; e++) {
                        if (hrA[e].contentEquals(HRS)) {
                            HRESp.setSelection(e);
                            Log.d(" HR ", hrA[e]+ e + " idALength.. "+ idA.length);
                        }
                    }

                    LocS = locationA1[k1];
                    Log.d(" locationA1[k] ", locationA1[k1] + " locA1Length.. "+ locationA1.length);
                    //LocSp.setSelection(k1);
                    //Log.d("Location ", locationA[k1] + k1 + " locALength.. "+ locationA.length);

                    for (int f = 0; f < locationA.length; f++) {
                        if (locationA[f].contentEquals(LocS)) {
                            LocSp.setSelection(f);
                            Log.d("Location ", locationA[f] + f + " locALength.. "+ locationA.length);
                        }
                    }
                    //Blood grp ++

                    BloodES = BloodA1[k1];
                    //String group = BloodES.substring(BloodES.length() - 1);
                    //String blood =BloodES.substring(0, BloodES.length() - 1);
                    //Log.d("BloodES ", BloodA[k1] + "blood.. "+ blood+ "grp.." +group);

                    Log.d(" Blood Group.. ", BloodA1[k1]+ k1 + " bloodA1Length.. "+ BloodA1.length + Blood.length);

                    for (int g1 = 0; g1 < Blood.length; g1++) {
                    if (Blood[g1].contentEquals(BloodES)) {
                        BloodESp.setSelection(g1);
                        Log.d(" Blood Group.. ", Blood[g1]+ g1 + " bloodALength.. "+ Blood.length);
                        }
                     }

                    genderES = genderA1[k1];
                    Log.d(" gender ", genderA1[k1]+ k1 + " GenderA1Length.. "+ genderA1.length+ "genderES " + genderES);

                    for (int g = 0; g < Gender.length; g++) {
                        Log.d("in"," for");
                        if (Gender[g].contentEquals(genderES)) {
                            Log.d("in"," if");
                            genderESp.setSelection(g);
                            Log.d(" gender ", Gender[g]+ g + " GenderLength.. "+ Gender.length);
                        }
                    }

                    DOBE.setText(DOBA[k1]);
                    Log.d(" DOBA[k] ", DOBA[k1] + " idALength.. "+ idA.length);

                    DOJE.setText(DOJA[k1]);
                    Log.d(" DOJA[k] ", DOJA[k1] + " idALength.. "+ idA.length);
                    AddrE.setText(AddrA[k1]);
                    Log.d(" AddrA[k] ", AddrA[k1] + " idALength.. "+ idA.length);
                }
            }
            Log.d( " out of ", " pid loop in httpRequest1 ");

        Log.d("exit  ","response block1.......");
    }
}
