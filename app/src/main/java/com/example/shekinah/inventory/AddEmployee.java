package com.example.shekinah.inventory;

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
import static com.example.shekinah.inventory.JobRolesMenu.eid;

public class AddEmployee extends AppCompatActivity {

    com.android.volley.RequestQueue sch_RequestQueue;
    String [] jIdA, lIdA, dIdA, deptA, locationA, statusNameA, jobRoleA, hrIdA, hrA, uomIdA, measurementA, Blood = {"O","A","B","AB"}, Grp = {"+","-"}, Gender = {"Male", "Female","Other"};
    Spinner BloodESp, veESp, genderESp, statusSp, roleSp, LocSp, deptSp, HRESp;
    String uid, mid,lid, did, jid, hrid,jsonS, nameANS, userNES, mailS, pswS, deptS, roleS, headS, HRS, LocS, BloodES, veES, genderES, DOBS, DOJS, DOES, AddrS, statusS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Button addEB = (Button) findViewById(R.id.addEmp);
        Button CancelEB = (Button) findViewById(R.id.cancelE);
        //alertdialog = builder.create();

        final EditText nameANE = (EditText) findViewById(R.id.nameE);
        final EditText userNE = (EditText) findViewById(R.id.uNameE);
        final EditText mailE = (EditText) findViewById(R.id.mailE);
        final EditText pswE = (EditText) findViewById(R.id.pswE);
        deptSp = (Spinner) findViewById(R.id.deptE);
        roleSp = (Spinner) findViewById(R.id.roleE);
        final EditText headE = (EditText) findViewById(R.id.headE);
        HRESp = (Spinner) findViewById(R.id.HRE);
        LocSp = (Spinner) findViewById(R.id.LocE);
        BloodESp = (Spinner)findViewById(R.id.BloodE);
        veESp = (Spinner)findViewById(R.id.veE);
        genderESp = (Spinner)findViewById(R.id.genderE);
        final EditText DOBE = (EditText) findViewById(R.id.DOBE);
        final EditText DOJE = (EditText) findViewById(R.id.DOJE);
        //final EditText DOEE = (EditText) findViewById(R.id.DOEE);
        final EditText AddrE = (EditText) findViewById(R.id.AddrE);

        Button LocB = (Button) findViewById(R.id.btn_loc);
        final Button Btnlogout = (Button) findViewById(R.id.btn_logout);
        //alertdialog = builder.create();
        getJobRole();
        getEmpLoc();
        getDept();
        getHR();
//        getUOM();
//        getStatus();
//        getMaterialType();
        ArrayAdapter<String> BloodA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, Blood);
        BloodA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BloodESp.setAdapter(BloodA);
        ArrayAdapter<String> GrpA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, Grp);
        GrpA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veESp.setAdapter(GrpA);
        ArrayAdapter<String> GenderA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, Gender);
        GenderA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderESp.setAdapter(GenderA);

        addEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameANS= nameANE.getText().toString();
                userNES= userNE.getText().toString();
                mailS= mailE.getText().toString();
                pswS= pswE.getText().toString();
                deptS= deptSp.getSelectedItem().toString();
                roleS= roleSp.getSelectedItem().toString();
                headS= headE.getText().toString();
                HRS= HRESp.getSelectedItem().toString();
                LocS= LocSp.getSelectedItem().toString();
                BloodES = BloodESp.getSelectedItem().toString();
                veES = veESp.getSelectedItem().toString();
                genderES = genderESp.getSelectedItem().toString();
                DOBS= DOBE.getText().toString();
                DOJS= DOJE.getText().toString();
                //DOES= DOEE.getText().toString();
                AddrS= AddrE.getText().toString();
                //statusS= statusE.getText().toString();

                for (int l = 0; l < jIdA.length; l++) {

                    if( jobRoleA[l] == roleS){
                        jid = jIdA[l];
                    }
                    Log.d("JobRole-- ", jobRoleA[l]);
                }

                for (int m = 0; m < lIdA.length; m++) {

                    if( locationA[m] == LocS){
                        lid = lIdA[m];
                    }
                    Log.d("JobRole-- ", jobRoleA[m]);
                }

                for (int n = 0; n < dIdA.length; n++) {

                    if( deptA[n] == deptS){
                        did = dIdA[n];
                    }
                    Log.d("JobRole-- ", deptA[n]);
                }

                for (int n = 0; n < hrIdA.length; n++) {

                    if( hrA[n] == HRS){
                        hrid = hrIdA[n];
                    }
                    Log.d("JobRole-- ", hrA[n]);
                }

                jsonS = "{\"fullname\":\"" + nameANS + "\",\"username\":\"" + userNES + "\",\"emailid\":\"" + mailS + "\",\"password\":\"" + pswS + "\",\"gender\":\"" + genderES + "\",\"bloodgroup\":\"" + BloodES+veES + "\",\"dateofbirth\":\"" + DOBS + "\",\"dateofjoining\":\"" + DOJS + "\",\"department\":\"" + did + "\",\"reportinghead\":\"" + headS + "\",\"reportinghr\":\"" + hrid + "\",\"jobrole\":\"" + jid + "\",\"address\":\"" + AddrS + "\",\"worklocation\":\"" + lid + "\"}";
                //jsonS = "{\"productname\":\"" + nameNPS + "\",\"productdescription\":\"" + descNPS + "\",\"productmodel\":\"" + modelNPS + "\",\"productcompany\":\"" + companyNPS + "\",\"productpackage\":\"" + PackageNPS + "\",\"productpurchasable\":\""+purchaseableS+"\",\"productpurchaselistprice\":\"" + listpriceNPS + "\",\"productpricetolerancepercent\":\""+tolerancepercentNPS+"\",\"productsellable\":\"" + sellableS + "\",\"productserialcontrolled\":\"" + serialcontrolledS + "\",\"productbatchtracked\":\"" + batchtrackedS + "\",\"standardcost\":\"" + standardcostS + "\",\"reorderlevel\":\"" + reorderlevelS + "\",\"reorderquantity\":\"" + reorderqtyS + "\",\"producttype\":\"" + mid + "\",\"productuom\":\"" + uid + "\",\"statusid\":\"" + sid + "\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/add_employee/";
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
                Intent intent = new Intent(AddEmployee.this, Employees.class);
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
                        Log.d("---------", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(AddEmployee.this);
                            builder.setTitle("Info");
                            builder.setMessage(errorDesc);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                    Intent intent = new Intent(AddEmployee.this, Employees.class);
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

                            for (int l = 0; l < locationL.size(); l++) {

                                lIdA[l] = lIdL.get(l);
                                locationA[l] = locationL.get(l);
                                Log.d("lId ", lIdA[l]);
                                Log.d("location ", locationA[l]);
                            }
                            ArrayAdapter<String> locA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, locationA);
                            locA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            LocSp.setAdapter(locA);

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

                            for (int l = 0; l < jobRoleL.size(); l++) {

                                jIdA[l] = idL.get(l);
                                jobRoleA[l] = jobRoleL.get(l);
                                Log.d("jId ", jIdA[l]);
                                Log.d("jobRole ", jobRoleA[l]);
                            }
                            ArrayAdapter<String> jobA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, jobRoleA);
                            jobA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roleSp.setAdapter(jobA);

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

                            for (int l = 0; l < deptL.size(); l++) {

                                dIdA[l] = dIdL.get(l);
                                deptA[l] = deptL.get(l);
                                Log.d("dId ", dIdA[l]);
                                Log.d("dept ", deptA[l]);
                            }
                            ArrayAdapter<String> jobA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, deptA);
                            jobA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            deptSp.setAdapter(jobA);

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

                            for (int l = 0; l < hrL.size(); l++) {

                                hrIdA[l] = hrIdL.get(l);
                                hrA[l] = hrL.get(l);
                                Log.d("hrId ", hrIdA[l]);
                                Log.d("hr_name ", hrA[l]);
                            }
                            ArrayAdapter<String> HRA= new ArrayAdapter<String>(AddEmployee.this,android.R.layout.simple_spinner_item, hrA);
                            HRA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            HRESp.setAdapter(HRA);

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
}
